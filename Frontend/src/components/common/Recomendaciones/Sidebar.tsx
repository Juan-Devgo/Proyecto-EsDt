import React, { useState } from 'react';

interface InterestGroup {
    id: number;
    name: string;
}

const Sidebar: React.FC = () => {
    const [selectedGroup, setSelectedGroup] = useState<number>(1);

    const interestGroups: InterestGroup[] = [
        { id: 1, name: 'Grupo 1' },
        { id: 2, name: 'Grupo 2' },
        { id: 3, name: 'Grupo 3' },
        { id: 4, name: 'Grupo 4' },
        { id: 5, name: 'Grupo 5' },
    ];

    return (
        <div className="w-64 bg-[#e09f3ee5] border-r border-[#dfdfdf]">
            <div className="p-6">
                <h2 className="text-2xl font-semibold mb-6">Grupos De Interés</h2>

                <div className="space-y-3">
                    {interestGroups.map((group) => (
                        <button
                            key={group.id}
                            className={`flex items-center w-full p-2 rounded-lg ${
                                selectedGroup === group.id ? 'bg-[#f7f7f7]' : 'bg-white'
                            }`}
                            onClick={() => setSelectedGroup(group.id)}
                        >
                            <div className="w-6 h-6 rounded-full bg-[#d9d9d9]"></div>
                            <span className="ml-4 font-medium">{group.name}</span>
                        </button>
                    ))}
                </div>
            </div>
        </div>
    );
};

export default Sidebar;