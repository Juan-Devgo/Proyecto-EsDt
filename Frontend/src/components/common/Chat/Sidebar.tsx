import React, { useState } from 'react';
import { User } from '../../types/Chat';

interface SidebarProps {
    contacts: User[];
    activeContactId: string;
    onContactSelect: (contactId: string) => void;
    logo: string;
}

const Sidebar: React.FC<SidebarProps> = ({
                                             contacts,
                                             activeContactId,
                                             onContactSelect,
                                             logo
                                         }) => {
    const [searchQuery, setSearchQuery] = useState('');

    const filteredContacts = contacts.filter(contact =>
        contact.name.toLowerCase().includes(searchQuery.toLowerCase())
    );

    return (
        <div className="w-[400px] h-full bg-white border-r border-border-primary flex flex-col">
            <div className="p-4 flex justify-center">
                <img src={logo} alt="Brain Loop Logo" className="h-[78px]" />
            </div>

            <div className="px-3 py-4">
                <div className="bg-accent-teal bg-opacity-30 rounded-lg flex items-center px-3 py-2 mb-4">
                    <img src="/images/img_search.svg" alt="Search" className="w-6 h-6 mr-2" />
                    <input
                        type="text"
                        placeholder="Buscar Chat"
                        className="bg-transparent text-text-tertiary text-base outline-none w-full"
                        value={searchQuery}
                        onChange={(e) => setSearchQuery(e.target.value)}
                    />
                </div>

                <div className="overflow-y-auto h-[calc(100vh-180px)]">
                    {filteredContacts.map(contact => (
                        <div
                            key={contact.id}
                            className={`flex items-center p-4 cursor-pointer ${activeContactId === contact.id ? 'bg-secondary-gray' : 'hover:bg-secondary-light'}`}
                            onClick={() => onContactSelect(contact.id)}
                        >
                            <div className="w-16 h-16 bg-secondary-gray rounded-full overflow-hidden">
                                <img
                                    src={contact.avatar}
                                    alt={`${contact.name} avatar`}
                                    className="w-full h-full object-cover rounded-full"
                                />
                            </div>
                            <div className="ml-4">
                                <h3 className="text-xl font-medium text-text-primary">{contact.name}</h3>
                                <p className="text-base text-text-secondary">{contact.career}</p>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
};

export default Sidebar;