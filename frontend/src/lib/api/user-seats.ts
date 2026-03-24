import { apiRequest } from "@/lib/api/client";
import type {
  CurrentSeat,
  UserSeatLeaveResponse,
  UserSeatRegisterRequest,
  UserSeatRegisterResponse,
} from "@/types/api";

export const registerCurrentSeat = async (payload: UserSeatRegisterRequest) => {
  return apiRequest<UserSeatRegisterResponse>("/user-seats", {
    method: "POST",
    body: JSON.stringify(payload),
  });
};

export const leaveCurrentSeat = async () => {
  return apiRequest<UserSeatLeaveResponse>("/user-seats/leave", {
    method: "POST",
  });
};

export const getCurrentSeat = async () => {
  return apiRequest<CurrentSeat>("/users/me/current-seat");
};