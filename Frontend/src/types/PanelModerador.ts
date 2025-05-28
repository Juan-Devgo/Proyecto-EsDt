export interface Publication {
  id: number;
  title: string;
  link: string;
}

export interface User {
  id: number;
  name: string;
  avatar: string;
}

export interface StudyGroup {
  id: number;
  name: string;
  participationLevel: number;
}

export interface NetworkNode {
  id: number;
  connections: number[];
}