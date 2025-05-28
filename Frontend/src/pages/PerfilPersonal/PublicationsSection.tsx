import React from 'react';
import { Publication } from '../../types/Profile';

interface PublicationsSectionProps {
  publications: Publication[];
}

const PublicationsSection: React.FC<PublicationsSectionProps> = ({ publications }) => {
  return (
    <div className="w-full md:w-[448px] bg-white shadow-sm border border-[#dfdfdf] rounded-lg">
      <div className="p-6">
        <h3 className="text-base font-semibold text-black mb-4">Publicaciones más valoradas</h3>

        <div className="bg-[#f5f5f5] border border-[#757575] rounded-lg shadow-sm p-6">
          {publications.map((publication, index) => (
            <div key={publication.id} className={`flex items-start ${index > 0 ? 'mt-6' : ''}`}>
              <img src="/images/img_star.svg" alt="Star" className="w-5 h-5 mt-0.5" />
              <div className="ml-3">
                <p className="text-base text-[#1e1e1e]">{publication.title}</p>
                <a
                  href={publication.link}
                  className="text-sm text-[#757575] block mt-1 hover:underline"
                >
                  ver publicación{index === 2 ? '.' : ''}
                </a>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default PublicationsSection;