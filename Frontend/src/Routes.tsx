import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

// Import page components
import PublicationsFeed from './pages/Feed';
import Chat from './pages/Chat';
import PanelModerador from './pages/PanelModerador';
import Registro from './pages/Registro';
import DetallesPublicacion from './pages/DetallesPublicacion';
import InicioSesion from './pages/InicioSesion';
import Perfil from './pages/Perfil';

const AppRoutes = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<PublicationsFeed />} />
        <Route path="/feed" element={<div>Feed</div>} />
        <Route path="/" element={<Chat />} />
        <Route path="/chat" element={<Chat />} />
        <Route path="/" element={<PanelModerador />} />
        <Route path="/dashboard" element={<PanelModerador />} />
        <Route path="/register" element={<Registro />} />
        <Route path="/login" element={<InicioSesion />} />
        <Route path="/" element={<InicioSesion />} />
        <Route path="/post-detail" element={<DetallesPublicacion />} />
        <Route path="/" element={<DetallesPublicacion />} />
        <Route path="/profile" element={<Perfil />} />
      </Routes>
    </Router>
  );
};

export default AppRoutes;
