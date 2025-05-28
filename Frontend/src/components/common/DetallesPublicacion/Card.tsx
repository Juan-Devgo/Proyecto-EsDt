import React from 'react';

interface CardProps {
  children: React.ReactNode;
  className?: string;
  hasShadow?: boolean;
  hasBorder?: boolean;
  borderColor?: string;
  rounded?: boolean;
}

const Card: React.FC<CardProps> = ({
                                     children,
                                     className = "",
                                     hasShadow = false,
                                     hasBorder = false,
                                     borderColor = "#e09f3ee5",
                                     rounded = false,
                                   }) => {
  return (
    <div
      className={`
        bg-white
        ${hasShadow ? 'shadow-md' : ''}
        ${hasBorder ? `border border-solid` : ''}
        ${rounded ? 'rounded-lg' : ''}
        ${className}
      `}
      style={{ borderColor: hasBorder ? borderColor : 'transparent' }}
    >
      {children}
    </div>
  );
};

export default Card;