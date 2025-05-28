export interface RegisterFormData {
  fullName: string;
  username: string;
  password: string;
}

export interface RegisterFormErrors {
  fullName?: string;
  username?: string;
  password?: string;
}