export interface User {
  id: string;
  name: string;
  username: string;
  email: string;
  avatar?: string;
  bio?: string;
  followers?: number;
  following?: number;
  publications?: number;
  joinedAt: Date;
}