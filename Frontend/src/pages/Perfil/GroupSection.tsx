import React from 'react';
import Card from '../../components/common/Perfil/Card';
import { Group } from '../../types/Perfil';

interface GroupSectionProps {
  group: Group;
}

const GroupSection: React.FC<GroupSectionProps> = ({ group }) => {
  return (
    <Card
      className="w-full md:w-[455px] h-[545px]"
      hasShadow={true}
      hasBorder={true}
      borderColor="#9e2a2b4c"
    >
      <div className="bg-[#7d97a3e5] rounded-t-lg p-3 h-[104px]">
        <div className="flex items-start ml-4 mt-2">
          <img
            src="/images/img_star.svg"
            alt="Star"
            className="w-5 h-5 mt-1 mr-3"
          />
          <div>
            <p className="text-base text-[#1e1e1e]">{group.name}</p>
            <p className="text-sm text-[#1e1e1e] mt-2">{group.description}</p>
          </div>
        </div>
      </div>
    </Card>
  );
};

export default GroupSection;