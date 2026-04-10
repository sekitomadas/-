"use client";

import { FormEvent, useState, useEffect, useMemo, useRef } from "react";
import { useRouter } from "next/navigation";
import { ApiClientError, getAllCurrentSeats, getCurrentSeatByUserId, getSeats, registerCurrentSeat } from "@/lib/api";
import { hasAccessToken } from "@/lib/api/client";
import type { CurrentSeat, Seat } from "@/types/api";
import styles from "./current-seat-lookup-page.module.css";
import FloorMap from "@/components/floor-map";

const isPositiveInteger = (value: string) => {
  return /^[1-9][0-9]*$/.test(value);
};

const normalizeLocation = (location?: string | null) => {
  return location && location.trim() ? location : "場所未設定";
};

const getSeatConflictMessage = (error: ApiClientError) => {
  const detail = error.details.find((item) => item.field === "seatId");
  if (detail?.reason) {
    return `${detail.reason}。別の座席を選択して再試行してください。`;
  }

  return "指定された座席は既に利用中です。別の座席を選択して再試行してください。";
};

export default function CurrentSeatLookupPage() {
  const router = useRouter();
  const [userIdInput, setUserIdInput] = useState("");
  const [submittedUserId, setSubmittedUserId] = useState<number | null>(null);
  const [results, setResults] = useState<CurrentSeat[]>([]);
  const [loading, setLoading] = useState(false);
  const [emptyMessage, setEmptyMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [noticeMessage, setNoticeMessage] = useState("");
  const [allSeats, setAllSeats] = useState<Seat[]>([]);
  const [occupiedSeats, setOccupiedSeats] = useState<CurrentSeat[]>([]);
  const [confirmingSeat, setConfirmingSeat] = useState<Seat | null>(null);
  const [registeringSeatId, setRegisteringSeatId] = useState<number | null>(null);

  const seatRegisterDialogRef = useRef<HTMLDivElement>(null);
  const seatRegisterCancelButtonRef = useRef<HTMLButtonElement>(null);
  const seatRegisterConfirmButtonRef = useRef<HTMLButtonElement>(null);

  const clearMessages = () => {
    setEmptyMessage("");
    setErrorMessage("");
    setNoticeMessage("");
  };

  const lookupAll = async () => {
    clearMessages();
    setSubmittedUserId(null);
    setLoading(true);

    try {
      const data = await getAllCurrentSeats();
      setResults(data);
      setOccupiedSeats(data);
      if (data.length === 0) {
        setEmptyMessage("現在座席が登録されているユーザーはいません。");
      }
    } catch (err) {
      setResults([]);
      if (err instanceof ApiClientError && err.status === 401) {
        router.replace("/login");
        return;
      }

      if (err instanceof ApiClientError && err.status >= 500) {
        setErrorMessage("サーバーエラーが発生しました。時間をおいて再試行してください。");
      } else if (err instanceof ApiClientError) {
        setErrorMessage(err.message);
      } else {
        setErrorMessage("現在位置の照会に失敗しました。");
      }
    } finally {
      setLoading(false);
    }
  };

  const lookupByUserId = async (userId: number) => {
    clearMessages();
    setResults([]);
    setSubmittedUserId(userId);
    setLoading(true);

    try {
      const data = await getCurrentSeatByUserId(userId);
      setResults([data]);
    } catch (err) {
      if (err instanceof ApiClientError && err.status === 401) {
        router.replace("/login");
        return;
      }

      if (err instanceof ApiClientError && err.status === 404) {
        setEmptyMessage(`userId=${userId} の現在位置は登録されていません。`);
      } else if (err instanceof ApiClientError && err.status >= 500) {
        setErrorMessage("サーバーエラーが発生しました。時間をおいて再試行してください。");
      } else if (err instanceof ApiClientError) {
        setErrorMessage(err.message);
      } else {
        setErrorMessage("現在位置の照会に失敗しました。");
      }
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (confirmingSeat) {
      seatRegisterCancelButtonRef.current?.focus();
    }
  }, [confirmingSeat]);

  useEffect(() => {
    if (!hasAccessToken()) {
      router.replace("/login");
      return;
    }

    const loadInitial = async () => {
      clearMessages();
      setSubmittedUserId(null);
      setLoading(true);

      try {
        const [seatsData, currentSeats] = await Promise.all([
          getSeats(),
          getAllCurrentSeats(),
        ]);
        setAllSeats(seatsData);
        setOccupiedSeats(currentSeats);
        setResults(currentSeats);
        if (currentSeats.length === 0) {
          setEmptyMessage("現在座席が登録されているユーザーはいません。");
        }
      } catch (err) {
        setResults([]);
        if (err instanceof ApiClientError && err.status === 401) {
          router.replace("/login");
          return;
        }

        if (err instanceof ApiClientError && err.status >= 500) {
          setErrorMessage("サーバーエラーが発生しました。時間をおいて再試行してください。");
        } else if (err instanceof ApiClientError) {
          setErrorMessage(err.message);
        } else {
          setErrorMessage("現在位置の照会に失敗しました。");
        }
      } finally {
        setLoading(false);
      }
    };

    void loadInitial();
  }, [router]);

  const onSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!userIdInput) {
      await lookupAll();
      return;
    }

    if (!isPositiveInteger(userIdInput)) {
      clearMessages();
      setResults([]);
      setErrorMessage("社員IDは1以上の整数を入力してください。");
      return;
    }

    await lookupByUserId(Number(userIdInput));
  };

  const onRetry = async () => {
    if (loading) {
      return;
    }

    if (submittedUserId === null) {
      await lookupAll();
      return;
    }

    await lookupByUserId(submittedUserId);
  };

  const onSelectAvailableSeat = (seat: Seat) => {
    if (registeringSeatId !== null) return;
    clearMessages();
    setConfirmingSeat(seat);
  };

  const onCancelSeatRegister = () => {
    if (registeringSeatId !== null) return;
    setConfirmingSeat(null);
  };

  const onConfirmSeatRegister = async () => {
    if (!confirmingSeat || registeringSeatId !== null) return;

    const targetSeat = confirmingSeat;
    setRegisteringSeatId(targetSeat.id);
    setErrorMessage("");
    setNoticeMessage("");

    // ① 在席登録
    try {
      await registerCurrentSeat({ seatId: targetSeat.id });
    } catch (err) {
      if (err instanceof ApiClientError && err.status === 409) {
        setErrorMessage(getSeatConflictMessage(err));
      } else if (err instanceof ApiClientError && err.status === 401) {
        router.replace("/login");
        return;
      } else if (err instanceof ApiClientError) {
        setErrorMessage(err.message);
      } else {
        setErrorMessage("在席登録に失敗しました。");
      }
      setRegisteringSeatId(null);
      setConfirmingSeat(null);
      return;
    }

    // ② 登録成功 → ダイアログを閉じて成功通知
    setNoticeMessage(`${targetSeat.name} に着席登録しました。`);
    setRegisteringSeatId(null);
    setConfirmingSeat(null);

    // ③ フロアマップ / 照会結果を最新化（失敗しても登録成功は確定済み）
    try {
      const latestSeats = await getAllCurrentSeats();
      setOccupiedSeats(latestSeats);
      if (submittedUserId === null) {
        setResults(latestSeats);
      }
    } catch {
      setErrorMessage("表示の更新に失敗しました。ページを再読み込みしてください。");
    }
  };

  const onSeatRegisterDialogKeyDown = (e: React.KeyboardEvent<HTMLDivElement>) => {
    if (e.key === "Escape") {
      onCancelSeatRegister();
      return;
    }
    if (e.key !== "Tab") return;
    const cancel = seatRegisterCancelButtonRef.current;
    const confirm = seatRegisterConfirmButtonRef.current;
    if (!cancel || !confirm) return;
    if (e.shiftKey ? document.activeElement === cancel : document.activeElement === confirm) {
      e.preventDefault();
      (e.shiftKey ? confirm : cancel).focus();
    }
  };

  const locationGroups = useMemo(
    () => allSeats.reduce<Record<string, Seat[]>>((acc, seat) => {
      const key = normalizeLocation(seat.location);
      if (!acc[key]) acc[key] = [];
      acc[key].push(seat);
      return acc;
    }, {}),
    [allSeats]
  );

  const seatLocationKeyById = useMemo(
    () => new Map(allSeats.map((seat) => [seat.id, normalizeLocation(seat.location)])),
    [allSeats]
  );

  const occupiedSeatsByLocation = useMemo(
    () => occupiedSeats.reduce<Record<string, CurrentSeat[]>>((acc, currentSeat) => {
      const key = seatLocationKeyById.get(currentSeat.seat.id) ?? normalizeLocation(currentSeat.seat.location);
      if (!acc[key]) acc[key] = [];
      acc[key].push(currentSeat);
      return acc;
    }, {}),
    [occupiedSeats, seatLocationKeyById]
  );

  return (
    <div className={styles.page}>
      <main className={styles.main}>
        <header className={styles.header}>
          <div>
            <p className={styles.kicker}>Current Seat Lookup</p>
            <h1>現在位置照会</h1>
          </div>
        </header>

        {allSeats.length > 0 && (
          <section className={styles.floorMapSection}>
            <h2>フロアマップ</h2>
            {Object.entries(locationGroups).map(([location, locationSeats]) => (
              <FloorMap
                key={location}
                location={location}
                seats={locationSeats}
                occupiedSeats={occupiedSeatsByLocation[location] ?? []}
                onSelectAvailableSeat={onSelectAvailableSeat}
                selectingSeatId={registeringSeatId}
              />
            ))}
          </section>
        )}
        <h1>座席照会</h1>
        <section className={styles.panel}>
          <form className={styles.form} onSubmit={onSubmit}>
            <label className={styles.field}>
              <span>社員ID</span>
              <input
                type="text"
                inputMode="numeric"
                pattern="[0-9]*"
                placeholder="空欄で全件（例: 1）"
                value={userIdInput}
                onChange={(e) => setUserIdInput(e.target.value.trim())}
                disabled={loading}
              />
            </label>
            <button type="submit" className={styles.primary} disabled={loading}>
              {loading ? "照会中..." : "照会"}
            </button>
          </form>
        </section>

        {errorMessage && (
          <section className={styles.errorBox}>
            <p>{errorMessage}</p>
            {submittedUserId !== null && (
              <button type="button" className={styles.retry} onClick={onRetry} disabled={loading}>
                再試行
              </button>
            )}
          </section>
        )}

        {noticeMessage && <p className={styles.noticeBox}>{noticeMessage}</p>}

        {emptyMessage && <p className={styles.empty}>{emptyMessage}</p>}

        {results.length > 0 && (
          <section className={styles.results}>
            <h2>照会結果</h2>
            <div className={styles.cards}>
              {results.map((result) => (
                <article className={styles.card} key={`${result.userId}-${result.seat.id}-${result.since}`}>
                  <dl>
                    <div>
                      <dt>社員ID</dt>
                      <dd>{result.userId}</dd>
                    </div>
                    <div>
                      <dt>社員名</dt>
                      <dd>{result.userName}</dd>
                    </div>
                    <div>
                      <dt>座席</dt>
                      <dd>{result.seat.name}</dd>
                    </div>
                    <div>
                      <dt>場所</dt>
                      <dd>{result.seat.location || "location未設定"}</dd>
                    </div>
                    <div>
                      <dt>着席開始</dt>
                      <dd>{new Date(result.since).toLocaleString("ja-JP")}</dd>
                    </div>
                  </dl>
                </article>
              ))}
            </div>
          </section>
        )}

      </main>

      {confirmingSeat && (
        <div className={styles.modalOverlay} role="presentation" onClick={onCancelSeatRegister}>
          <div
            ref={seatRegisterDialogRef}
          className={styles.modal}
            role="dialog"
            aria-modal="true"
            aria-labelledby="seat-register-title"
            aria-describedby="seat-register-description"
            onClick={(e) => e.stopPropagation()}
            onKeyDown={onSeatRegisterDialogKeyDown}
          >
            <h2 id="seat-register-title">ここに座りますか？</h2>
            <p id="seat-register-description">
              対象座席: {confirmingSeat.name}（{normalizeLocation(confirmingSeat.location)}）
            </p>
            <div className={styles.modalActions}>
              <button ref={seatRegisterCancelButtonRef} type="button" className={styles.retry} onClick={onCancelSeatRegister} disabled={registeringSeatId !== null}>
                いいえ
              </button>
              <button ref={seatRegisterConfirmButtonRef} type="button" className={styles.primary} onClick={onConfirmSeatRegister} disabled={registeringSeatId !== null}>
                {registeringSeatId !== null ? "登録中..." : "はい"}
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
