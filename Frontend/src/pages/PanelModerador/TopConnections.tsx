import React from "react";
import Card from "../../components/common/PanelModerador/Card";
import { User } from "../../types/PanelModerador";

interface TopConnectionsProps {
  users?: User[];
}

const TopConnections: React.FC<TopConnectionsProps> = ({
                                                         users = [
                                                           { id: 1, name: "Nombre Usuario", avatar: "/images/img_rectangle_1.png" },
                                                           { id: 2, name: "Nombre Usuario", avatar: "/images/img_rectangle_1_48x48.png" },
                                                           { id: 3, name: "Nombre Usuario", avatar: "/images/img_rectangle_1_1.png" },
                                                           { id: 4, name: "Nombre Usuario", avatar: "/images/img_rectangle_1_2.png" },
                                                           { id: 5, name: "Nombre Usuario", avatar: "/images/img_rectangle_1_3.png" },
                                                         ]
                                                       }) => {
  return (
    <Card title="Usuarios con mas conexiones" className="h-[515px]">
      {users.map((user) => (
        <div key={user.id} className="mb-6 last:mb-0">
          <div className="flex items-start">
            <img
              src={user.avatar}
              alt={`${user.name} avatar`}
              className="w-12 h-12 rounded-full"
            />
            <div className="ml-4">
              <p className="text-base font-medium text-black">{user.name}</p>
              <a
                href={`/connections/${user.id}`}
                className="text-base text-[#444444] hover:underline"
              >
                ver conexiones
              </a>
            </div>
          </div>
        </div>
      ))}
    </Card>
  );
};

export default TopConnections;