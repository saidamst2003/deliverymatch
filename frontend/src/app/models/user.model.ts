export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  token: string;
}

export interface User {
  id: number;
  fullName: string;
  email: string;
  role: string;
}
