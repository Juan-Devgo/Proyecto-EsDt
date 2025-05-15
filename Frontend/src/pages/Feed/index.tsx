import React from "react";
import Sidebar from "../../components/common/Sidebar";
import Header from "../../components/common/Header";
import RecentPublications from "./RecentPublications";
import RecommendedContent from "./RecommendedContent";

const PublicationsFeed: React.FC = () => {
  // Mock data for recent publications
  const recentPublications = [
    {
      id: 1,
      title: "Título Publicación",
      author: "Nombre de Usuario",
      image: "/images/img_chatgpt_image_12_abr_2025_163213_1.png",
    },
    {
      id: 2,
      title: "Título Publicación",
      author: "Nombre de Usuario",
      image: "/images/img_chatgpt_image_12_abr_2025_163213_1.png",
    },
    {
      id: 3,
      title: "Título Publicación",
      author: "Nombre de Usuario",
      image: "/images/img_chatgpt_image_12_abr_2025_163213_1.png",
    },
    {
      id: 4,
      title: "Título Publicación",
      author: "Nombre de Usuario",
      image: "/images/img_chatgpt_image_12_abr_2025_163213_1.png",
    },
  ];

  // Mock data for recommended content
  const recommendedContent = [
    {
      id: 1,
      title: "Titulo Publicación",
      author: "Nombre de Usuario",
      image: "/images/img_chatgpt_image_12_abr_2025_050726_pm_1.png",
    },
    {
      id: 2,
      title: "Titulo Publicación",
      author: "Nombre de Usuario",
      image: "/images/img_chatgpt_image_12_abr_2025_050726_pm_1.png",
    },
    {
      id: 3,
      title: "Titulo Publicación",
      author: "Nombre de Usuario",
      image: "/images/img_chatgpt_image_12_abr_2025_050726_pm_1.png",
    },
    {
      id: 4,
      title: "Titulo Publicación",
      author: "Nombre de Usuario",
      image: "/images/img_chatgpt_image_12_abr_2025_050726_pm_1.png",
    },
    {
      id: 5,
      title: "Titulo Publicación",
      author: "Nombre de Usuario",
      image: "/images/img_chatgpt_image_12_abr_2025_050726_pm_1.png",
    },
    {
      id: 6,
      title: "Titulo Publicación",
      author: "Nombre de Usuario",
      image: "/images/img_chatgpt_image_12_abr_2025_050726_pm_1.png",
    },
  ];

  return (
    <div className="flex min-h-screen bg-white">
      {/* Sidebar */}
      <div className="w-64 fixed h-full">
        <Sidebar />
      </div>

      {/* Main content */}
      <div className="ml-64 flex-1">
        <Header />
        
        <main className="p-6">
          <RecentPublications publications={recentPublications} />
          <RecommendedContent publications={recommendedContent} />
        </main>
      </div>
    </div>
  );
};

export default PublicationsFeed;