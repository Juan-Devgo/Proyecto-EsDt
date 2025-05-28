import React from 'react';

interface TagProps {
  text: string;
  color?: string;
  backgroundColor?: string;
  className?: string;
}

const Tag: React.FC<TagProps> = ({
                                   text,
                                   color = '#333355',
                                   backgroundColor = 'transparent',
                                   className = '',
                                 }) => {
  return (
    <span
      className={`inline-block font-medium text-center ${className}`}
      style={{
        color: color,
        backgroundColor: backgroundColor,
      }}
    >
      {text}
    </span>
  );
};

export default Tag;