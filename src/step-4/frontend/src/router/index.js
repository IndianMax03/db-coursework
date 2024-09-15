import Catalog from '../components/Catalog/Catalog';
import Friends from '../components/Friends/Friends';
import LoginForm from '../components/Auth/LoginForm';
import Profile from '../components/Profile/Profile';
import { Navigate } from 'react-router-dom';
import RegisterForm from '../components/Auth/RegisterForm';
import CharacterCreationForm from '../components/CharacterCreationForm';
import GameCreationForm from '../components/GameCreationForm';
import CharacterSelection from '../components/CharacterSelection';
import Requests from '../components/Requests/Requests';
import Lobby from '../components/Lobby';
import FriendsPage from '../components/Friends/FriendsPage';

export const privateRoutes = [
  { path: '/friends', element: <FriendsPage /> },
  { path: '/catalog', element: <Catalog /> },
  { path: '/profile/:login', element: <Profile /> },
  { path: '*', element: <Navigate to="/profile" replace /> },
  { path: '/lobby/:lobbyId', element: <Lobby /> },
  { path: '/character-creation', element: <CharacterCreationForm /> },
  { path: '/game-creation', element: <GameCreationForm /> },
  { path: '/character-selection/:gameId', element: <CharacterSelection /> },
  { path: '/requests', element: <Requests /> }
];

export const publicRoutes = [
  { path: '/login', element: <LoginForm /> },
  { path: '/register', element: <RegisterForm /> }
  // { path: '*', element: <Navigate to="/login" replace /> }
];
