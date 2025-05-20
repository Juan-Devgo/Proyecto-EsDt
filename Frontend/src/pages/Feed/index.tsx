import React, {useEffect, useState} from "react";
import Sidebar from "../../components/common/Sidebar";
import Header from "../../components/common/Header";
import RecentPublications from "./RecentPublications";
import RecommendedContent from "./RecommendedContent";

const PublicationsFeed: React.FC = () => {
  const [data, setData] = useState([])

  useEffect(()=>{
    (async ()=>{
      const res = await fetch("http://localhost:4029/recommended-content")
      const newData = await res.json();
      setData(newData ?? []);
    })()
  }, [])

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
          <RecentPublications publications={data} />
          <RecommendedContent publications={data} />
        </main>
      </div>
    </div>
  );
};

export default PublicationsFeed;