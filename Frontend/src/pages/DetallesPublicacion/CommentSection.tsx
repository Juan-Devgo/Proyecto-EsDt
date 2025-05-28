import React, { useState } from 'react';
import { Comment } from '../../types/DetallesPublicacion';


interface CommentSectionProps {
    comments: Comment[];
    onAddComment: (text: string) => void;
    likes: number;
}

const CommentSection: React.FC<CommentSectionProps> = ({
                                                           comments,
                                                           onAddComment,
                                                           likes
                                                       }) => {
    const [newComment, setNewComment] = useState('');

    const handleSubmit = () => {
        if (newComment.trim()) {
            onAddComment(newComment);
            setNewComment('');
        }
    };

    return (
        <div className="flex flex-col space-y-4">
            <button
                className="bg-[#9e2a2bcc] text-white py-2 px-4 rounded-lg font-medium text-base"
                onClick={handleSubmit}
            >
                Agregar comentario
            </button>

            {comments.map((comment) => (
                <div
                    key={comment.id}
                    className="bg-[#7d97a3e5] rounded-lg p-3"
                >
                    <div className="flex items-start">
                        {comment.isStarred && (
                            <img src="/images/img_star.svg" alt="Star" className="w-5 h-5 mt-1 mr-3" />
                        )}
                        <div>
                            <p className="font-normal text-base text-[#1e1e1e]">{comment.user.name}</p>
                            <p className="font-normal text-sm text-[#1e1e1e] mt-1">{comment.text}</p>
                        </div>
                    </div>
                </div>
            ))}

            <div className="flex items-center mt-4">
                <div className="bg-[#eb221e] w-9 h-9 rounded-full border border-[#2c2c2c] flex items-center justify-center">
                    <img src="/images/img_heart.svg" alt="Like" className="w-5 h-5" />
                </div>
                <span className="ml-3 text-base text-[#757575]"># {likes}</span>
            </div>
        </div>
    );
};

export default CommentSection;