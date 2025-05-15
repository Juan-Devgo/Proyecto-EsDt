import React from "react";

interface CardProps {
  title: string;
  author: string;
  image: string;
  className?: string;
  onClick?: () => void;
}

const Card: React.FC<CardProps> = ({
  title,
  author,
  image,
  className = "",
  onClick,
}) => {
  return (
    <div 
      className={`flex flex-col cursor-pointer ${className}`}
      onClick={onClick}
    >
      <img 
        src={image} 
        alt={title} 
        className="w-full object-cover mb-3 rounded-md" 
      />
      <h3 className="text-xl font-medium text-black">{title}</h3>
      <p className="text-base text-gray-600">{author}</p>
    </div>
  );
};

export default Card;