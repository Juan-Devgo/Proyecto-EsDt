import React from 'react';

interface InputFieldProps {
  value: string;
  onChange: (value: string) => void;
  placeholder?: string;
  icon?: string;
  className?: string;
}

const InputField: React.FC<InputFieldProps> = ({
                                                 value,
                                                 onChange,
                                                 placeholder = '',
                                                 icon,
                                                 className = ''
                                               }) => {
  return (
    <div className={`flex items-center border border-[#dfdfdf] rounded-lg px-3 py-2 bg-white ${className}`}>
      {icon && <img src={icon} alt="Icon" className="w-6 h-6 mr-3" />}
      <input
        type="text"
        placeholder={placeholder}
        className="w-full outline-none text-base text-gray-500"
        value={value}
        onChange={(e) => onChange(e.target.value)}
      />
    </div>
  );
};

export default InputField;