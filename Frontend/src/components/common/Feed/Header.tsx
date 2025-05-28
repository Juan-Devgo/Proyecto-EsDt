import React from "react";
import Button from "../../ui/Feed/Button.tsx";

interface HeaderProps {
  className?: string;
}

const Header: React.FC<HeaderProps> = ({ className = "" }) => {
  return (
    <header className={`flex items-center justify-between p-6 ${className}`}>
      <div className="flex space-x-2 bg-gray-100 rounded-lg overflow-hidden">
        <div className="bg-white shadow-sm rounded-l-lg px-3 py-1">
          <span className="text-base font-medium">Publicaciones</span>
        </div>
        <div className="bg-blue-gray-500 bg-opacity-60 px-3 py-1 rounded-r-lg">
          <span className="text-base font-medium">Solicitudes de Ayuda</span>
        </div>
      </div>
      
      <Button 
        className="bg-red-700 bg-opacity-80 text-white px-4 py-2 rounded-lg"
        onClick={() => console.log("Create publication clicked")}
      >
        Crear Publicación
      </Button>
    </header>
  );
};

export default Header;