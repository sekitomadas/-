"use client";

import { useEffect, useMemo, useState } from "react";
import Link from "next/link";
import {
  ApiClientError,
  getCurrentSeat,
  getSeats,
  getUsers,
  leaveCurrentSeat,
  registerCurrentSeat,
} from "@/lib/api";
import type { CurrentSeat, Seat, User } from "@/types/api";
import styles from "./seat-actions-page.module.css";

const getSeatConflictMessage = (error: ApiClientError) => {
  const detail = error.details.find((item) => item.field === "seatId");
  if (detail?.reason) {
    return `${detail.reason}。別の座席を選択して再実行してください。`;
  }

  return "指定された座席は既に利用中です。別の座席を選択して再実行してください。";
};

export default function SeatActionsPage() {
  const [users, setUsers] = useState<User[]>([]);
  const [seats, setSeats] = useState<Seat[]>([]);
  const [selectedUserId, setSelectedUserId] = useState("");
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
    const loadMasterData = async () => {
      try {
        setLoadingInitial(true);
        setError("");
        const [usersData, seatsData] = await Promise.all([getUsers(), getSeats()]);
        setUsers(usersData);
        setSeats(seatsData);
      } catch (err) {
        if (err instanceof ApiClientError) {
          setError(err.message);
        } else {
          setError("社員または座席データの取得に失敗しました");
        }
      } finally {
        setLoadingInitial(false);
      }
    };

    void loadMasterData();
  }, []);

  useEffect(() => {
    const loadCurrentSeat = async () => {
      if (!selectedUserId) {
        setCurrentSeat(null);
        return;
      }

      try {
        setLoadingCurrentSeat(true);
        const data = await getCurrentSeat(Number(selectedUserId));
        setCurrentSeat(data);
      } catch (err) {
        if (err instanceof ApiClientError && err.status === 404 && err.code === "CURRENT_SEAT_NOT_FOUND") {
          setCurrentSeat(null);
          return;
        }

        if (err instanceof ApiClientError) {
          setError(err.message);
        } else {
          setError("現在の在席情報の取得に失敗しました");
        }
      } finally {
        setLoadingCurrentSeat(false);
      }
    };

    void loadCurrentSeat();
  }, [selectedUserId]);

  const canSubmitSeat = useMemo(() => {
    return !!selectedUserId && !!selectedSeatId && !submittingSeat && !submittingLeave;
  }, [selectedSeatId, selectedUserId, submittingLeave, submittingSeat]);

  const canSubmitLeave = useMemo(() => {
    return !!selectedUserId && !submittingSeat && !submittingLeave;
  }, [selectedUserId, submittingLeave, submittingSeat]);

  const onRegisterSeat = async () => {
    if (!selectedUserId || !selectedSeatId) {
      setError("社員と座席を選択してください");
      return;
    }

    setError("");
    setConflict("");
    setNotice("");

    try {
      setSubmittingSeat(true);
      await registerCurrentSeat({
        userId: Number(selectedUserId),
        seatId: Number(selectedSeatId),
      });

      const latest = await getCurrentSeat(Number(selectedUserId));
      setCurrentSeat(latest);
      setNotice("着席登録が完了しました");
    } catch (err) {
      if (err instanceof ApiClientError && err.status === 409) {
        setConflict(getSeatConflictMessage(err));
        return;
      }

      if (err instanceof ApiClientError) {
        setError(err.message);
      } else {
        setError("着席登録に失敗しました");
      }
    } finally {
      setSubmittingSeat(false);
    }
  };

  const onLeaveSeat = async () => {
    if (!selectedUserId) {
      setError("社員を選択してください");
      return;
    }

    setError("");
    setConflict("");
    setNotice("");

    try {
      setSubmittingLeave(true);
      await leaveCurrentSeat({ userId: Number(selectedUserId) });
      setCurrentSeat(null);
      setNotice("退席登録が完了しました");
    } catch (err) {
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
          <div className={styles.links}>
            <Link href="/">トップ</Link>
            <Link href="/users">社員一覧</Link>
          </div>
        </header>

        {loadingInitial && <p className={styles.message}>社員・座席データを読み込み中です...</p>}
        {!loadingInitial && error && <p className={styles.error}>{error}</p>}
        {!loadingInitial && conflict && <p className={styles.conflict}>{conflict}</p>}
        {!loadingInitial && notice && <p className={styles.notice}>{notice}</p>}

        {!loadingInitial && (
          <section className={styles.panel}>
            <label className={styles.field}>
              <span>社員</span>
              <select value={selectedUserId} onChange={(e) => setSelectedUserId(e.target.value)}>
                <option value="">社員を選択してください</option>
                {users.map((user) => (
                  <option key={user.id} value={user.id}>
                    {user.name} (ID: {user.id})
                  </option>
                ))}
              </select>
            </label>

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

        {!loadingInitial && selectedUserId && (
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
              <p className={styles.message}>この社員は現在退席状態です</p>
            )}
          </section>
        )}
      </main>
    </div>
  );
}