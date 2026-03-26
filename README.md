# フリーアドレス在籍管理アプリ

## 概要

フリーアドレス制のオフィスにおいて、社員の現在地や在席状況を共有するための業務アプリケーションです。

バックエンドは Spring Boot による REST API、フロントエンドは Next.js による画面群で構成されています。
現在は以下の機能を利用できます。

- ログイン
- 社員一覧表示
- 社員登録
- 座席一覧取得
- 着席登録
- 退席登録
- 現在位置照会
- フロアマップからの空席選択と着席登録

## 背景・課題

フリーアドレス環境では、社員の所在が分かりづらく、
コミュニケーションや業務効率の低下につながることがあります。

本プロジェクトでは、社員が自身の現在位置（席・エリア）を登録し、
他の社員がそれを確認できる仕組みを提供します。

## 想定ユーザー

- 一般社員
- 管理者

一般社員は自身の着席・退席や現在位置確認を行い、管理者はそれに加えて社員登録を行います。

## 想定利用シーン

- 社内での在籍確認
- 来客対応時の所在確認
- 空席の把握と座席利用の更新
- フロアマップからの座席選択

---

## 開発方針

- バックエンドは REST API として設計し、フロントエンドから同一 API を利用する
- フロントエンドは業務導線を重視し、各画面を App Router ベースで分離する
- Controller / Service / Repository の責務を明確に分離する
- バックエンドは View（HTML）を返却しない設計とする
- JSON形式での入出力を前提とする
- 認証は JWT ベースで扱い、フロントエンドはログイン状態に応じて画面遷移を制御する

---

## 開発目的

- フリーアドレス環境における所在把握の課題を解決する
- REST APIを中心とした設計・実装経験を積む
- レイヤードアーキテクチャを意識した設計力を示す
- 転職用ポートフォリオとして、設計意図を説明できる成果物を作成する
- フロントエンドとバックエンドを連携させた業務アプリの一連の実装を示す

---

## 技術選定

### バックエンド

- Java 21（LTS）
- Spring Boot
- Spring Security
- JWT
- Maven
- PostgreSQL

### フロントエンド

- Next.js 16
- React 19
- TypeScript
- ESLint

### 採用理由

- **Java 21**
  - LTS（長期サポート）版であり、業務利用を想定した選択
- **Spring Boot**
  - REST API 開発に必要な機能が標準で揃っている
  - 実務での採用実績が高く、再現性のある構成にしやすい
- **PostgreSQL**
  - RDBの基本設計（正規化・制約）を意識した設計が可能
  - 業務システムでの利用実績が多い
- **Maven**
  - 設定の可読性が高く、チーム開発を想定した構成にしやすいため
- **Spring Security / JWT**
  - API の認証・認可を HTTP セッションに依存せず扱える
  - フロントエンドとの疎結合な構成を維持しやすい
- **Next.js（App Router）**
  - 画面単位の構成が明確で、業務画面を整理しやすい
  - React ベースで API クライアントとの連携を実装しやすい
- **TypeScript**
  - API の入出力型を明示でき、画面実装時の不整合を早期に検出しやすい

---

## 機能要件

### 認証

- ユーザーがログインできる
- 未認証ユーザーは業務画面へ直接アクセスできない
- 管理者のみ社員登録画面を利用できる

### 社員管理

- 社員の一覧を取得できる
- 社員を登録できる

### 座席管理

- 座席一覧を取得できる

### 在席情報管理

- 社員が現在の在席座席を登録できる
- 社員の現在位置を確認できる
- 社員が退席できる
- 社員IDを指定して現在位置を検索できる
- フロアマップ上で空席を確認できる
- フロアマップの空席を選択して着席登録できる

---

## 非機能要件

- レスポンス形式はJSONとする
- HTTPステータスコードを適切に返却する
- 入力値チェックを行い、不正なリクエストはエラーとして返却する
- 例外は共通ハンドリングし、レスポンス形式を統一する
- DB アクセスはRepository層に集約する
- ロジックはService層に集約し、Controllerを薄く保つ
- フロントエンドはログイン状態に応じてリダイレクト制御を行う
- フロントエンドは API エラーを画面メッセージとして利用者に明示する
- フロアマップ操作時は確認ダイアログを表示し、誤操作を抑制する

## API仕様ドキュメント

- APIの詳細仕様（リクエスト/レスポンス/バリデーション条件）は `docs/api-design.md` を参照
- ER図は `docs/er.md` を参照
- SQL 初期化スクリプトは `docs/DDL.sql` / `docs/DML.sql` を参照
- 補助スクリプトとして以下も用意
  - `docs/00_create_database.sql`: `office-navi` データベース自体を再作成する
- スキーマ変更が発生した場合は、`docs/NN_description.sql` 形式で新しいマイグレーションを追加

---

## MVP（最小構成）

本プロジェクトの MVP は以下とする。

- 社員の登録
- 社員一覧の取得
- 社員の現在位置（座席）の登録
- 社員の現在位置の取得
- 社員の退席

現在は上記に加えて、以下も実装済みです。

- ログイン認証
- 管理者向け社員登録画面
- 現在位置の全件照会
- フロアマップによる空席確認と着席登録

※ 履歴管理は引き続き MVP 対象外です。

---

## フロントエンド開発

- フロントエンドは `frontend/` に配置
- 技術スタックは Next.js（App Router）+ TypeScript
- 主要画面は `/login`, `/users`, `/users/new`, `/seat-actions`, `/current-seat-lookup`
- `/` はログイン状態に応じて `/users` または `/login` へ自動遷移するエントリーポイント
- 詳細は `frontend/README.md` を参照

### 起動手順

事前に PostgreSQL を起動し、データベースを初期化します。

1. データベース自体を作成する場合は、メンテナンスDB（例: `postgres`）へ接続して `docs/00_create_database.sql` を実行
2. `office-navi` データベースへ接続し、`docs/DDL.sql` を実行してスキーマを作成
3. 続けて `docs/DML.sql` を実行して初期データを投入

例:

```bash
psql -d postgres -f docs/00_create_database.sql
psql -d office-navi -f docs/DDL.sql
psql -d office-navi -f docs/DML.sql
```

既存DB向けの変更が必要な場合は、`docs/NN_description.sql` 形式で新しいマイグレーションを追加して適用します。

バックエンド起動:

```bash
./mvnw spring-boot:run
```

フロントエンド起動:

```bash
cd frontend
npm install
cp .env.example .env.local
npm run dev
```

必要に応じて以下の環境変数を設定します。

バックエンド:

```env
DB_HOST=localhost
DB_PORT=5432
OFFICE_NAVI_DB_NAME=office-navi
OFFICE_NAVI_DB_USER=postgres
OFFICE_NAVI_DB_PASS=postgres
FRONTEND_ORIGIN=http://localhost:3000
JWT_SECRET=officenavi-local-secret-key-for-hs256-token-signing
JWT_EXPIRATION_SECONDS=3600
```

フロントエンド:

```env
NEXT_PUBLIC_API_BASE_URL=http://localhost:8080/api
```

### 主なコマンド

バックエンド:

```bash
./mvnw spring-boot:run
./mvnw test
```

フロントエンド:

```bash
cd frontend
npm run dev
npm run lint
npm run build
```

---

## リポジトリ構成

```text
.
├── docs/                  # API設計、ER図、DDL/DML、補助資料
├── frontend/              # Next.js フロントエンド
├── src/main/java/         # Spring Boot アプリケーション本体
├── src/main/resources/    # application.yml など設定
└── src/test/java/         # バックエンドテスト
```

## 現在の実装状況

- バックエンド API は社員・座席・在席・認証を提供
- フロントエンドは主要業務画面を実装済み
- 現在位置照会画面ではフロアマップ上の空席選択から着席登録可能
- ログイン状態に応じて画面アクセスを制御
