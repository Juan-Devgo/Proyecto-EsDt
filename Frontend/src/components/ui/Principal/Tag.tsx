import React from "react";

interface TagProps {
  text: string;
  color?: "primary" | "secondary" | "success" | "danger" | "warning" | "info";
  onRemove?: () => void;
  className?: string;
}

const Tag: React.FC<TagProps> = ({
  text,
  color = "primary",
  onRemove,
  className = "",
}) => {
  const colorStyles = {
    primary: "bg-primary-light text-primary-dark",
    secondary: "bg-gray-200 text-gray-800",
    success: "bg-green-100 text-green-800",
    danger: "bg-red-100 text-red-800",
    warning: "bg-yellow-100 text-yellow-800",
    info: "bg-blue-100 text-blue-800",
  };

  return (
    <span
      className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${colorStyles[color]} ${className}`}
    >
      {text}
      {onRemove && (
        <button
          type="button"
          className="flex-shrink-0 ml-1.5 h-4 w-4 rounded-full inline-flex items-center justify-center text-gray-400 hover:bg-gray-200 hover:text-gray-500 focus:outline-none"
          onClick={onRemove}
        >
          <span className="sr-only">Remove {text}</span>
          <svg
            className="h-2 w-2"
            stroke="currentColor"
            fill="none"
            viewBox="0 0 8 8"
          >
            <path
              strokeLinecap="round"
              strokeWidth="1.5"
              d="M1 1l6 6m0-6L1 7"
            />
          </svg>
        </button>
      )}
    </span>
  );
};

export default Tag;