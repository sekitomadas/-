import { apiRequest } from "@/lib/api/client";
import type { User, UserRegisterRequest, UserRegisterResponse } from "@/types/api";

export const getUsers = async () => {
  return apiRequest<User[]>("/users");
};

export const registerUser = async (payload: UserRegisterRequest) => {
  return apiRequest<UserRegisterResponse>("/users", {
    method: "POST",
    body: JSON.stringify(payload),
  });
};