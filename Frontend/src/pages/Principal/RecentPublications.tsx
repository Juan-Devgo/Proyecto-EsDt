import React from "react";
import Card from "../../components/common/Feed/Card.tsx";

interface Publication {
  id: number;
  title: string;
  author: string;
  image: string;
}

interface RecentPublicationsProps {
  publications: Publication[];
}

const RecentPublications: React.FC<RecentPublicationsProps> = ({ publications }) => {
  return (
    <section className="mb-12">
      <h2 className="text-3xl font-semibold mb-6">Publicaciones recientes</h2>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        {publications.map((publication) => (
          <Card
            key={publication.id}
            title={publication.title}
            author={publication.author}
            image={publication.image}
            onClick={() => console.log(`Publication ${publication.id} clicked`)}
          />
        ))}
      </div>
    </section>
  );
};

export default RecentPublications;