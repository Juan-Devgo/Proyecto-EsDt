import React from 'react';

interface InputFieldProps {
    value: string;
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    placeholder?: string;
    type?: string;
    name?: string;
    id?: string;
    className?: string;
    disabled?: boolean;
    required?: boolean;
    leftIcon?: React.ReactNode;
    rightIcon?: React.ReactNode;
    onKeyDown?: (e: React.KeyboardEvent<HTMLInputElement>) => void;
}

const InputField: React.FC<InputFieldProps> = ({
                                                   value,
                                                   onChange,
                                                   placeholder = '',
                                                   type = 'text',
                                                   name,
                                                   id,
                                                   className = '',
                                                   disabled = false,
                                                   required = false,
                                                   leftIcon,
                                                   rightIcon,
                                                   onKeyDown,
                                               }) => {
    return (
        <div className={`relative flex items-center w-full ${className}`}>
            {leftIcon && (
                <div className="absolute left-3 flex items-center justify-center">
                    {leftIcon}
                </div>
            )}

            <input
                type={type}
                value={value}
                onChange={onChange}
                placeholder={placeholder}
                name={name}
                id={id}
                disabled={disabled}
                required={required}
                onKeyDown={onKeyDown}
                className={`w-full py-2 px-4 bg-transparent border border-border-primary rounded-lg text-text-primary placeholder-text-tertiary focus:outline-none focus:border-accent
          ${leftIcon ? 'pl-10' : ''}
          ${rightIcon ? 'pr-10' : ''}
          ${disabled ? 'opacity-50 cursor-not-allowed' : ''}
        `}
            />

            {rightIcon && (
                <div className="absolute right-3 flex items-center justify-center">
                    {rightIcon}
                </div>
            )}
        </div>
    );
};

export default InputField;