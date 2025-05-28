import React from "react";

interface InputFieldProps extends React.InputHTMLAttributes<HTMLInputElement> {
  label?: string;
  error?: string;
  fullWidth?: boolean;
}

const InputField: React.FC<InputFieldProps> = ({
                                                 label,
                                                 error,
                                                 fullWidth = false,
                                                 className = "",
                                                 ...props
                                               }) => {
  const widthClass = fullWidth ? "w-full" : "";

  return (
    <div className={`${widthClass} ${className}`}>
      {label && (
        <label className="block text-gray-700 text-sm font-medium mb-1">
          {label}
        </label>
      )}
      <div className="relative">
        <input
          className={`border border-[#dfdfdf] rounded-lg px-4 py-2 w-full text-gray-700 focus:outline-none focus:ring-1 focus:ring-[#335c67] ${
            error ? "border-red-500" : ""
          }`}
          {...props}
        />
      </div>
      {error && <p className="text-red-500 text-xs mt-1">{error}</p>}
    </div>
  );
};

export default InputField;