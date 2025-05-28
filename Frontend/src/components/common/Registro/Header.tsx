import React from "react";

interface HeaderProps {
  logoSrc: string;
  altText?: string;
}

const Header: React.FC<HeaderProps> = ({
                                         logoSrc,
                                         altText = "Brain Loop Logo"
                                       }) => {
  return (
    <header className="flex justify-center items-center py-4">
      <div className="w-[480px]">
        <img
          src={logoSrc}
          alt={altText}
          className="w-full h-auto"
        />
      </div>
    </header>
  );
};

export default Header;