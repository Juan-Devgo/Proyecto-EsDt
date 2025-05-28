import React, { useState, useEffect } from 'react';
import { User, Message } from '../../types/Chat';
import ChatWindow from './ChatWindow';
import ContactList from './ContactList';

// Mock data
const mockCurrentUser: User = {
  id: 'user1',
  name: 'Nombre de Usuario',
  career: 'Carrera',
  avatar: '/images/img_rectangle_1.png'
};

const mockContacts: User[] = [
  {
    id: 'contact1',
    name: 'Nombre de usuario',
    career: 'Carrera',
    avatar: '/images/img_rectangle_1_64x64.png'
  },
  {
    id: 'contact2',
    name: 'Nombre de usuario',
    career: 'Carrera',
    avatar: '/images/img_rectangle_1_1.png'
  },
  {
    id: 'contact3',
    name: 'Nombre de usuario',
    career: 'Carrera',
    avatar: '/images/img_rectangle_1_2.png'
  },
  {
    id: 'contact4',
    name: 'Nombre de usuario',
    career: 'Carrera',
    avatar: '/images/img_rectangle_1_3.png'
  },
  {
    id: 'contact5',
    name: 'Nombre de usuario',
    career: 'Carrera',
    avatar: '/images/img_rectangle_1_4.png'
  },
  {
    id: 'contact6',
    name: 'Nombre de usuario',
    career: 'Carrera',
    avatar: '/images/img_rectangle_1_5.png'
  },
  {
    id: 'contact7',
    name: 'Nombre de usuario',
    career: 'Carrera',
    avatar: '/images/img_rectangle_1_6.png'
  },
  {
    id: 'contact8',
    name: 'Nombre de usuario',
    career: 'Carrera',
    avatar: '/images/img_rectangle_1_7.png'
  },
  {
    id: 'contact9',
    name: 'Nombre de usuario',
    career: 'Carrera',
    avatar: '/images/img_rectangle_1_8.png'
  }
];

// Mock conversation data
const mockMessages: Record<string, Message[]> = {
  contact1: [
    {
      id: 'm1',
      senderId: 'user1',
      text: 'miaw miaw',
      timestamp: new Date(),
      isRead: true
    },
    {
      id: 'm2',
      senderId: 'user1',
      text: 'miaw miawmiaw miawmiaw miawmiaw miawmiaw miaw',
      timestamp: new Date(),
      isRead: true
    },
    {
      id: 'm3',
      senderId: 'contact1',
      text: 'miaw miaw?',
      timestamp: new Date(),
      isRead: true
    },
    {
      id: 'm4',
      senderId: 'contact1',
      text: 'Cool',
      timestamp: new Date(),
      isRead: true
    },
    {
      id: 'm5',
      senderId: 'contact1',
      text: 'miaw miawmiaw miawmiaw miaw?',
      timestamp: new Date(),
      isRead: true
    },
    {
      id: 'm6',
      senderId: 'user1',
      text: 'miaw miawmiaw miaw',
      timestamp: new Date(),
      isRead: true
    },
    {
      id: 'm7',
      senderId: 'user1',
      text: 'miaw miawmiaw miawmiaw miawmiaw miawmiaw miawmiaw miawmiaw miawv',
      timestamp: new Date(),
      isRead: true
    },
    {
      id: 'm8',
      senderId: 'user1',
      text: 'miaw miaw',
      timestamp: new Date(),
      isRead: true
    },
    {
      id: 'm9',
      senderId: 'contact1',
      text: 'Hmmm',
      timestamp: new Date(),
      isRead: true
    },
    {
      id: 'm10',
      senderId: 'contact1',
      text: 'miaw miaw?miaw miaw?',
      timestamp: new Date(),
      isRead: true
    },
    {
      id: 'm11',
      senderId: 'contact1',
      text: 'miaw miaw?miaw miaw?miaw miaw?miaw miaw?',
      timestamp: new Date(),
      isRead: true
    }
  ],
  contact2: [
    {
      id: 'm1',
      senderId: 'user1',
      text: 'Hello there!',
      timestamp: new Date(),
      isRead: true
    },
    {
      id: 'm2',
      senderId: 'contact2',
      text: 'Hi! How are you?',
      timestamp: new Date(),
      isRead: true
    }
  ]
};

const ChatPage: React.FC = () => {
  const [activeContactId, setActiveContactId] = useState<string>('contact1');
  const [messages, setMessages] = useState<Message[]>(mockMessages[activeContactId] || []);

  useEffect(() => {
    setMessages(mockMessages[activeContactId] || []);
  }, [activeContactId]);

  const handleContactSelect = (contactId: string) => {
    setActiveContactId(contactId);
  };

  const handleSendMessage = (text: string) => {
    const newMessage: Message = {
      id: `m${Date.now()}`,
      senderId: mockCurrentUser.id,
      text,
      timestamp: new Date(),
      isRead: false
    };

    const updatedMessages = [...messages, newMessage];
    setMessages(updatedMessages);

    // Update mock data (in a real app, this would be an API call)
    mockMessages[activeContactId] = updatedMessages;
  };

  const activeContact = mockContacts.find(contact => contact.id === activeContactId) || mockContacts[0];

  return (
    <div className="flex h-screen bg-secondary-light">
      <ContactList
        contacts={mockContacts}
        activeContactId={activeContactId}
        onContactSelect={handleContactSelect}
        logo="/images/img_616ab3338e874f96ab6b56159dd87ea5_4.png"
      />

      <div className="flex-1">
        <ChatWindow
          messages={messages}
          currentUser={mockCurrentUser}
          activeContact={activeContact}
          onSendMessage={handleSendMessage}
        />
      </div>
    </div>
  );
};

export default ChatPage;