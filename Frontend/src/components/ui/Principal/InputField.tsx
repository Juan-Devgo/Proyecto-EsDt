import React from "react";

interface InputFieldProps extends React.InputHTMLAttributes<HTMLInputElement> {
  label?: string;
  error?: string;
  fullWidth?: boolean;
  className?: string;
  containerClassName?: string;
}

const InputField: React.FC<InputFieldProps> = ({
  label,
  error,
  fullWidth = false,
  className = "",
  containerClassName = "",
  ...props
}) => {
  const inputStyles = `
    px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary
    ${error ? "border-red-500" : "border-gray-300"}
    ${fullWidth ? "w-full" : ""}
    ${className}
  `;

  return (
    <div className={`${containerClassName} ${fullWidth ? "w-full" : ""}`}>
      {label && (
        <label className="block text-sm font-medium text-gray-700 mb-1">
          {label}
        </label>
      )}
      <input className={inputStyles} {...props} />
      {error && <p className="mt-1 text-sm text-red-500">{error}</p>}
    </div>
  );
};

export default InputField;