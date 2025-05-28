import React from "react";

interface CardProps {
  title: string;
  children: React.ReactNode;
  className?: string;
}

const Card: React.FC<CardProps> = ({ title, children, className = "" }) => {
  return (
    <div className={`bg-white border border-[#dfdfdf] rounded-lg shadow-[0px_4px_12px_#0000000a] ${className}`}>
      <h2 className="text-base font-semibold text-black p-6 pb-4">{title}</h2>
      <div className="px-6 pb-6">
        {children}
      </div>
    </div>
  );
};

export default Card;