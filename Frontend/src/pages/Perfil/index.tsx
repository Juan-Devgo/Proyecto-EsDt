import React, { useState } from 'react';
import ProfileSection from './ProfileSection';
import PublicationsSection from './PublicationsSection';
import GroupSection from './GroupSection';
import { UserProfile, Publication, Group } from '../../types/Perfil';

const Perfil: React.FC = () => {
    // Mock data
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
        { id: '1', title: 'Título Publicación', link: '#' },
        { id: '2', title: 'Título Publicación', link: '#' },
        { id: '3', title: 'Título Publicación', link: '#' },
        { id: '4', title: 'Título Publicación', link: '#' },
        { id: '5', title: 'Título Publicación', link: '#' }
    ]);

    const [group] = useState<Group>({
        name: 'Grupo a el que pertenece',
        description: 'Descripcion'
    });

    const [isFollowing, setIsFollowing] = useState(false);

    const handleFollow = () => {
        setIsFollowing(!isFollowing);
        // In a real application, you would make an API call here
        console.log(`User ${isFollowing ? 'unfollowed' : 'followed'} successfully`);
    };

    return (
        <div className="bg-white min-h-screen p-16">
            <div className="max-w-[1101px] mx-auto">
                <div className="mb-12">
                    <ProfileSection
                        profile={profile}
                        onFollow={handleFollow}
                    />
                </div>

                <div className="flex flex-col md:flex-row gap-8">
                    <PublicationsSection publications={publications} />
                    <GroupSection group={group} />
                </div>
            </div>
        </div>
    );
};

export default Perfil;