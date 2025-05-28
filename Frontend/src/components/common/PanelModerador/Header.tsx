import React, { useState } from "react";

interface HeaderProps {
  username?: string;
  avatar?: string;
}

const Header: React.FC<HeaderProps> = ({
                                         username = "Panel Moderador",
                                         avatar = "/images/img_rectangle_1_40x40.png"
                                       }) => {
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const toggleDropdown = () => {
    setIsDropdownOpen(!isDropdownOpen);
  };

  return (
    <header className="w-full h-[84px] bg-white border-b border-[#dfdfdf] flex items-center justify-between px-6">
      <div className="flex items-center">
        <h1 className="text-xl font-semibold text-black">{username}</h1>
      </div>

      <div className="flex items-center">
        <div className="relative">
          <div className="flex items-center cursor-pointer" onClick={toggleDropdown}>
            <img
              src={avatar}
              alt="User avatar"
              className="w-10 h-10 rounded-full"
            />
            <img
              src="/images/img_chevrondown.svg"
              alt="Dropdown icon"
              className="w-6 h-6 ml-2"
            />
          </div>

          {isDropdownOpen && (
            <div className="absolute right-0 mt-2 w-48 bg-white rounded-md shadow-lg z-10">
              <div className="py-1">
                <a href="#profile" className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                  Mi Perfil
                </a>
                <a href="#settings" className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                  Configuración
                </a>
                <a href="#logout" className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                  Cerrar Sesión
                </a>
              </div>
            </div>
          )}
        </div>

        <img
          src="/images/img_616ab3338e874f96ab6b56159dd87ea5_7.png"
          alt="BrainLoop Logo"
          className="ml-4 h-[63px]"
        />
      </div>
    </header>
  );
};

export default Header;