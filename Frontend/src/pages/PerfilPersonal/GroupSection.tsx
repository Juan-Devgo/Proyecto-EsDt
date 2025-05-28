import React from 'react';
import { Group } from '../../types/PerfilPersonal';

interface GroupSectionProps {
    group: Group;
}

const GroupSection: React.FC<GroupSectionProps> = ({ group }) => {
    return (
        <div className="w-full md:w-[455px] bg-white shadow-md border border-[#9e2a2b4c]">
            <div className="bg-[#7d97a3e5] rounded-t-lg p-3">
                <div className="flex items-start pl-4">
                    <img src="/images/img_star.svg" alt="Star" className="w-5 h-5 mt-0.5" />
                    <div className="ml-3">
                        <p className="text-base text-[#1e1e1e]">{group.name}</p>
                        <p className="text-sm text-[#1e1e1e] mt-1">{group.description}</p>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default GroupSection;