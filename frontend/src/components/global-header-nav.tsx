"use client";

import { useCallback, useEffect, useState, useSyncExternalStore } from "react";
import Link from "next/link";
import { usePathname, useRouter } from "next/navigation";
import { logout } from "@/lib/api";
import { getLoggedInUserName, hasAccessToken, isAdminUser } from "@/lib/api/client";
import styles from "./global-header-nav.module.css";

type NavItem = {
  href: string;
  label: string;
};

type AuthState = {
  loggedIn: boolean;
  isAdmin: boolean;
  userName: string | null;
};

const SERVER_AUTH_SNAPSHOT: AuthState = {
  loggedIn: false,
  isAdmin: false,
  userName: null,
};

let cachedAuthSnapshot: AuthState = SERVER_AUTH_SNAPSHOT;

const readAuthState = (): AuthState => {
  const loggedIn = hasAccessToken();
  return {
    loggedIn,
    isAdmin: loggedIn && isAdminUser(),
    userName: getLoggedInUserName(),
  };
};

const getAuthSnapshot = (): AuthState => {
  const next = readAuthState();
  if (
    cachedAuthSnapshot.loggedIn === next.loggedIn
    && cachedAuthSnapshot.isAdmin === next.isAdmin
    && cachedAuthSnapshot.userName === next.userName
  ) {
    return cachedAuthSnapshot;
  }
  cachedAuthSnapshot = next;
  return cachedAuthSnapshot;
};

const getServerAuthSnapshot = (): AuthState => SERVER_AUTH_SNAPSHOT;

const subscribeAuth = () => () => {};

const NAV_ITEMS: NavItem[] = [
  { href: "/users", label: "社員一覧" },
  { href: "/seat-actions", label: "座席操作" },
  { href: "/current-seat-lookup", label: "現在位置照会" },
];

export default function GlobalHeaderNav() {
  const pathname = usePathname();
  const router = useRouter();
  const authState = useSyncExternalStore(subscribeAuth, getAuthSnapshot, getServerAuthSnapshot);
  const [showLogoutConfirm, setShowLogoutConfirm] = useState(false);
  const { loggedIn, isAdmin, userName } = authState;

  const getNavLinkClassName = (href: string) => {
    return pathname === href ? `${styles.link} ${styles.active}` : styles.link;
  };

  const onLogin = useCallback(() => {
    router.push("/login");
  }, [router]);

  const onLogoutClick = useCallback(() => {
    setShowLogoutConfirm(true);
  }, []);

  const onCancelLogout = useCallback(() => {
    setShowLogoutConfirm(false);
  }, []);

  const onConfirmLogout = useCallback(() => {
    logout();
    setShowLogoutConfirm(false);
    router.push("/login");
  }, [router]);

  useEffect(() => {
    if (!showLogoutConfirm) return;

    const handleKeyDown = (event: KeyboardEvent) => {
      if (event.key === "Escape") {
        event.preventDefault();
        onCancelLogout();
      }
    };

    window.addEventListener("keydown", handleKeyDown);
    return () => window.removeEventListener("keydown", handleKeyDown);
  }, [showLogoutConfirm, onCancelLogout]);

  if (pathname === "/login") {
    return null;
  }

  return (
    <header className={styles.header}>
      <div className={styles.inner}>
        <Link href="/users" className={styles.brand}>
          OfficeNavi
        </Link>
        <nav className={styles.nav} aria-label="global">
          {loggedIn && (
            <span className={styles.userStatus} aria-label={`ログイン中ユーザー: ${userName ?? "ユーザ"}`}>
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
                className={getNavLinkClassName(item.href)}
              >
                {item.label}
              </Link>
            );
          })}
          {isAdmin && (
            <Link
              href="/users/new"
              aria-current={pathname === "/users/new" ? "page" : undefined}
              className={getNavLinkClassName("/users/new")}
            >
              社員登録
            </Link>
          )}
          <button
            type="button"
            onClick={loggedIn ? onLogoutClick : onLogin}
            className={styles.logoutButton}
            aria-label={loggedIn ? "ログアウト" : "ログイン"}
          >
            {loggedIn ? "ログアウト" : "ログイン"}
          </button>
        </nav>
      </div>

      {showLogoutConfirm && (
        <div className={styles.modalOverlay} role="presentation" onClick={onCancelLogout}>
          <div
            className={styles.modal}
            role="dialog"
            aria-modal="true"
            aria-labelledby="logout-confirm-title"
            aria-describedby="logout-confirm-description"
            onClick={(event) => event.stopPropagation()}
          >
            <h2 id="logout-confirm-title" className={styles.modalTitle}>ログアウトしますか？</h2>
            <p id="logout-confirm-description" className={styles.modalDescription}>未保存の入力内容は失われる可能性があります。</p>
            <div className={styles.modalActions}>
              <button type="button" className={styles.cancelButton} onClick={onCancelLogout}>
                いいえ
              </button>
              <button type="button" className={styles.confirmButton} onClick={onConfirmLogout}>
                はい
              </button>
            </div>
          </div>
        </div>
      )}
    </header>
  );
}
