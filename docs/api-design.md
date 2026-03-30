# OfficeNavi API設計書（MVP）

## 1. 目的

本書は、フリーアドレス在籍管理アプリ「OfficeNavi」のMVP向けREST API仕様を定義する。

MVP対象は以下とする。

- 社員の登録
- 社員一覧の取得
- 社員の現在位置（座席）の登録
- 社員の現在位置の取得
- 社員の退席
- 認証・認可
  ※ 履歴参照API、論理削除はMVP対象外。

---

## 2. 前提

### 2.1 技術前提

- Backend: Spring Boot / Java 21
- DB: PostgreSQL
- API形式: JSON

### 2.2 データモデル（MVPで利用）

- `users`
  - `id`, `name`, `email`, `password_hash`, `created_at`, `updated_at`
- `seats`
  - `id`, `name`, `location`, `created_at`, `updated_at`
- `user_seats`
  - `id`, `user_id`, `seat_id`, `start_time`, `end_time`, `created_at`

---

## 3. API共通仕様

### 3.1 Base URL

- `/api`

### 3.2 ヘッダ

- Request: `Content-Type: application/json`（POST時）
- Response: `Content-Type: application/json`

### 3.3 日時フォーマット

- ISO 8601形式（例: `2026-02-19T10:00:00Z`）

### 3.4 共通エラーレスポンス

```json
{
  "code": "VALIDATION_ERROR",
  "message": "入力値が不正です",
  "details": [
    {
      "field": "email",
      "reason": "メールアドレスの形式が不正です"
    }
  ],
  "timestamp": "2026-02-19T10:00:00Z"
}
```

- MVPでは`reason`は日本語文言で返却する（将来、多言語対応時は`code`中心の運用へ移行可能）。

### 3.5 ステータスコード方針

- `200 OK`: 取得成功
- `201 Created`: 登録成功
- `400 Bad Request`: 入力不正
- `404 Not Found`: 対象リソースなし
- `409 Conflict`: 一意制約違反などの競合
- `500 Internal Server Error`: 想定外エラー

---

## 4. エンドポイント一覧（MVP）

| No  | Method | Path                               | 用途               |
| --- | ------ | ---------------------------------- | ------------------ |
| 1   | POST   | `/api/users`                       | 社員登録           |
| 2   | GET    | `/api/users`                       | 社員一覧取得       |
| 3   | GET    | `/api/seats`                       | 座席一覧取得       |
| 4   | POST   | `/api/user-seats`                  | 社員の現在位置登録 |
| 5   | GET    | `/api/users/{userId}/current-seat` | 社員の現在位置取得 |
| 6   | POST   | `/api/user-seats/leave`            | 社員の退席         |

---

## 5. API詳細

## 5.1 社員登録

- Method: `POST`
- Path: `/api/users`

### Request Body

```json
{
  "name": "山田 太郎",
  "email": "taro.yamada@example.com",
  "password": "Passw0rd!"
}
```

#### UserRegisterRequest バリデーション定義

| 項目       | 必須 | 条件                                               | エラー時の理由（例）                                              |
| ---------- | ---- | -------------------------------------------------- | ----------------------------------------------------------------- |
| `name`     | 必須 | 1〜100文字、空白のみ不可                           | `名前は1〜100文字で入力してください`                              |
| `email`    | 必須 | メール形式、255文字以内、重複不可                  | `メールアドレスの形式が不正です`                                  |
| `password` | 必須 | 8〜72文字、英大文字/英小文字/数字を各1文字以上含む | `パスワードは英大文字・英小文字・数字を各1文字以上含めてください` |

※ Controllerでは`@Valid`を利用し、入力不正時は`400 Bad Request`を返却する。

※ `password` の実装想定（例）: `^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$`

### Success Response

- Status: `201 Created`

```json
{
  "id": 1,
  "name": "山田 太郎",
  "email": "taro.yamada@example.com",
  "createdAt": "2026-02-19T10:00:00Z"
}
```

### Error

- `400` バリデーションエラー
- `409` email重複

---

## 5.2 社員一覧取得

- Method: `GET`
- Path: `/api/users`

### Query Parameters（任意）

- `name`: 部分一致検索

### Success Response

- Status: `200 OK`

```json
[
  {
    "id": 1,
    "name": "山田 太郎",
    "email": "taro.yamada@example.com"
  },
  {
    "id": 2,
    "name": "佐藤 花子",
    "email": "hanako.sato@example.com"
  }
]
```

---

## 5.3 座席一覧取得

- Method: `GET`
- Path: `/api/seats`

### Success Response

- Status: `200 OK`

```json
[
  {
    "id": 10,
    "name": "A-01",
    "location": "3F East"
  },
  {
    "id": 11,
    "name": "A-02",
    "location": "3F East"
  }
]
```

---

## 5.4 社員の現在位置登録

- Method: `POST`
- Path: `/api/user-seats`

### Request Body

```json
{
  "seatId": 10
}
```

### 業務ルール

1. `userId`が存在しない場合は`404`
2. `seatId`が存在しない場合は`404`
3. 指定された`seatId`を他ユーザーが利用中（`end_time IS NULL`）の場合は`409`
4. 同一ユーザーの有効レコード（`end_time IS NULL`）がある場合、`end_time = now()`でクローズ
5. 新規レコードを`user_seats`へ登録（`start_time = now()`, `end_time = NULL`）

### Success Response

- Status: `201 Created`

```json
{
  "userSeatId": 100,
  "userId": 1,
  "seatId": 10,
  "startTime": "2026-02-19T10:05:00Z"
}
```

### Error

- `400` 入力不正
- `404` user/seat未存在
- `409` 指定seatが他ユーザーにより利用中

---

## 5.5 社員の現在位置取得

- Method: `GET`
- Path: `/api/users/{userId}/current-seat`

### Success Response

- Status: `200 OK`

```json
{
  "userId": 1,
  "userName": "山田 太郎",
  "seat": {
    "id": 10,
    "name": "A-01",
    "location": "3F East"
  },
  "since": "2026-02-19T10:05:00Z"
}
```

### Not Found

- Status: `404 Not Found`

```json
{
  "code": "CURRENT_SEAT_NOT_FOUND",
  "message": "対象ユーザーの現在位置が登録されていません",
  "details": [],
  "timestamp": "2026-02-19T10:10:00Z"
}
```

---

## 5.6 社員の退席

- Method: `POST`
- Path: `/api/user-seats/leave`

### Request Body

なし（認証済みユーザーに紐づく現在の在席情報を対象とする）

### 業務ルール

1. `userId`が存在しない場合は`404`
2. 対象ユーザーの有効レコード（`end_time IS NULL`）がない場合は`404`
3. 対象ユーザーの有効レコードを1件クローズする（`end_time = now()`）

### Success Response

- Status: `200 OK`

```json
{
  "userId": 1,
  "leftAt": "2026-02-19T10:20:00Z"
}
```

### Error

- `400` 入力不正
- `404` user未存在 / 在席情報なし

---

## 6. 例外・エラーハンドリング設計

- `@RestControllerAdvice`で例外を集約し、エラー形式を統一
- 想定例外とHTTPマッピング
  - `MethodArgumentNotValidException` -> `400`
  - `ResourceNotFoundException`（業務独自） -> `404`
  - `SeatAlreadyInUseException`（業務独自） -> `409`
  - `DataIntegrityViolationException`（一意制約違反など） -> `409`
  - その他 -> `500`

---

## 7. レイヤ責務（実装方針）

- Controller
  - リクエスト受け取り、バリデーション、レスポンス返却
- Service
  - 業務ルール適用（現在位置更新時のクローズ処理など）
- Repository
  - DBアクセスのみを担当

---

## 8. 今後拡張（MVP外）

- 認証・認可（JWT等）
- 在席履歴一覧API（期間指定）
- 社員詳細取得API
- ページング・ソート
- 論理削除/監査ログ
