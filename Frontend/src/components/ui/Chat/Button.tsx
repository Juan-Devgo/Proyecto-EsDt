import React from 'react';

interface ButtonProps {
    children: React.ReactNode;
    variant?: 'primary' | 'secondary' | 'outline';
    size?: 'sm' | 'md' | 'lg';
    onClick?: () => void;
    className?: string;
    disabled?: boolean;
    type?: 'button' | 'submit' | 'reset';
}

const Button: React.FC<ButtonProps> = ({
                                           children,
                                           variant = 'primary',
                                           size = 'md',
                                           onClick,
                                           className = '',
                                           disabled = false,
                                           type = 'button',
                                       }) => {
    const baseClasses = 'rounded-lg font-medium transition-colors focus:outline-none';

    const variantClasses = {
        primary: 'bg-primary-background text-white hover:bg-primary-dark',
        secondary: 'bg-secondary-background text-text-primary hover:bg-secondary-dark',
        outline: 'bg-transparent border border-border-primary text-text-primary hover:bg-secondary-light',
    };

    const sizeClasses = {
        sm: 'py-1 px-3 text-sm',
        md: 'py-2 px-4 text-base',
        lg: 'py-3 px-6 text-lg',
    };

    const disabledClasses = disabled ? 'opacity-50 cursor-not-allowed' : 'cursor-pointer';

    return (
        <button
            type={type}
            className={`${baseClasses} ${variantClasses[variant]} ${sizeClasses[size]} ${disabledClasses} ${className}`}
            onClick={onClick}
            disabled={disabled}
        >
            {children}
        </button>
    );
};

export default Button;