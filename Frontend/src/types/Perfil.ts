export interface UserProfile {
    nickname: string;
    name: string;
    followersCount: number;
    followingCount: number;
    status: string;
    career: string;
    userType: string;
    profileImage: string;
}

export interface Publication {
    id: string;
    title: string;
    link: string;
}

export interface Group {
    name: string;
    description: string;
}