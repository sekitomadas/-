"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { ApiClientError, getUsers } from "@/lib/api";
import type { User } from "@/types/api";
import styles from "./users-page.module.css";

export default function UsersPage() {
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const load = async () => {
      try {
        setLoading(true);
        setError("");
        const data = await getUsers();
        setUsers(data);
      } catch (err) {
        if (err instanceof ApiClientError) {
          setError(err.message);
        } else {
          setError("社員一覧の取得に失敗しました");
        }
      } finally {
        setLoading(false);
      }
    };

    void load();
  }, []);

  return (
    <div className={styles.page}>
      <main className={styles.main}>
        <header className={styles.header}>
          <div>
            <p className={styles.kicker}>Employees</p>
            <h1>社員一覧</h1>
          </div>
          <div className={styles.links}>
            <Link href="/">トップ</Link>
            <Link href="/users/new">新規登録</Link>
            <Link href="/seat-actions">座席操作</Link>
          </div>
        </header>

        {loading && <p className={styles.message}>読み込み中です...</p>}
        {!loading && error && <p className={styles.error}>{error}</p>}
        {!loading && !error && users.length === 0 && (
          <p className={styles.message}>社員データがありません</p>
        )}

        {!loading && !error && users.length > 0 && (
          <div className={styles.tableWrap}>
            <table className={styles.table}>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>名前</th>
                  <th>メールアドレス</th>
                </tr>
              </thead>
              <tbody>
                {users.map((user) => (
                  <tr key={user.id}>
                    <td>{user.id}</td>
                    <td>{user.name}</td>
                    <td>{user.email}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </main>
    </div>
  );
}