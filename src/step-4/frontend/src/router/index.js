import Catalog from '../components/Catalog/Catalog';
import Friends from '../components/Friends/Friends';
import LoginForm from '../components/Auth/LoginForm';
import Profile from '../components/Profile/Profile';
import { Navigate } from 'react-router-dom';
import RegisterForm from '../components/Auth/RegisterForm';
import LobbyPage from '../components/LobbyPage';
import CharacterCreationForm from '../components/CharacterCreationForm';
import GameCreationForm from '../components/GameCreationForm';
import CharacterSelection from '../components/CharacterSelection';
import Requests from '../components/Requests/Requests';

export const privateRoutes = [
  { path: '/friends', element: <Friends /> },
  { path: '/catalog', element: <Catalog /> },
  { path: '/profile', element: <Profile /> },
  { path: '*', element: <Navigate to="/profile" replace /> },
  { path: '/lobby', element: <LobbyPage /> },
  { path: '/character-creation', element: <CharacterCreationForm /> },
  { path: '/game-creation', element: <GameCreationForm /> },
  { path: '/character-selection', element: <CharacterSelection /> },
  { path: '/requests', element: <Requests /> },
  //   TODO: delete when auth is done
  { path: '/register', element: <RegisterForm /> },
  { path: '/login', element: <LoginForm /> }
];

export const publicRoutes = [
  { path: '/login', element: <LoginForm /> },
  { path: '/register', element: <RegisterForm /> },
  { path: '*', element: <Navigate to="/login" replace /> }
];
