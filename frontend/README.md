# OfficeNavi Frontend

OfficeNavi のフロントエンド実装です。Next.js App Router を使用し、社員一覧、社員登録、座席登録・退席、現在位置照会を提供します。

## 実装済み画面

- `/`
  - エントリーポイント
  - ログイン状態に応じて `/users` または `/login` へ自動リダイレクト
- `/login`
  - ログイン画面
- `/users`
  - 社員一覧画面
- `/users/new`
  - 社員登録画面
  - 管理者のみ利用可能
- `/seat-actions`
  - 自身の座席登録・退席画面
- `/current-seat-lookup`
  - 現在位置照会画面
  - 社員IDによる検索とフロアマップ表示に対応
  - 空席クリック時の確認ダイアログから在席登録が可能

## 主な仕様

- グローバルヘッダーから各画面へ遷移可能
- 未ログイン時は各業務画面から `/login` へリダイレクト
- 管理者以外は `/users/new` へアクセス不可
- 現在位置照会画面では以下を提供
  - 全件照会
  - 社員ID指定での照会
  - フロアマップでの在席状況表示
  - 空席選択時の確認ダイアログ付き着席登録

## 技術スタック

- Next.js 16.2.0
- React 19
- TypeScript
- ESLint 9

## 前提

- Node.js 22.13.0 以上を推奨
- npm 10 以上

## セットアップ

1. 依存関係をインストール

```bash
npm install
```

2. バックエンドのデータベースを初期化

```bash
cd ..
psql -d postgres -f docs/00_create_database.sql
psql -d office-navi -f docs/DDL.sql
psql -d office-navi -f docs/DML.sql
cd frontend
```

3. 環境変数ファイルを作成

```bash
cp .env.example .env.local
```

4. バックエンド API の接続先を `.env.local` に設定

```env
NEXT_PUBLIC_API_BASE_URL=http://localhost:8080/api
```

5. バックエンドとフロントエンドを起動

```bash
cd ..
./mvnw spring-boot:run
```

別ターミナルで:

```bash
cd frontend
npm run dev
```

既存DB向けのスキーマ変更が必要な場合は、`docs/NN_description.sql` 形式で新しいマイグレーションを追加して適用してください。

## 利用可能なコマンド

```bash
npm run dev
npm run build
npm run start
npm run lint
```

## ディレクトリ構成

```text
src/
  app/
    page.tsx                  # エントリーポイント（自動リダイレクト）
    login/                    # ログイン画面
    users/                    # 社員一覧
    users/new/                # 社員登録
    seat-actions/             # 座席登録・退席
    current-seat-lookup/      # 現在位置照会
  components/
    global-header-nav.tsx     # グローバルヘッダー
    floor-map.tsx             # フロアマップ表示
  lib/api/
    client.ts                 # 共通APIクライアント
    auth.ts                   # ログイン・ログアウト
    users.ts                  # 社員系API
    seats.ts                  # 座席一覧API
    user-seats.ts             # 在席系API
  types/
    api.ts                    # APIレスポンス/リクエスト型
```

## API クライアント

- 共通クライアント: `src/lib/api/client.ts`
- エンドポイント別関数: `src/lib/api/`
- 型定義: `src/types/api.ts`

現在利用している主な API 関数:

- `login`
- `logout`
- `getUsers`
- `registerUser`
- `getSeats`
- `getCurrentSeat`
- `getAllCurrentSeats`
- `getCurrentSeatByUserId`
- `registerCurrentSeat`
- `leaveCurrentSeat`

## 補足

- 認証状態はブラウザ側の保存情報をもとに管理しています。
- `/` は専用UIを持たず、ログイン状態に応じた遷移のみを担当します。
