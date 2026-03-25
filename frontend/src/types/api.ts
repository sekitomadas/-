export type User = {
  id: number;
  name: string;
  email: string;
};

export type LoginRequest = {
  email: string;
  password: string;
};

export type LoginResponse = {
  accessToken: string;
  tokenType: string;
  expiresIn: number;
  roleCode: number;
};

export type UserRegisterRequest = {
  name: string;
  email: string;
  password: string;
};

export type UserRegisterResponse = {
  id: number;
  name: string;
  email: string;
  createdAt: string;
};

export type Seat = {
  id: number;
  name: string;
  location: string;
};

export type UserSeatRegisterRequest = {
  seatId: number;
};

export type UserSeatRegisterResponse = {
  userSeatId: number;
  userId: number;
  seatId: number;
  startTime: string;
};

export type UserSeatLeaveResponse = {
  userId: number;
  leftAt: string;
};

export type CurrentSeat = {
  userId: number;
  userName: string;
  seat: {
    id: number;
    name: string;
    location: string;
  };
  since: string;
};

export type ApiErrorDetail = {
  field: string;
  reason: string;
};

export type ApiValidationError = {
  field: string;
  message: string;
  rejectedValue?: string | null;
};

export type ApiErrorResponse = {
  code?: string;
  message: string;
  details?: ApiErrorDetail[];
  errors?: ApiValidationError[];
};