import React from 'react';

interface SearchSectionProps {
  searchQuery: string;
  onSearch: (query: string) => void;
  onFilter: () => void;
}

const SearchSection: React.FC<SearchSectionProps> = ({ searchQuery, onSearch, onFilter }) => {
  return (
    <div className="mb-8">
      <div className="flex items-center justify-between">
        <h1 className="text-xl font-semibold">Busca a tus amigos</h1>
        <div className="flex items-center">
          <img
            src="/images/img_616ab3338e874f96ab6b56159dd87ea5_3.png"
            alt="Brain Loop Logo"
            className="h-[73px] w-[203px]"
          />
        </div>
      </div>

      <div className="flex mt-4 gap-4">
        <div className="relative flex-1">
          <div className="flex items-center border border-[#dfdfdf] rounded-lg px-3 py-2 bg-white">
            <img src="/images/img_search.svg" alt="Search" className="w-6 h-6 mr-3" />
            <input
              type="text"
              placeholder="Search tickets..."
              className="w-full outline-none text-base text-gray-500"
              value={searchQuery}
              onChange={(e) => onSearch(e.target.value)}
            />
          </div>
        </div>

        <button
          onClick={onFilter}
          className="flex items-center px-3 py-2 bg-[#9e2a2b7f] border border-[#dfdfdf] rounded-lg"
        >
          <img src="/images/img_filter.svg" alt="Filter" className="w-6 h-6 mr-3" />
          <span className="text-black/40">Filter</span>
        </button>
      </div>

      <h2 className="text-xl font-semibold mt-8">Personas que quizás conozcas:</h2>
    </div>
  );
};

export default SearchSection;