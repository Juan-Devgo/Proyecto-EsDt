import React from 'react';
import { User } from '../../types/Chat';
import Sidebar from '../../components/common/Chat/Sidebar.tsx';

interface ContactListProps {
  contacts: User[];
  activeContactId: string;
  onContactSelect: (contactId: string) => void;
  logo: string;
}

const ContactList: React.FC<ContactListProps> = ({contacts, activeContactId, onContactSelect, logo}) => {
  return (
    <Sidebar
      contacts={contacts}
      activeContactId={activeContactId}
      onContactSelect={onContactSelect}
      logo={logo}
    />
  );
};

export default ContactList;