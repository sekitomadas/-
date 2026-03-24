"use client";

import { useMemo, useState } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { ApiClientError, registerUser } from "@/lib/api";
import type { UserRegisterRequest } from "@/types/api";
import styles from "./users-new-page.module.css";

type FormErrors = Partial<Record<keyof UserRegisterRequest, string>>;

const NO_WHITESPACE_PATTERN = /^(?!.*[\s　]).+$/;
const EMAIL_PATTERN = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
const PASSWORD_PATTERN = /^(?!.*[\s　])(?=.*[a-z])(?=.*[A-Z])(?=.*\d).+$/;

const validate = (payload: UserRegisterRequest): FormErrors => {
  const errors: FormErrors = {};

  if (!payload.name) {
    errors.name = "名前は必須です";
  } else if (payload.name.length > 100) {
    errors.name = "名前は1~100文字以内で入力してください";
  } else if (!NO_WHITESPACE_PATTERN.test(payload.name)) {
    errors.name = "名前に空白（半角/全角）は使用できません";
  }

  if (!payload.email) {
    errors.email = "メールアドレスは必須です";
  } else if (payload.email.length < 8 || payload.email.length > 255) {
    errors.email = "メールアドレスは8~255文字以内で入力してください";
  } else if (!EMAIL_PATTERN.test(payload.email)) {
    errors.email = "有効なメールアドレスを入力してください";
  } else if (!NO_WHITESPACE_PATTERN.test(payload.email)) {
    errors.email = "メールアドレスに空白（半角/全角）は使用できません";
  }

  if (!payload.password) {
    errors.password = "パスワードは必須です";
  } else if (payload.password.length < 8 || payload.password.length > 255) {
    errors.password = "パスワードは8~255文字以内で入力してください";
  } else if (!PASSWORD_PATTERN.test(payload.password)) {
    errors.password = "パスワードは空白なしで、大文字・小文字・数字をそれぞれ1文字以上含めてください";
  }

  return errors;
};

const toServerFieldErrors = (err: ApiClientError): FormErrors => {
  const serverFieldErrors: FormErrors = {};

  for (const item of err.fieldErrors) {
    if (item.field === "name" || item.field === "email" || item.field === "password") {
      serverFieldErrors[item.field] = item.message;
    }
  }

  for (const item of err.details) {
    if (item.field === "name" || item.field === "email" || item.field === "password") {
      serverFieldErrors[item.field] = item.reason;
    }
  }

  return serverFieldErrors;
};

export default function NewUserPage() {
  const router = useRouter();

  const [form, setForm] = useState<UserRegisterRequest>({
    name: "",
    email: "",
    password: "",
  });
  const [fieldErrors, setFieldErrors] = useState<FormErrors>({});
  const [error, setError] = useState("");
  const [submitting, setSubmitting] = useState(false);

  const hasClientError = useMemo(() => {
    const errors = validate(form);
    return Object.keys(errors).length > 0;
  }, [form]);

  const onChange = (key: keyof UserRegisterRequest, value: string) => {
    setForm((prev) => ({ ...prev, [key]: value }));
  };

  const onBlur = (key: keyof UserRegisterRequest) => {
    const errors = validate(form);
    setFieldErrors((prev) => ({ ...prev, [key]: errors[key] }));
  };

  const onSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const errors = validate(form);
    setFieldErrors(errors);
    setError("");

    if (Object.keys(errors).length > 0) {
      return;
    }

    try {
      setSubmitting(true);
      await registerUser(form);
      router.push("/users");
    } catch (err) {
      if (err instanceof ApiClientError) {
        const serverFieldErrors = toServerFieldErrors(err);
        setFieldErrors(serverFieldErrors);

        if (Object.keys(serverFieldErrors).length > 0) {
          setError("");
        } else {
          setError(err.message || "登録に失敗しました");
        }
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
            <p className={styles.kicker}>New Employee</p>
            <h1>社員登録</h1>
          </div>
          <div className={styles.links}>
            <Link href="/">トップ</Link>
            <Link href="/users">社員一覧</Link>
          </div>
        </header>

        <form className={styles.form} onSubmit={onSubmit}>
          <label className={styles.field}>
            <span>名前</span>
            <input
              type="text"
              value={form.name}
              onChange={(e) => onChange("name", e.target.value)}
              onBlur={() => onBlur("name")}
              placeholder="例: yamada"
              autoComplete="name"
            />
            {fieldErrors.name && <small>{fieldErrors.name}</small>}
          </label>

          <label className={styles.field}>
            <span>メールアドレス</span>
            <input
              type="email"
              value={form.email}
              onChange={(e) => onChange("email", e.target.value)}
              onBlur={() => onBlur("email")}
              placeholder="example@example.com"
              autoComplete="email"
            />
            {fieldErrors.email && <small>{fieldErrors.email}</small>}
          </label>

          <label className={styles.field}>
            <span>パスワード</span>
            <input
              type="password"
              value={form.password}
              onChange={(e) => onChange("password", e.target.value)}
              onBlur={() => onBlur("password")}
              placeholder="英大文字・英小文字・数字を含む"
              autoComplete="new-password"
            />
            {fieldErrors.password && <small>{fieldErrors.password}</small>}
          </label>

          {error && <p className={styles.error}>{error}</p>}

          <button type="submit" disabled={submitting || hasClientError}>
            {submitting ? "登録中..." : "登録する"}
          </button>
        </form>
      </main>
    </div>
  );
}