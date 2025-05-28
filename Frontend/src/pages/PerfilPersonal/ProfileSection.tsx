import React from 'react';
import { UserProfile } from '../../types/Profile';
import { Button } from '../../components/ui/Button';

interface ProfileSectionProps {
  profile: UserProfile;
  onUpdate: () => void;
}

const ProfileSection: React.FC<ProfileSectionProps> = ({ profile, onUpdate }) => {
  return (
    <div className="flex flex-col md:flex-row gap-0 w-full">
      <div className="w-full md:w-[455px] h-[544px]">
        <img
          src={profile.profileImage}
          alt="Profile"
          className="w-full h-full object-cover"
        />
      </div>

      <div className="w-full md:w-[315px] h-[544px] bg-white shadow-md border border-[#e09f3ee5] relative">
        <div className="mt-[13px] text-center">
          <h2 className="text-2xl font-semibold text-[#1e1e1e]">{profile.nickname}</h2>
        </div>

        <div className="mt-[62px] px-5">
          <p className="text-base text-[#757575] text-center">{profile.name}</p>
        </div>

        <div className="mt-[38px] px-5">
          <p className="text-base text-[#757575]"># Numero seguidores</p>
        </div>

        <div className="mt-[38px] px-5">
          <p className="text-base text-[#757575]"># Numero de seguidos</p>
        </div>

        <div className="mt-[38px] text-center">
          <p className="text-xl font-semibold text-[#e09f3ee5]">{profile.status}</p>
        </div>

        <div className="mt-[46px] text-center">
          <p className="text-xl font-semibold text-[#335c67]">{profile.career}</p>
        </div>

        <div className="mt-[48px] text-center">
          <p className="text-xl font-semibold text-[#333355]">{profile.userType}</p>
        </div>

        <div className="absolute bottom-[165px] left-1/2 transform -translate-x-1/2">
          <Button
            className="bg-[#9e2a2bcc] text-white rounded-lg px-4 py-2 w-[109px] h-[40px]"
            onClick={onUpdate}
          >
            Actualizar
          </Button>
        </div>
      </div>
    </div>
  );
};

export default ProfileSection;