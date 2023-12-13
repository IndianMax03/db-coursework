import Catalog from '../components/Catalog';
import FriendList from '../components/FriendList';
import LoginForm from '../components/LoginForm';
import Profile from '../components/Profile';
import { Navigate } from 'react-router-dom';

export const privateRoutes = [
  { path: '/friends', element: <FriendList /> },
  { path: '/catalog', element: <Catalog /> },
  { path: '/profile', element: <Profile /> },
  { path: '*', element: <Navigate to="/profile" replace /> }
];

export const publicRoutes = [
  { path: '/login', element: <LoginForm /> },
  { path: '*', element: <Navigate to="/login" replace /> }
];
