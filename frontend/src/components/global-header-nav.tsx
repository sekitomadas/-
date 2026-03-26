"use client";

import { useCallback, useEffect, useRef, useState, useSyncExternalStore } from "react";
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

const getDisplayUserName = (userName: string | null) => userName ?? "ユーザ";

const getFocusableButtons = (
  cancelButton: HTMLButtonElement | null,
  confirmButton: HTMLButtonElement | null
) => {
  return [cancelButton, confirmButton].filter(
    (element): element is HTMLButtonElement => element !== null && !element.disabled
  );
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
  { href: "/", label: "トップ" },
  { href: "/users", label: "社員一覧" },
  { href: "/seat-actions", label: "座席操作" },
  { href: "/current-seat-lookup", label: "現在位置照会" },
];

export default function GlobalHeaderNav() {
  const pathname = usePathname();
  const router = useRouter();
  const authState = useSyncExternalStore(subscribeAuth, getAuthSnapshot, getServerAuthSnapshot);
  const [showLogoutConfirm, setShowLogoutConfirm] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const dialogRef = useRef<HTMLDivElement>(null);
  const cancelButtonRef = useRef<HTMLButtonElement>(null);
  const confirmButtonRef = useRef<HTMLButtonElement>(null);
  const previousFocusedElementRef = useRef<HTMLElement | null>(null);
  const { loggedIn, isAdmin, userName } = authState;
  const displayUserName = getDisplayUserName(userName);
  const isUsersNewActive = pathname === "/users/new";

  const getNavLinkClassName = (href: string) => {
    return pathname === href ? `${styles.link} ${styles.active}` : styles.link;
  };

  const onLogin = useCallback(() => {
    router.push("/login");
  }, [router]);

  const onLogoutClick = useCallback(() => {
    setIsSubmitting(false);
    setShowLogoutConfirm(true);
  }, []);

  const onCancelLogout = useCallback(() => {
    if (isSubmitting) return;
    setShowLogoutConfirm(false);
  }, [isSubmitting]);

  const onConfirmLogout = useCallback(() => {
    if (isSubmitting) return;
    setIsSubmitting(true);
    logout();
    router.push("/login");
  }, [isSubmitting, router]);

  useEffect(() => {
    if (!showLogoutConfirm) {
      previousFocusedElementRef.current?.focus();
      previousFocusedElementRef.current = null;
      return;
    }

    previousFocusedElementRef.current = document.activeElement instanceof HTMLElement
      ? document.activeElement
      : null;

    cancelButtonRef.current?.focus();
  }, [showLogoutConfirm]);

  useEffect(() => {
    if (!showLogoutConfirm) return;

    const dialog = dialogRef.current;
    if (!dialog) return;

    const handleKeyDown = (event: KeyboardEvent) => {
      if (event.key === "Escape") {
        event.preventDefault();
        onCancelLogout();
        return;
      }

      if (event.key !== "Tab") {
        return;
      }

      const focusableElements = getFocusableButtons(cancelButtonRef.current, confirmButtonRef.current);

      if (focusableElements.length === 0) {
        event.preventDefault();
        return;
      }

      const first = focusableElements[0];
      const last = focusableElements[focusableElements.length - 1];
      const active = document.activeElement;

      if (event.shiftKey) {
        if (active === first || !dialog.contains(active)) {
          event.preventDefault();
          last.focus();
        }
        return;
      }

      if (active === last || !dialog.contains(active)) {
        event.preventDefault();
        first.focus();
      }
    };

    dialog.addEventListener("keydown", handleKeyDown);
    return () => dialog.removeEventListener("keydown", handleKeyDown);
  }, [showLogoutConfirm, onCancelLogout]);

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
          {loggedIn && (
            <span className={styles.userStatus} aria-label={`ログイン中ユーザー: ${displayUserName}`}>
              ログイン中: {displayUserName}
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
              aria-current={isUsersNewActive ? "page" : undefined}
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
            ref={dialogRef}
            className={styles.modal}
            role="dialog"
            aria-modal="true"
            aria-labelledby="logout-confirm-title"
            aria-describedby="logout-confirm-description"
            aria-busy={isSubmitting}
            tabIndex={-1}
            onClick={(event) => event.stopPropagation()}
          >
            <h2 id="logout-confirm-title" className={styles.modalTitle}>ログアウトしますか？</h2>
            <p id="logout-confirm-description" className={styles.modalDescription}>未保存の入力内容は失われる可能性があります。</p>
            <div className={styles.modalActions}>
              <button type="button" className={styles.cancelButton} onClick={onCancelLogout} disabled={isSubmitting} ref={cancelButtonRef}>
                いいえ
              </button>
              <button type="button" className={styles.confirmButton} onClick={onConfirmLogout} disabled={isSubmitting} ref={confirmButtonRef}>
                {isSubmitting ? "処理中..." : "はい"}
              </button>
            </div>
          </div>
        </div>
      )}
    </header>
  );
}
