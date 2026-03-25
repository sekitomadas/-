"use client";

import { useEffect, useMemo, useState } from "react";
import { useRouter } from "next/navigation";
import {
  ApiClientError,
  getCurrentSeat,
  getSeats,
  leaveCurrentSeat,
  registerCurrentSeat,
} from "@/lib/api";
import { hasAccessToken } from "@/lib/api/client";
import type { CurrentSeat, Seat } from "@/types/api";
import styles from "./seat-actions-page.module.css";

const getSeatConflictMessage = (error: ApiClientError) => {
  const detail = error.details.find((item) => item.field === "seatId");
  if (detail?.reason) {
    return `${detail.reason}。別の座席を選択して再実行してください。`;
  }

  return "指定された座席は既に利用中です。別の座席を選択して再実行してください。";
};

export default function SeatActionsPage() {
  const router = useRouter();
  const [seats, setSeats] = useState<Seat[]>([]);
  const [selectedSeatId, setSelectedSeatId] = useState("");
  const [currentSeat, setCurrentSeat] = useState<CurrentSeat | null>(null);

  const [loadingInitial, setLoadingInitial] = useState(true);
  const [loadingCurrentSeat, setLoadingCurrentSeat] = useState(false);
  const [submittingSeat, setSubmittingSeat] = useState(false);
  const [submittingLeave, setSubmittingLeave] = useState(false);

  const [error, setError] = useState("");
  const [conflict, setConflict] = useState("");
  const [notice, setNotice] = useState("");

  useEffect(() => {
    if (!hasAccessToken()) {
      router.replace("/login");
      return;
    }

    const loadData = async () => {
      try {
        setLoadingInitial(true);
        setLoadingCurrentSeat(true);
        setError("");

        const [seatsData, currentSeatData] = await Promise.all([
          getSeats(),
          getCurrentSeat().catch((err) => {
            if (err instanceof ApiClientError && err.status === 404 && err.code === "CURRENT_SEAT_NOT_FOUND") {
              return null;
            }
            throw err;
          }),
        ]);

        setSeats(seatsData);
        setCurrentSeat(currentSeatData);
      } catch (err) {
        if (err instanceof ApiClientError && err.status === 401) {
          router.replace("/login");
          return;
        }

        if (err instanceof ApiClientError) {
          setError(err.message);
        } else {
          setError("座席データまたは現在位置の取得に失敗しました");
        }
      } finally {
        setLoadingCurrentSeat(false);
        setLoadingInitial(false);
      }
    };

    void loadData();
  }, [router]);

  const canSubmitSeat = useMemo(() => {
    return !!selectedSeatId && !submittingSeat && !submittingLeave;
  }, [selectedSeatId, submittingLeave, submittingSeat]);

  const canSubmitLeave = useMemo(() => {
    return !submittingSeat && !submittingLeave;
  }, [submittingLeave, submittingSeat]);

  const onRegisterSeat = async () => {
    if (!selectedSeatId) {
      setError("座席を選択してください");
      return;
    }

    setError("");
    setConflict("");
    setNotice("");

    try {
      setSubmittingSeat(true);
      await registerCurrentSeat({
        seatId: Number(selectedSeatId),
      });

      setLoadingCurrentSeat(true);
      const latest = await getCurrentSeat();
      setCurrentSeat(latest);
      setNotice("着席登録が完了しました");
    } catch (err) {
      if (err instanceof ApiClientError && err.status === 409) {
        setConflict(getSeatConflictMessage(err));
        return;
      }

      if (err instanceof ApiClientError && err.status === 401) {
        router.replace("/login");
        return;
      }

      if (err instanceof ApiClientError) {
        setError(err.message);
      } else {
        setError("着席登録に失敗しました");
      }
    } finally {
      setLoadingCurrentSeat(false);
      setSubmittingSeat(false);
    }
  };

  const onLeaveSeat = async () => {
    setError("");
    setConflict("");
    setNotice("");

    try {
      setSubmittingLeave(true);
      await leaveCurrentSeat();
      setCurrentSeat(null);
      setNotice("退席登録が完了しました");
    } catch (err) {
      if (err instanceof ApiClientError && err.status === 401) {
        router.replace("/login");
        return;
      }

      if (err instanceof ApiClientError) {
        setError(err.message);
      } else {
        setError("退席登録に失敗しました");
      }
    } finally {
      setSubmittingLeave(false);
    }
  };

  return (
    <div className={styles.page}>
      <main className={styles.main}>
        <header className={styles.header}>
          <div>
            <p className={styles.kicker}>Seat Actions</p>
            <h1>座席登録・退席</h1>
          </div>
        </header>

        {loadingInitial && <p className={styles.message}>社員・座席データを読み込み中です...</p>}
        {!loadingInitial && error && <p className={styles.error}>{error}</p>}
        {!loadingInitial && conflict && <p className={styles.conflict}>{conflict}</p>}
        {!loadingInitial && notice && <p className={styles.notice}>{notice}</p>}

        {!loadingInitial && (
          <section className={styles.panel}>
            <label className={styles.field}>
              <span>座席</span>
              <select value={selectedSeatId} onChange={(e) => setSelectedSeatId(e.target.value)}>
                <option value="">座席を選択してください</option>
                {seats.map((seat) => (
                  <option key={seat.id} value={seat.id}>
                    {seat.name} ({seat.location || "location未設定"})
                  </option>
                ))}
              </select>
            </label>

            <div className={styles.actions}>
              <button type="button" className={styles.primary} onClick={onRegisterSeat} disabled={!canSubmitSeat}>
                {submittingSeat ? "着席登録中..." : "着席登録"}
              </button>
              <button
                type="button"
                className={styles.secondary}
                onClick={onLeaveSeat}
                disabled={!canSubmitLeave}
              >
                {submittingLeave ? "退席登録中..." : "退席登録"}
              </button>
            </div>
          </section>
        )}

        {!loadingInitial && (
          <section className={styles.currentSeat}>
            <h2>現在の在席情報</h2>
            {loadingCurrentSeat && <p className={styles.message}>現在位置を確認中です...</p>}
            {!loadingCurrentSeat && currentSeat && (
              <dl>
                <div>
                  <dt>社員名</dt>
                  <dd>{currentSeat.userName}</dd>
                </div>
                <div>
                  <dt>座席</dt>
                  <dd>{currentSeat.seat.name}</dd>
                </div>
                <div>
                  <dt>場所</dt>
                  <dd>{currentSeat.seat.location || "location未設定"}</dd>
                </div>
                <div>
                  <dt>着席開始</dt>
                  <dd>{new Date(currentSeat.since).toLocaleString("ja-JP")}</dd>
                </div>
              </dl>
            )}
            {!loadingCurrentSeat && !currentSeat && (
              <p className={styles.message}>あなたは現在退席状態です</p>
            )}
          </section>
        )}
      </main>
    </div>
  );
}