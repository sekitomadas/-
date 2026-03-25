"use client";

import Link from "next/link";
import { usePathname, useRouter } from "next/navigation";
import { logout } from "@/lib/api";
import { getLoggedInUserName, hasAccessToken, isAdminUser } from "@/lib/api/client";
import styles from "./global-header-nav.module.css";

type NavItem = {
  href: string;
  label: string;
};

const NAV_ITEMS: NavItem[] = [
  { href: "/", label: "トップ" },
  { href: "/users", label: "社員一覧" },
  { href: "/seat-actions", label: "座席操作" },
  { href: "/current-seat-lookup", label: "現在位置照会" },
];

export default function GlobalHeaderNav() {
  const pathname = usePathname();
  const router = useRouter();
  const loggedIn = hasAccessToken();
  const isAdmin = loggedIn && isAdminUser();
  const userName = getLoggedInUserName();

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
          {loggedIn && (
            <span className={styles.userStatus} aria-label="ログイン中ユーザー">
              ログイン中: {userName ?? "ユーザ"}
            </span>
          )}
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
          {isAdmin && (
            <Link
              href="/users/new"
              aria-current={pathname === "/users/new" ? "page" : undefined}
              className={pathname === "/users/new" ? `${styles.link} ${styles.active}` : styles.link}
            >
              社員登録
            </Link>
          )}
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
