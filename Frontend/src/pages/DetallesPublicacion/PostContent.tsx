import React from 'react';
import { Post } from '../../types/DetallesPublicacion';
import Card from '../../components/common/DetallesPublicacion/Card';

interface PostContentProps {
    post: Post;
}

const PostContent: React.FC<PostContentProps> = ({ post }) => {
    return (
        <div className="flex flex-col md:flex-row">
            <div className="w-full md:w-auto">
                <img
                    src={post.image}
                    alt={post.title}
                    className="w-full h-auto object-cover"
                />
            </div>

            <Card
                className="p-5 w-full md:w-[187px]"
                hasShadow={true}
                hasBorder={true}
                borderColor="#e09f3ee5"
            >
                <h1 className="text-2xl font-semibold text-[#1e1e1e] mb-4">
                    {post.title}
                </h1>

                <div className="mb-3">
                    <p className="text-base font-normal text-[#757575] text-center">
                        {post.type}
                    </p>
                </div>

                <div className="mb-3">
                    <p className="text-base font-normal text-[#757575]">
                        {post.author.name}
                    </p>
                </div>

                <div>
                    <p className="text-base font-normal text-[#757575]">
                        {post.description}
                    </p>
                </div>
            </Card>
        </div>
    );
};

export default PostContent;