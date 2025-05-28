import React, { useState } from 'react';
import Sidebar from '../../components/common/Recomendaciones/Sidebar.tsx';
import SearchSection from './SearchSection.tsx';
import MainContent from './MainContent.tsx';

interface User {
  id: number;
  name: string;
  career: string;
  commonProject: string;
  avatar: string;
}

const Recomendaciones: React.FC = () => {
  const [searchQuery, setSearchQuery] = useState<string>('');
  const [filteredUsers, setFilteredUsers] = useState<User[]>([]);

  // Mock data for users
  const users: User[] = [
    { id: 1, name: 'Nombre de usuario', career: 'Carrera', commonProject: 'Project 1', avatar: '/images/img_avatar.png' },
    { id: 2, name: 'Nombre de usuario', career: 'Carrera', commonProject: 'Project 1', avatar: '/images/img_avatar.png' },
    { id: 3, name: 'Nombre de usuario', career: 'Carrera', commonProject: 'Project 1', avatar: '/images/img_avatar.png' },
    { id: 4, name: 'Nombre de usuario', career: 'Carrera', commonProject: 'Project 1', avatar: '/images/img_avatar.png' },
    { id: 5, name: 'Nombre de usuario', career: 'Carrera', commonProject: 'Project 1', avatar: '/images/img_avatar.png' },
    { id: 6, name: 'Nombre de usuario', career: 'Carrera', commonProject: 'Project 1', avatar: '/images/img_avatar.png' },
    { id: 7, name: 'Nombre de usuario', career: 'Carrera', commonProject: 'Project 1', avatar: '/images/img_avatar.png' },
    { id: 8, name: 'Nombre de usuario', career: 'Carrera', commonProject: 'Project 1', avatar: '/images/img_avatar.png' },
    { id: 9, name: 'Nombre de usuario', career: 'Carrera', commonProject: 'Project 1', avatar: '/images/img_avatar.png' },
    { id: 10, name: 'Nombre de usuario', career: 'Carrera', commonProject: 'Project 1', avatar: '/images/img_avatar.png' },
  ];

  const handleSearch = (query: string) => {
    setSearchQuery(query);
    if (query.trim() === '') {
      setFilteredUsers(users);
    } else {
      const filtered = users.filter(user =>
        user.name.toLowerCase().includes(query.toLowerCase()) ||
        user.career.toLowerCase().includes(query.toLowerCase()) ||
        user.commonProject.toLowerCase().includes(query.toLowerCase())
      );
      setFilteredUsers(filtered);
    }
  };

  const handleFilter = () => {
    // Implement filtering logic here
    alert('Filter functionality would be implemented here');
  };

  return (
    <div className="flex h-screen bg-white">
      <Sidebar />
      <div className="flex-1 overflow-auto">
        <div className="p-6">
          <SearchSection
            searchQuery={searchQuery}
            onSearch={handleSearch}
            onFilter={handleFilter}
          />
          <MainContent users={searchQuery ? filteredUsers : users} />
        </div>
      </div>
    </div>
  );
};

export default Recomendaciones;