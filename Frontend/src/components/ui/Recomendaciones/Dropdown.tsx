import React, { useState } from 'react';

interface DropdownOption {
  id: string | number;
  label: string;
}

interface DropdownProps {
  options: DropdownOption[];
  value: string | number | null;
  onChange: (value: string | number) => void;
  placeholder?: string;
  className?: string;
}

const Dropdown: React.FC<DropdownProps> = ({
                                             options,
                                             value,
                                             onChange,
                                             placeholder = 'Select an option',
                                             className = ''
                                           }) => {
  const [isOpen, setIsOpen] = useState(false);

  const selectedOption = options.find(option => option.id === value);

  return (
    <div className={`relative ${className}`}>
      <button
        type="button"
        className="w-full flex items-center justify-between border border-[#dfdfdf] rounded-lg px-3 py-2 bg-white"
        onClick={() => setIsOpen(!isOpen)}
      >
        <span className={selectedOption ? 'text-black' : 'text-gray-500'}>
          {selectedOption ? selectedOption.label : placeholder}
        </span>
        <svg className="w-4 h-4 ml-2" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 9l-7 7-7-7" />
        </svg>
      </button>

      {isOpen && (
        <div className="absolute z-10 w-full mt-1 bg-white border border-gray-200 rounded-md shadow-lg">
          {options.map((option) => (
            <button
              key={option.id}
              type="button"
              className="w-full text-left px-4 py-2 hover:bg-gray-100"
              onClick={() => {
                onChange(option.id);
                setIsOpen(false);
              }}
            >
              {option.label}
            </button>
          ))}
        </div>
      )}
    </div>
  );
};

export default Dropdown;