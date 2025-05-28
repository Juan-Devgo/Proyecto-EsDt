import React from "react";

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: "primary" | "secondary";
  fullWidth?: boolean;
  children: React.ReactNode;
}

const Button: React.FC<ButtonProps> = ({
                                         variant = "primary",
                                         fullWidth = false,
                                         children,
                                         className = "",
                                         ...props
                                       }) => {
  const baseClasses = "py-2 px-4 rounded-lg font-medium text-base focus:outline-none transition-colors";
  const variantClasses = {
    primary: "bg-[#335c67] text-white hover:bg-[#2a4a54]",
    secondary: "bg-[#9e2a2b] text-white hover:bg-[#7e2122]",
  };

  const widthClass = fullWidth ? "w-full" : "";

  return (
    <button
      className={`${baseClasses} ${variantClasses[variant]} ${widthClass} ${className}`}
      {...props}
    >
      {children}
    </button>
  );
};

export default Button;