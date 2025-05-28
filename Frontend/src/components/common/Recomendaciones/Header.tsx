import React from 'react';

interface HeaderProps {
  title: string;
  logo?: string;
}

const Header: React.FC<HeaderProps> = ({ title, logo }) => {
  return (
    <header className="flex justify-between items-center p-4 border-b border-gray-200">
      <h1 className="text-xl font-semibold">{title}</h1>
      {logo && (
        <img src={logo} alt="Logo" className="h-12" />
      )}
    </header>
  );
};

export default Header;