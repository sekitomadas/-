import { apiRequest } from "@/lib/api/client";
import type { Seat } from "@/types/api";

export const getSeats = async () => {
  return apiRequest<Seat[]>("/seats");
};