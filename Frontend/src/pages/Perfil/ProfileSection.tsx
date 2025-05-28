import React from 'react';
import Card from '../../components/common/Perfil/Card';
import Button from '../../components/ui/Perfil/Button';
import Tag from '../../components/ui/Perfil/Tag';
import { UserProfile } from '../../types/Perfil';

interface ProfileSectionProps {
  profile: UserProfile;
  onFollow: () => void;
}

const ProfileSection: React.FC<ProfileSectionProps> = ({ profile, onFollow }) => {
  return (
    <div className="flex flex-col md:flex-row gap-0 w-full">
      <div className="w-full md:w-[455px] h-[544px]">
        <img
          src={profile.profileImage}
          alt="Profile"
          className="w-full h-full object-cover"
        />
      </div>

      <div className="flex flex-col">
        <Card
          className="w-full md:w-[315px] h-[544px] p-4 relative"
          hasShadow={true}
          hasBorder={true}
          borderColor="#e09f3ee5"
        >
          <div className="flex justify-center mt-3 mb-6">
            <h1 className="text-2xl font-semibold text-[#1e1e1e]">
              {profile.nickname}
            </h1>
          </div>

          <div className="mt-6">
            <p className="text-base text-[#757575] text-center">
              {profile.name}
            </p>
          </div>

          <div className="mt-6">
            <p className="text-base text-[#757575]">
              # Numero seguidores
            </p>
          </div>

          <div className="mt-6">
            <p className="text-base text-[#757575]">
              # Numero de seguidos
            </p>
          </div>

          <div className="flex flex-col items-center mt-6 space-y-4">
            <Tag
              text="Activo"
              color="#e09f3ee5"
              className="text-xl font-semibold"
            />

            <Tag
              text="Carrera"
              color="#335c67"
              className="text-xl font-semibold"
            />

            <Tag
              text="Tipo Usuario"
              color="#333355"
              className="text-xl font-semibold"
            />
          </div>

          <div className="absolute bottom-[165px] left-0 w-full flex justify-center">
            <Button
              variant="primary"
              size="sm"
              className="w-[82px] h-[40px] flex items-center justify-center"
              onClick={onFollow}
            >
              Seguir
            </Button>
          </div>
        </Card>
      </div>
    </div>
  );
};

export default ProfileSection;