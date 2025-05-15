export interface Publication {
  id: number;
  title: string;
  author: string;
  authorId: string;
  image: string;
  content?: string;
  createdAt: Date;
  tags?: string[];
  likes?: number;
  comments?: number;
  isBookmarked?: boolean;
}