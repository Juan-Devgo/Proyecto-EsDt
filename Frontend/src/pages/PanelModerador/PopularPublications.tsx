import React from "react";
import Card from "../../components/common/PanelModerador/Card";
import { Publication } from "../../types/PanelModerador";

interface PopularPublicationsProps {
  publications?: Publication[];
}

const PopularPublications: React.FC<PopularPublicationsProps> = ({
                                                                   publications = [
                                                                     { id: 1, title: "Título Publicación", link: "#" },
                                                                     { id: 2, title: "Título Publicación", link: "#" },
                                                                     { id: 3, title: "Título Publicación", link: "#" },
                                                                     { id: 4, title: "Título Publicación", link: "#" },
                                                                     { id: 5, title: "Título Publicación", link: "#" },
                                                                   ]
                                                                 }) => {
  return (
    <Card title="Publicaciones más valoradas" className="h-[733px]">
      <div className="bg-white shadow-[0px_4px_4px_#0c0c0d0c] border border-[#d9d9d9] rounded-lg p-6">
        {publications.map((publication, index) => (
          <div key={publication.id} className="mb-8 last:mb-0">
            <div className="flex items-start">
              <img src="/images/img_star.svg" alt="Star icon" className="w-5 h-5 mt-1" />
              <div className="ml-3">
                <p className="text-base text-[#1e1e1e]">{publication.title}</p>
                <a
                  href={publication.link}
                  className="text-sm text-[#757575] hover:underline"
                >
                  {index === 2 ? "ver publicación." : "ver publicación"}
                </a>
              </div>
            </div>
          </div>
        ))}
      </div>
    </Card>
  );
};

export default PopularPublications;