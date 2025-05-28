import React from 'react';

interface ButtonProps {
  children: React.ReactNode;
  onClick?: () => void;
  variant?: 'primary' | 'secondary' | 'filter';
  className?: string;
  icon?: string;
}

const Button: React.FC<ButtonProps> = ({
                                         children,
                                         onClick,
                                         variant = 'primary',
                                         className = '',
                                         icon
                                       }) => {
  const baseClasses = "flex items-center px-3 py-2 rounded-lg border font-medium";

  const variantClasses = {
    primary: "bg-blue-600 text-white border-blue-600 hover:bg-blue-700",
    secondary: "bg-white text-gray-800 border-gray-300 hover:bg-gray-50",
    filter: "bg-[#9e2a2b7f] border-[#dfdfdf] text-black/40"
  };

  return (
    <button
      className={`${baseClasses} ${variantClasses[variant]} ${className}`}
      onClick={onClick}
    >
      {icon && <img src={icon} alt="Icon" className="w-6 h-6 mr-3" />}
      {children}
    </button>
  );
};

export default Button;