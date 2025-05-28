import React from "react";
import Header from "../../components/common/PanelModerador/Header";
import PopularPublications from "./PopularPublications";
import TopConnections from "./TopConnections";
import StudyCommunities from "./StudyCommunities";
import NetworkGraph from "./NetworkGraph";
import ShortestPath from "./ShortestPath";

const PanelModerador: React.FC = () => {
  const handleSearch = (query: string, type: 'content' | 'user') => {
    console.log(`Searching for ${type}: ${query}`);
    // In a real app, this would trigger an API call
  };

  return (
    <div className="min-h-screen bg-white">
      <Header />

      <div className="container mx-auto px-4 py-6">
        <div className="flex justify-center gap-8 mb-6">
          {/* Search bars */}
          <div className="relative">
            <input
              type="text"
              placeholder="Buscar contenido"
              className="h-10 w-[405px] pl-12 pr-4 rounded-lg bg-[#9e2a2bcc] text-[#f5f5f5] border border-[#dfdfdf] placeholder-[#f5f5f5]"
              onChange={(e) => handleSearch(e.target.value, 'content')}
            />
            <img
              src="/images/img_search.svg"
              alt="Search icon"
              className="absolute left-3 top-2 w-6 h-6"
            />
          </div>

          <div className="relative">
            <input
              type="text"
              placeholder="Buscar usuario"
              className="h-10 w-[405px] pl-12 pr-4 rounded-lg bg-[#335c6799] text-white border border-[#dfdfdf] placeholder-white"
              onChange={(e) => handleSearch(e.target.value, 'user')}
            />
            <img
              src="/images/img_search.svg"
              alt="Search icon"
              className="absolute left-3 top-2 w-6 h-6"
            />
          </div>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
          <div className="space-y-8">
            <PopularPublications />
          </div>

          <div className="space-y-8">
            <TopConnections />
            <NetworkGraph />
          </div>
        </div>

        <div className="mt-8 grid grid-cols-1 lg:grid-cols-2 gap-8">
          <StudyCommunities />
          <ShortestPath />
        </div>
      </div>
    </div>
  );
};

export default PanelModerador;