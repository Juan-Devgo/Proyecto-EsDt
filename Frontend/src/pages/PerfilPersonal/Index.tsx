import React, { useState } from 'react';
import ProfileSection from './ProfileSection';
import PublicationsSection from './PublicationsSection';
import GroupSection from './GroupSection';
import { UserProfile, Publication, Group } from '../../types/Profile';

const ProfilePage: React.FC = () => {
  //Mock data
  const [profile] = useState<UserProfile>({
    nickname: 'Nickname Usuario',
    name: 'Nombre Usuario',
    followersCount: 0,
    followingCount: 0,
    status: 'Activo',
    career: 'Carrera',
    userType: 'Tipo Usuario',
    profileImage: '/images/img_image.png'
  });

  const [publications] = useState<Publication[]>([
    { id: 1, title: 'Título Publicación', link: '#' },
    { id: 2, title: 'Título Publicación', link: '#' },
    { id: 3, title: 'Título Publicación', link: '#' },
    { id: 4, title: 'Título Publicación', link: '#' },
    { id: 5, title: 'Título Publicación', link: '#' }
  ]);

  const [group] = useState<Group>({
    id: 1,
    name: 'Grupo a el que pertenece',
    description: 'Descripcion'
  });

  const handleUpdateProfile = () => {
    // In a real application, this would open a form or modal to update the profile
    alert('Actualizar perfil');
  };

  return (
    <div className="bg-white min-h-screen">
      <div className="container mx-auto px-4 py-16">
        <div className="max-w-[1101px] mx-auto">
          {/* Profile Section */}
          <ProfileSection
            profile={profile}
            onUpdate={handleUpdateProfile}
          />

          {/* Publications and Group Section */}
          <div className="mt-12 flex flex-col md:flex-row gap-0">
            <PublicationsSection publications={publications} />
            <div className="mt-8 md:mt-0 md:ml-[108px]">
              <GroupSection group={group} />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;