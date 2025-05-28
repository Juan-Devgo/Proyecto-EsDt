import React from 'react';

interface HeaderProps {
    user: {
        name: string;
        career: string;
        avatar: string;
    };
}

const Header: React.FC<HeaderProps> = ({ user }) => {
    return (
        <div className="bg-accent-teal bg-opacity-50 h-[78px] w-full flex items-center px-6 border-b border-border-primary">
            <div className="flex items-center">
                <img
                    src={user.avatar}
                    alt="User avatar"
                    className="w-12 h-12 rounded-full object-cover"
                />
                <div className="ml-4">
                    <h3 className="text-text-primary text-base font-medium">{user.name}</h3>
                    <p className="text-text-secondary text-base font-normal">{user.career}</p>
                </div>
            </div>
        </div>
    );
};

export default Header;