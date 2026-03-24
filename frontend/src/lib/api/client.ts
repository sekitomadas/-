import type { ApiErrorDetail, ApiErrorResponse, ApiValidationError } from "@/types/api";

const DEFAULT_HEADERS = {
  "Content-Type": "application/json",
} as const;

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
  const response = await fetch(buildUrl(path), {
    ...init,
    headers: {
      ...DEFAULT_HEADERS,
      ...(init?.headers ?? {}),
    },
    cache: "no-store",
  });

  const body = await parseJson<T | ApiErrorResponse>(response);

  if (!response.ok) {
    throw new ApiClientError(response.status, toApiErrorResponse(body as ApiErrorResponse | null));
  }

  if (body === null) {
    throw new Error("Expected a JSON response but received none.");
  }

  return body as T;
};