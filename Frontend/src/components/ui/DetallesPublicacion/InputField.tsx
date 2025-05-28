import React from 'react';

interface InputFieldProps {
    type?: string;
    placeholder?: string;
    value: string;
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    name?: string;
    id?: string;
    className?: string;
    required?: boolean;
    disabled?: boolean;
    label?: string;
    error?: string;
}

const InputField: React.FC<InputFieldProps> = ({
                                                   type = 'text',
                                                   placeholder = '',
                                                   value,
                                                   onChange,
                                                   name,
                                                   id,
                                                   className = '',
                                                   required = false,
                                                   disabled = false,
                                                   label,
                                                   error,
                                               }) => {
    return (
        <div className="w-full">
            {label && (
                <label
                    htmlFor={id || name}
                    className="block text-sm font-medium text-gray-700 mb-1"
                >
                    {label}
                    {required && <span className="text-red-500 ml-1">*</span>}
                </label>
            )}
            <input
                type={type}
                placeholder={placeholder}
                value={value}
                onChange={onChange}
                name={name}
                id={id || name}
                required={required}
                disabled={disabled}
                className={`
          w-full px-3 py-2 border border-gray-300 rounded-md
          focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500
          ${disabled ? 'bg-gray-100 cursor-not-allowed' : 'bg-white'}
          ${error ? 'border-red-500' : ''}
          ${className}
        `}
            />
            {error && <p className="mt-1 text-sm text-red-500">{error}</p>}
        </div>
    );
};

export default InputField;