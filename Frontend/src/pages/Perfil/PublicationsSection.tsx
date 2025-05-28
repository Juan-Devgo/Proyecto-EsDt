import React from 'react';
import Card from '../../../components/common/Card';
import { Publication } from '../../../types/Profile';

interface PublicationsSectionProps {
  publications: Publication[];
}

const PublicationsSection: React.FC<PublicationsSectionProps> = ({ publications }) => {
  return (
    <Card
      className="w-full md:w-[448px] p-6"
      hasShadow={true}
      hasBorder={true}
    >
      <h2 className="text-base font-semibold text-black mb-4">
        Publicaciones más valoradas
      </h2>

      <div className="bg-[#f5f5f5] border border-[#757575] rounded-lg shadow-sm p-4">
        {publications.map((publication, index) => (
          <div key={publication.id} className="mb-6 last:mb-0">
            <div className="flex items-start">
              <img
                src="/images/img_star.svg"
                alt="Star"
                className="w-5 h-5 mt-1 mr-3"
              />
              <div>
                <p className="text-base text-[#1e1e1e]">{publication.title}</p>
                <a
                  href={publication.link}
                  className="text-sm text-[#757575] hover:underline"
                >
                  ver publicación{index === 2 ? '.' : ''}
                </a>
              </div>
            </div>
          </div>
        ))}
      </div>
    </Card>
  );
};

export default PublicationsSection;