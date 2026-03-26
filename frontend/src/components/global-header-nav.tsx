"use client";

import { useCallback, useEffect, useState } from "react";
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

const NAV_ITEMS: NavItem[] = [
  { href: "/", label: "トップ" },
  { href: "/users", label: "社員一覧" },
  { href: "/seat-actions", label: "座席操作" },
  { href: "/current-seat-lookup", label: "現在位置照会" },
];

export default function GlobalHeaderNav() {
  const pathname = usePathname();
  const router = useRouter();
  const [authState, setAuthState] = useState<AuthState>({
    loggedIn: false,
    isAdmin: false,
    userName: null,
  });
  const [showLogoutConfirm, setShowLogoutConfirm] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const syncAuthStateFromStorage = () => {
    const loggedIn = hasAccessToken();
    setAuthState({
      loggedIn,
      isAdmin: loggedIn && isAdminUser(),
      userName: getLoggedInUserName(),
    });
  };

  useEffect(() => {
    syncAuthStateFromStorage();
    setIsSubmitting(false);
    setShowLogoutConfirm(false);
  }, [pathname]);

  const onLogin = useCallback(() => {
    router.push("/login");
  }, [router]);

  const onLogoutClick = useCallback(() => {
    setShowLogoutConfirm(true);
  }, []);

  const onCancelLogout = useCallback(() => {
    if (isSubmitting) return;
    setShowLogoutConfirm(false);
  }, [isSubmitting]);

  const onConfirmLogout = useCallback(() => {
    if (isSubmitting) return;
    setIsSubmitting(true);
    try {
      logout();
      setAuthState({ loggedIn: false, isAdmin: false, userName: null });
      setShowLogoutConfirm(false);
      router.push("/login");
    } finally {
      setIsSubmitting(false);
    }
  }, [isSubmitting, router]);

  useEffect(() => {
    if (!showLogoutConfirm) return;

    const handleKeyDown = (event: KeyboardEvent) => {
      if (event.key === "Escape") {
        event.preventDefault();
        onCancelLogout();
      }
      if (event.key === "Enter") {
        event.preventDefault();
        onConfirmLogout();
      }
    };

    window.addEventListener("keydown", handleKeyDown);
    return () => window.removeEventListener("keydown", handleKeyDown);
  }, [showLogoutConfirm, onCancelLogout, onConfirmLogout]);

  if (pathname === "/login") {
    return null;
  }

  return (
    <header className={styles.header}>
      <div className={styles.inner}>
        <Link href="/" className={styles.brand}>
          OfficeNavi
        </Link>
        <nav className={styles.nav} aria-label="global">
          {authState.loggedIn && (
            <span className={styles.userStatus} aria-label={`ログイン中ユーザー: ${authState.userName ?? "ユーザ"}`}>
              ログイン中: {authState.userName ?? "ユーザ"}
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
          {authState.isAdmin && (
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
            onClick={authState.loggedIn ? onLogoutClick : onLogin}
            className={styles.logoutButton}
            aria-label={authState.loggedIn ? "ログアウト" : "ログイン"}
          >
            {authState.loggedIn ? "ログアウト" : "ログイン"}
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
            onClick={(event) => event.stopPropagation()}
          >
            <h2 id="logout-confirm-title" className={styles.modalTitle}>ログアウトしますか？</h2>
            <p className={styles.modalDescription}>未保存の入力内容は失われる可能性があります。</p>
            <div className={styles.modalActions}>
              <button type="button" className={styles.cancelButton} onClick={onCancelLogout} disabled={isSubmitting}>
                いいえ
              </button>
              <button type="button" className={styles.confirmButton} onClick={onConfirmLogout} disabled={isSubmitting}>
                {isSubmitting ? "処理中..." : "はい"}
              </button>
            </div>
          </div>
        </div>
      )}
    </header>
  );
}
