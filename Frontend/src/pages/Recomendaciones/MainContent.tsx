import React from 'react';

interface User {
  id: number;
  name: string;
  career: string;
  commonProject: string;
  avatar: string;
}

interface MainContentProps {
  users: User[];
}

const MainContent: React.FC<MainContentProps> = ({ users }) => {
  return (
    <div>
      <div className="grid grid-cols-1">
        {/* Table Header */}
        <div className="grid grid-cols-4 mb-2">
          <div className="text-[#33335567] font-medium">Carrera</div>
          <div className="text-[#335c67] font-medium">Nombre de usuario</div>
          <div className="text-[#335c67] font-medium">Grupo en Común</div>
          <div className="text-[#335c67] font-medium text-center">Usuario</div>
        </div>

        {/* Table Content */}
        {users.map((user) => (
          <div
            key={user.id}
            className="grid grid-cols-4 items-center border border-[#dfdfdf] py-4 px-2"
          >
            <div className="text-gray-500 font-medium">{user.career}</div>
            <div className="text-black font-medium">{user.name}</div>
            <div>
              <span className="bg-white text-xs font-semibold px-2 py-1 rounded-lg border border-[#dfdfdf]">
                {user.commonProject}
              </span>
            </div>
            <div className="flex justify-center">
              <img src={user.avatar} alt="User Avatar" className="w-7 h-7" />
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default MainContent;