# OfficeNavi Frontend

Next.js（App Router）で実装するOfficeNaviのフロントエンドです。

## 前提

- Node.js 22.13.0 以上を推奨
- npm 10 以上

## セットアップ

1. 依存関係をインストール

```bash
npm install
```

2. 環境変数ファイルを作成

```bash
cp .env.example .env.local
```

3. 開発サーバーを起動

```bash
npm run dev
```

## 利用可能なコマンド

```bash
npm run dev
npm run build
npm run start
npm run lint
npm run format
npm run format:check
```

## 環境変数

- NEXT_PUBLIC_API_BASE_URL
  - バックエンドAPIのベースURL
  - 例: http://localhost:8080/api

## APIクライアント

- 共通クライアント: `src/lib/api/client.ts`
- エンドポイント別関数: `src/lib/api/`
- 型定義: `src/types/api.ts`

現在実装しているAPI関数:

- `getUsers`
- `registerUser`
- `getSeats`
- `registerCurrentSeat`
- `leaveCurrentSeat`
- `getCurrentSeat`
