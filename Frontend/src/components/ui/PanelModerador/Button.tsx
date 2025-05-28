import React from "react";

type ButtonVariant = "primary" | "secondary" | "outline";
type ButtonSize = "sm" | "md" | "lg";

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: ButtonVariant;
  size?: ButtonSize;
  fullWidth?: boolean;
  leftIcon?: React.ReactNode;
  rightIcon?: React.ReactNode;
}

const Button: React.FC<React.PropsWithChildren<ButtonProps>> = ({
                                                                  children,
                                                                  variant = "primary",
                                                                  size = "md",
                                                                  fullWidth = false,
                                                                  leftIcon,
                                                                  rightIcon,
                                                                  className = "",
                                                                  ...props
                                                                }) => {
  const baseStyles = "inline-flex items-center justify-center rounded-lg font-medium transition-colors";

  const variantStyles = {
    primary: "bg-[#9e2a2b] text-white hover:bg-[#7d2123]",
    secondary: "bg-[#335c67] text-white hover:bg-[#264751]",
    outline: "border border-gray-300 bg-white text-gray-700 hover:bg-gray-50"
  };

  const sizeStyles = {
    sm: "px-3 py-1.5 text-sm",
    md: "px-4 py-2 text-base",
    lg: "px-6 py-3 text-lg"
  };

  const widthStyles = fullWidth ? "w-full" : "";

  return (
    <button
      className={`${baseStyles} ${variantStyles[variant]} ${sizeStyles[size]} ${widthStyles} ${className}`}
      {...props}
    >
      {leftIcon && <span className="mr-2">{leftIcon}</span>}
      {children}
      {rightIcon && <span className="ml-2">{rightIcon}</span>}
    </button>
  );
};

export default Button;