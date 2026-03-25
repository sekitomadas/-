"use client";

import Link from "next/link";
import { usePathname, useRouter } from "next/navigation";
import { logout } from "@/lib/api";
import { hasAccessToken } from "@/lib/api/client";
import styles from "./global-header-nav.module.css";

type NavItem = {
  href: string;
  label: string;
};

const NAV_ITEMS: NavItem[] = [
  { href: "/", label: "トップ" },
  { href: "/users", label: "社員一覧" },
  { href: "/users/new", label: "社員登録" },
  { href: "/seat-actions", label: "座席操作" },
  { href: "/current-seat-lookup", label: "現在位置照会" },
];

export default function GlobalHeaderNav() {
  const pathname = usePathname();
  const router = useRouter();
  const loggedIn = hasAccessToken();

  if (pathname === "/login") {
    return null;
  }

  const onLogin = () => {
    router.push("/login");
  };

  const onLogout = () => {
    logout();
    router.push("/login");
  };

  return (
    <header className={styles.header}>
      <div className={styles.inner}>
        <Link href="/" className={styles.brand}>
          OfficeNavi
        </Link>
        <nav className={styles.nav} aria-label="global">
          {NAV_ITEMS.map((item) => {
            const active = pathname === item.href;
            return (
              <Link
                key={item.href}
                href={item.href}
                aria-current={active ? "page" : undefined}
                className={active ? `${styles.link} ${styles.active}` : styles.link}
              >
                {item.label}
              </Link>
            );
          })}
          <button
            type="button"
            onClick={loggedIn ? onLogout : onLogin}
            className={styles.logoutButton}
            aria-label={loggedIn ? "ログアウト" : "ログイン"}
          >
            {loggedIn ? "ログアウト" : "ログイン"}
          </button>
        </nav>
      </div>
    </header>
  );
}
