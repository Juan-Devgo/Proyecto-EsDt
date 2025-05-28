import React from "react";
import Card from "../../components/common/PanelModerador/Card";
import { StudyGroup } from "../../types/PanelModerador";

interface StudyCommunitiesProps {
  groups?: StudyGroup[];
}

const StudyCommunities: React.FC<StudyCommunitiesProps> = ({
                                                             groups = [
                                                               { id: 1, name: "Nombre Grupo", participationLevel: 4321 },
                                                               { id: 2, name: "Nombre Grupo", participationLevel: 4033 },
                                                               { id: 3, name: "Nombre Grupo", participationLevel: 3128 },
                                                               { id: 4, name: "Nombre Grupo", participationLevel: 2104 },
                                                               { id: 5, name: "Nombre Grupo", participationLevel: 2003 },
                                                               { id: 6, name: "Nombre Grupo", participationLevel: 1894 },
                                                               { id: 7, name: "Nombre Grupo", participationLevel: 405 },
                                                             ]
                                                           }) => {
  return (
    <Card title="Comunidades de estudio" className="h-[515px]">
      <div className="mt-4 mb-2 flex justify-between">
        <span className="text-base font-semibold text-[#828282]">Comunidad</span>
        <span className="text-base font-semibold text-[#828282]">Niveles de participación</span>
      </div>

      {groups.map((group) => (
        <div
          key={group.id}
          className="flex justify-between items-center h-12 border border-[#dfdfdf] mb-2"
        >
          <span className="text-base font-medium text-black ml-4">{group.name}</span>
          <span className="text-base text-black mr-4">{group.participationLevel}</span>
        </div>
      ))}
    </Card>
  );
};

export default StudyCommunities;