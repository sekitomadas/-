import Link from "next/link";
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

        <div className={styles.actions}>
          <Link className={styles.primary} href="/users">
            社員一覧を開く
          </Link>
          <Link className={styles.secondary} href="/users/new">
            社員を登録する
          </Link>
          <Link className={styles.secondary} href="/seat-actions">
            座席登録・退席
          </Link>
        </div>
      </main>
    </div>
  );
}
