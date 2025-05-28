import React from 'react';

interface ButtonProps {
  children: React.ReactNode;
  onClick?: () => void;
  variant?: 'primary' | 'secondary';
  className?: string;
  type?: 'button' | 'submit' | 'reset';
  fullWidth?: boolean;
}

const Button: React.FC<ButtonProps> = ({
                                         children,
                                         onClick,
                                         variant = 'primary',
                                         className = '',
                                         type = 'button',
                                         fullWidth = false,
                                       }) => {
  const baseStyles = 'rounded-lg text-base font-medium py-2 px-4 transition-colors duration-200';
  const variantStyles = {
    primary: 'bg-[#335c67] text-white hover:bg-[#2a4a54]',
    secondary: 'bg-[#9e2a2b] text-white hover:bg-[#7e2122]',
  };

  const widthClass = fullWidth ? 'w-full' : '';

  return (
    <button
      type={type}
      className={`${baseStyles} ${variantStyles[variant]} ${widthClass} ${className}`}
      onClick={onClick}
    >
      {children}
    </button>
  );
};

export default Button;