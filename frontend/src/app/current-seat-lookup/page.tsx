"use client";

import { FormEvent, useState, useEffect, useMemo } from "react";
import { useRouter } from "next/navigation";
import { ApiClientError, getAllCurrentSeats, getCurrentSeatByUserId, getSeats } from "@/lib/api";
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

export default function CurrentSeatLookupPage() {
  const router = useRouter();
  const [userIdInput, setUserIdInput] = useState("");
  const [submittedUserId, setSubmittedUserId] = useState<number | null>(null);
  const [results, setResults] = useState<CurrentSeat[]>([]);
  const [loading, setLoading] = useState(false);
  const [emptyMessage, setEmptyMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [allSeats, setAllSeats] = useState<Seat[]>([]);
  const [occupiedSeats, setOccupiedSeats] = useState<CurrentSeat[]>([]);

  const clearMessages = () => {
    setEmptyMessage("");
    setErrorMessage("");
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
    </div>
  );
}
