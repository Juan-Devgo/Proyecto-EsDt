import React from 'react';

interface InputFieldProps {
  type?: string;
  placeholder: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  className?: string;
  name: string;
  required?: boolean;
}

const InputField: React.FC<InputFieldProps> = ({
                                                 type = 'text',
                                                 placeholder,
                                                 value,
                                                 onChange,
                                                 className = '',
                                                 name,
                                                 required = false,
                                               }) => {
  return (
    <input
      type={type}
      placeholder={placeholder}
      value={value}
      onChange={onChange}
      name={name}
      required={required}
      className={`w-full h-10 px-4 py-2 text-gray-700 border border-[#dfdfdf] rounded-lg focus:outline-none focus:border-[#335c67] ${className}`}
    />
  );
};

export default InputField;