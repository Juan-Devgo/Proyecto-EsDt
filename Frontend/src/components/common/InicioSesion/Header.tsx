import React from 'react';

interface HeaderProps {
    logoSrc: string;
}

const Header: React.FC<HeaderProps> = ({ logoSrc }) => {
    return (
        <header className="flex justify-center items-center py-8">
            <div className="w-auto h-auto">
                <img src={logoSrc} alt="Brain Loop Logo" className="max-w-full h-auto" />
            </div>
        </header>
    );
};

export default Header;