import Catalog from '../components/Catalog';
import Friends from '../components/Friends';
import LoginForm from '../components/LoginForm';
import Profile from '../components/Profile';
import { Navigate } from 'react-router-dom';
import RegisterForm from '../components/RegisterForm';
import LobbyPage from '../components/LobbyPage';

export const privateRoutes = [
  { path: '/friends', element: <Friends /> },
  { path: '/catalog', element: <Catalog /> },
  { path: '/profile', element: <Profile /> },
  { path: '*', element: <Navigate to="/profile" replace /> },
  { path: '/lobby', element: <LobbyPage /> },
  //   TODO: delete when auth is done
  { path: '/register', element: <RegisterForm /> },
  { path: '/login', element: <LoginForm /> }
];

export const publicRoutes = [
  { path: '/login', element: <LoginForm /> },
  { path: '/register', element: <RegisterForm /> },
  { path: '*', element: <Navigate to="/login" replace /> }
];
