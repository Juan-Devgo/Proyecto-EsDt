export interface User {
    id: string;
    name: string;
    avatar?: string;
}

export interface Comment {
    id: string;
    user: User;
    text: string;
    createdAt: Date;
    isStarred?: boolean;
}

export interface Post {
    id: string;
    title: string;
    type: string;
    description: string;
    image: string;
    author: User;
    likes: number;
    comments: Comment[];
    createdAt: Date;
}