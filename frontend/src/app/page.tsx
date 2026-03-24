"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { logout } from "@/lib/api";
import { hasAccessToken } from "@/lib/api/client";
import styles from "./page.module.css";

export default function Home() {
  const router = useRouter();
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const frameId = requestAnimationFrame(() => {
      setIsLoggedIn(hasAccessToken());
    });

    return () => {
      cancelAnimationFrame(frameId);
    };
  }, []);

  const onLogout = () => {
    logout();
    setIsLoggedIn(false);
    router.push("/login");
  };

  return (
    <div className={styles.page}>
      <main className={styles.main}>
        <div className={styles.hero}>
          <p className={styles.kicker}>OfficeNavi</p>
          <h1>フロント実装をここから開始</h1>
          <p>
            社員一覧の確認と社員登録からMVPを進めます。APIクライアント層を利用して、画面から
            バックエンドへ接続します。
          </p>
        </div>

        <div className={styles.actions}>
          {!isLoggedIn && (
            <Link className={styles.primary} href="/login">
              ログイン
            </Link>
          )}
          {isLoggedIn && (
            <button type="button" className={styles.secondary} onClick={onLogout}>
              ログアウト
            </button>
          )}
          <Link className={styles.primary} href="/users">
            社員一覧を開く
          </Link>
          <Link className={styles.secondary} href="/users/new">
            社員を登録する
          </Link>
        </div>
      </main>
    </div>
  );
}
