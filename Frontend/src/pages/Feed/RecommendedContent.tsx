import React from "react";
import Card from "../../components/common/Card";

interface Publication {
  id: number;
  title: string;
  author: string;
  image: string;
}

interface RecommendedContentProps {
  publications: Publication[];
}

const RecommendedContent: React.FC<RecommendedContentProps> = ({ publications }) => {
  return (
    <section>
      <h2 className="text-3xl font-semibold mb-6">Te podría gustar</h2>
      <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-4">
        {publications.map((publication) => (
          <Card
            key={publication.id}
            title={publication.title}
            author={publication.author}
            image={publication.image}
            onClick={() => console.log(`Recommended publication ${publication.id} clicked`)}
          />
        ))}
      </div>
    </section>
  );
};

export default RecommendedContent;