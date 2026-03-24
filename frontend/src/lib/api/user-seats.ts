import { apiRequest } from "@/lib/api/client";
import type {
  CurrentSeat,
  UserSeatLeaveRequest,
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

export const leaveCurrentSeat = async (payload: UserSeatLeaveRequest) => {
  return apiRequest<UserSeatLeaveResponse>("/user-seats/leave", {
    method: "POST",
    body: JSON.stringify(payload),
  });
};

export const getCurrentSeat = async (userId: number) => {
  return apiRequest<CurrentSeat>(`/users/${userId}/current-seat`);
};