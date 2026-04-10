"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { ApiClientError, login } from "@/lib/api";
import { hasAccessToken } from "@/lib/api/client";
import styles from "./login-page.module.css";

export default function LoginPage() {
  const router = useRouter();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    if (hasAccessToken()) {
      router.replace("/users");
    }
  }, [router]);

  const onSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setError("");

    if (!email || !password) {
      setError("emailとpasswordを入力してください");
      return;
    }

    try {
      setSubmitting(true);
      await login({ email, password });
      router.push("/users");
    } catch (err) {
      if (err instanceof ApiClientError) {
        setError(err.message || "ログインに失敗しました");
      } else {
        setError("通信エラーが発生しました");
      }
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className={styles.page}>
      <main className={styles.main}>
        <header className={styles.header}>
          <div>
            <p className={styles.kicker}>OfficeNavi Auth</p>
            <h1>ログイン</h1>
          </div>
        </header>

        <form className={styles.form} onSubmit={onSubmit} noValidate>
          <label className={styles.field}>
            <span>email</span>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="example@example.com"
              autoComplete="email"
            />
          </label>

          <label className={styles.field}>
            <span>password</span>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="password"
              autoComplete="current-password"
            />
          </label>

          {error && <p className={styles.error}>{error}</p>}

          <button type="submit" disabled={submitting}>
            {submitting ? "ログイン中..." : "ログイン"}
          </button>
        </form>
      </main>
    </div>
  );
}
