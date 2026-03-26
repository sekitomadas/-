"use client";

import { useEffect } from "react";
import { useRouter } from "next/navigation";
import { hasAccessToken } from "@/lib/api/client";
import styles from "./page.module.css";

export default function Home() {
  const router = useRouter();

  useEffect(() => {
    if (hasAccessToken()) {
      router.replace("/users");
      return;
    }

    router.replace("/login");
  }, [router]);

  return (
    <div className={styles.page}>
      <main className={styles.main}>
        <p className={styles.message}>ページを移動しています...</p>
      </main>
    </div>
  );
}
