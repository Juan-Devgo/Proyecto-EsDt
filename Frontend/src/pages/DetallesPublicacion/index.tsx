import React, { useState } from 'react';
import PostContent from './PostContent';
import CommentSection from './CommentSection';
import { Post, Comment } from '../../types/DetallesPublicacion';

const DetallesPublicacion: React.FC = () => {
    // Mock data for the post
    const [post, setPost] = useState<Post>({
        id: '1',
        title: 'Titulo Publicacion',
        type: 'Tipo Publicacion',
        description: 'Descripcion',
        image: '/images/img_image.png',
        author: {
            id: '1',
            name: 'Nombre Usuario',
        },
        likes: 5,
        comments: [
            {
                id: '1',
                user: {
                    id: '2',
                    name: 'Nombre Usuario',
                },
                text: 'Comentario',
                createdAt: new Date(),
                isStarred: true,
            }
        ],
        createdAt: new Date(),
    });

    const handleAddComment = (text: string) => {
        const newComment: Comment = {
            id: Date.now().toString(),
            user: {
                id: '1',
                name: 'Nombre Usuario',
            },
            text,
            createdAt: new Date(),
            isStarred: false,
        };

        setPost(prevPost => ({
            ...prevPost,
            comments: [...prevPost.comments, newComment],
        }));
    };

    const handleLike = () => {
        setPost(prevPost => ({
            ...prevPost,
            likes: prevPost.likes + 1,
        }));
    };

    return (
        <div className="bg-white min-h-screen">
            <div className="container mx-auto px-4 py-16">
                <div className="max-w-4xl mx-auto">
                    <div className="bg-white p-4">
                        <PostContent post={post} />

                        <div className="mt-6">
                            <CommentSection
                                comments={post.comments}
                                onAddComment={handleAddComment}
                                likes={post.likes}
                            />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default DetallesPublicacion;