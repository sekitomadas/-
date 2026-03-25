import type { ApiErrorDetail, ApiErrorResponse, ApiValidationError } from "@/types/api";

const DEFAULT_HEADERS = {
  "Content-Type": "application/json",
} as const;

const ACCESS_TOKEN_STORAGE_KEY = "officenavi_access_token";
const LOGGED_IN_USER_NAME_STORAGE_KEY = "officenavi_logged_in_user_name";

export class ApiClientError extends Error {
  status: number;
  code?: string;
  details: ApiErrorDetail[];
  fieldErrors: ApiValidationError[];

  constructor(status: number, response: ApiErrorResponse) {
    super(response.message || "API request failed");
    this.name = "ApiClientError";
    this.status = status;
    this.code = response.code;
    this.details = response.details ?? [];
    this.fieldErrors = response.errors ?? [];
  }
}

const getAccessToken = () => {
  if (typeof window === "undefined") {
    return null;
  }
  return window.localStorage.getItem(ACCESS_TOKEN_STORAGE_KEY);
};

export const hasAccessToken = () => {
  return !!getAccessToken();
};

export const setAccessToken = (token: string | null) => {
  if (typeof window === "undefined") {
    return;
  }

  if (!token) {
    window.localStorage.removeItem(ACCESS_TOKEN_STORAGE_KEY);
    return;
  }

  window.localStorage.setItem(ACCESS_TOKEN_STORAGE_KEY, token);
};

export const getLoggedInUserName = () => {
  if (typeof window === "undefined") {
    return null;
  }

  const userName = window.localStorage.getItem(LOGGED_IN_USER_NAME_STORAGE_KEY);
  if (userName === null) {
    return null;
  }

  const normalized = userName.trim();
  return normalized.length > 0 ? normalized : null;
};

export const setLoggedInUserName = (userName: string | null) => {
  if (typeof window === "undefined") {
    return;
  }

  if (!userName || userName.trim().length === 0) {
    window.localStorage.removeItem(LOGGED_IN_USER_NAME_STORAGE_KEY);
    return;
  }

  window.localStorage.setItem(LOGGED_IN_USER_NAME_STORAGE_KEY, userName.trim());
};

const getApiBaseUrl = () => {
  const baseUrl = process.env.NEXT_PUBLIC_API_BASE_URL;

  if (!baseUrl) {
    throw new Error("NEXT_PUBLIC_API_BASE_URL is not configured.");
  }

  return baseUrl.replace(/\/$/, "");
};

const buildUrl = (path: string) => {
  if (path.startsWith("http://") || path.startsWith("https://")) {
    return path;
  }

  const normalizedPath = path.startsWith("/") ? path : `/${path}`;
  return `${getApiBaseUrl()}${normalizedPath}`;
};

const parseJson = async <T>(response: Response): Promise<T | null> => {
  const contentType = response.headers.get("content-type");

  if (!contentType?.includes("application/json")) {
    return null;
  }

  return (await response.json()) as T;
};

const toApiErrorResponse = (response: ApiErrorResponse | null): ApiErrorResponse => {
  if (response) {
    return response;
  }

  return {
    message: "通信エラーが発生しました",
  };
};

export const apiRequest = async <T>(path: string, init?: RequestInit): Promise<T> => {
  const accessToken = getAccessToken();

  const response = await fetch(buildUrl(path), {
    ...init,
    headers: {
      ...DEFAULT_HEADERS,
      ...(accessToken ? { Authorization: `Bearer ${accessToken}` } : {}),
      ...(init?.headers ?? {}),
    },
    cache: "no-store",
  });

  const body = await parseJson<T | ApiErrorResponse>(response);

  if (!response.ok) {
    if (response.status === 401) {
      setAccessToken(null);
      setLoggedInUserName(null);
    }
    throw new ApiClientError(response.status, toApiErrorResponse(body as ApiErrorResponse | null));
  }

  if (body === null) {
    throw new Error("Expected a JSON response but received none.");
  }

  return body as T;
};