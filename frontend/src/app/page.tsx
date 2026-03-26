import Link from "next/link";
import styles from "./page.module.css";

const FEATURE_LINKS = [
  {
    href: "/users",
    title: "社員一覧を見る",
    description: "登録済みの社員情報を確認し、利用者の基本情報を把握できます。",
  },
  {
    href: "/seat-actions",
    title: "座席を登録・退席する",
    description: "自分の現在座席を登録し、退席時にはすぐに解放できます。",
  },
  {
    href: "/current-seat-lookup",
    title: "現在位置を照会する",
    description: "社員IDやフロアマップから、誰がどこに座っているかを確認できます。",
  },
];

export default function Home() {
  return (
    <div className={styles.page}>
      <main className={styles.main}>
        <section className={styles.hero}>
          <div>
            <p className={styles.kicker}>OfficeNavi</p>
            <h1>オフィス内の在席状況を、ひと目で確認・更新。</h1>
          </div>
          <p className={styles.lead}>
            OfficeNavi は、社員一覧の確認、着席・退席の操作、現在位置の照会を一元化した業務アプリです。
            ログイン後すぐに必要な機能へ移動でき、日々の在席管理をスムーズに進められます。
          </p>
          <div className={styles.actions}>
            <Link href="/users" className={styles.primary}>
              利用を始める
            </Link>
            <Link href="/login" className={styles.secondary}>
              ログイン画面へ
            </Link>
          </div>
        </section>

        <section className={styles.panel}>
          <div className={styles.panelHeader}>
            <p className={styles.sectionLabel}>Primary Flow</p>
            <h2>最初に使う機能</h2>
          </div>
          <ol className={styles.steps}>
            <li>ログインして利用可能な画面へ入る</li>
            <li>社員一覧で対象ユーザーを確認する</li>
            <li>座席登録または現在位置照会へ進む</li>
          </ol>
        </section>

        <section className={styles.panel}>
          <div className={styles.panelHeader}>
            <p className={styles.sectionLabel}>Available Pages</p>
            <h2>機能別メニュー</h2>
          </div>
          <div className={styles.cardGrid}>
            {FEATURE_LINKS.map((item) => (
              <Link key={item.href} href={item.href} className={styles.card}>
                <span className={styles.cardTitle}>{item.title}</span>
                <p>{item.description}</p>
                <span className={styles.cardLink}>この画面を開く</span>
              </Link>
            ))}
          </div>
        </section>
      </main>
    </div>
  );
}
