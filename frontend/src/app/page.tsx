"use client";

import styles from "./page.module.css";

export default function Home() {
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
      </main>
    </div>
  );
}
