import React, { ReactNode } from 'react';

interface CardProps {
    children: ReactNode;
    className?: string;
    hasShadow?: boolean;
    hasBorder?: boolean;
    borderColor?: string;
    borderRadius?: string;
}

const Card: React.FC<CardProps> = ({
                                       children,
                                       className = '',
                                       hasShadow = true,
                                       hasBorder = false,
                                       borderColor = '#dfdfdf',
                                       borderRadius = '8px',
                                   }) => {
    return (
        <div
            className={`bg-white ${hasShadow ? 'shadow-md' : ''} ${
                hasBorder ? `border border-solid` : ''
            } ${className}`}
            style={{
                borderRadius,
                borderColor: hasBorder ? borderColor : 'transparent',
            }}
        >
            {children}
        </div>
    );
};

export default Card;