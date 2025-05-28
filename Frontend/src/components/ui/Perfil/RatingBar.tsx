import React from 'react';

interface RatingBarProps {
    rating: number;
    maxRating?: number;
    size?: 'sm' | 'md' | 'lg';
    className?: string;
}

const RatingBar: React.FC<RatingBarProps> = ({
                                                 rating,
                                                 maxRating = 5,
                                                 size = 'md',
                                                 className = '',
                                             }) => {
    const sizeClasses = {
        sm: 'h-4',
        md: 'h-5',
        lg: 'h-6',
    };

    return (
        <div className={`flex items-center ${className}`}>
            {[...Array(maxRating)].map((_, index) => (
                <img
                    key={index}
                    src={index < rating ? "/images/img_star.svg" : "/images/img_star_inactive.svg"}
                    alt={index < rating ? "Filled star" : "Empty star"}
                    className={`${sizeClasses[size]} w-auto mr-1`}
                />
            ))}
        </div>
    );
};

export default RatingBar;