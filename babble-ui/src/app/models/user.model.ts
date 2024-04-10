export interface User {
  username: string;
  email: string;
  password: string;
}

export interface UserLoginResponse {
  user: User;
  token: string;
}
