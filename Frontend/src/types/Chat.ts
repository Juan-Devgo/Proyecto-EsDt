export interface User {
  id: string;
  name: string;
  career: string;
  avatar: string;
}

export interface Message {
  id: string;
  senderId: string;
  text: string;
  timestamp: Date;
  isRead: boolean;
}

export interface Conversation {
  id: string;
  participants: User[];
  messages: Message[];
  lastMessageTimestamp: Date;
}