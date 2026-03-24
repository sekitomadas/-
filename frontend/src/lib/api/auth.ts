import { apiRequest, setAccessToken } from "@/lib/api/client";
import type { LoginRequest, LoginResponse } from "@/types/api";

export const login = async (payload: LoginRequest) => {
  const response = await apiRequest<LoginResponse>("/auth/login", {
    method: "POST",
    body: JSON.stringify(payload),
  });

  setAccessToken(response.accessToken);
  return response;
};

export const logout = () => {
  setAccessToken(null);
};
