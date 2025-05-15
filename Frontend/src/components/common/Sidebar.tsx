import React from "react";
import { Link } from "react-router-dom";

interface SidebarProps {
  className?: string;
}

const Sidebar: React.FC<SidebarProps> = ({ className = "" }) => {
  return (
    <aside className={`bg-amber-300 bg-opacity-90 h-full p-6 ${className}`}>
      <div className="mb-6">
        <img 
          src="/images/img_616ab3338e874f96ab6b56159dd87ea5_5.png" 
          alt="BrainLoop Logo" 
          className="h-16 mb-4" 
        />
      </div>
      
      <div className="mb-6">
        <h2 className="text-base font-semibold mb-2">Discover</h2>
        <div className="space-y-3">
          <Link to="/profile">
            <div className="flex items-center p-2 bg-gray-100 rounded-lg">
              <img src="/images/img_vector.svg" alt="Profile icon" className="w-6 h-6 mr-3" />
              <span className="text-base font-medium">Perfil</span>
            </div>
          </Link>
          
          <Link to="/search">
            <div className="flex items-center p-2 bg-white rounded-lg">
              <img src="/images/img_search.svg" alt="Search icon" className="w-6 h-6 mr-3" />
              <span className="text-base font-medium">Buscar Contenido</span>
            </div>
          </Link>
          
          <Link to="/help">
            <div className="flex items-center p-2 bg-gray-400 bg-opacity-90 rounded-lg">
              <span className="text-base font-medium ml-2">Solicitud de Ayuda</span>
            </div>
          </Link>
        </div>
      </div>
      
      <div>
        <h2 className="text-base font-semibold mb-2">Otros:</h2>
        <div className="space-y-3">
          <Link to="/recommendations">
            <div className="flex items-center p-2 bg-white rounded-lg">
              <img src="/images/img_list.svg" alt="List icon" className="w-6 h-6 mr-3" />
              <span className="text-base font-medium">Recomendaciones</span>
            </div>
          </Link>
          
          <Link to="/groups">
            <div className="flex items-center p-2 bg-white rounded-lg">
              <img src="/images/img_addressbooktabsduotone.svg" alt="Groups icon" className="w-6 h-6 mr-3" />
              <span className="text-base font-medium">Grupos</span>
            </div>
          </Link>
          
          <Link to="/friends">
            <div className="flex items-center p-2 bg-white rounded-lg">
              <img src="/images/img_smile.svg" alt="Friends icon" className="w-6 h-6 mr-3" />
              <span className="text-base font-medium">Amigos</span>
            </div>
          </Link>
        </div>
      </div>
    </aside>
  );
};

export default Sidebar;