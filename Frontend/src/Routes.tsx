import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

// Import page components
import PublicationsFeed from './pages/Feed';

const AppRoutes = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<PublicationsFeed />} />
        <Route path="/feed" element={<div>Feed</div>} />
      </Routes>
    </Router>
  );
};

export default AppRoutes;