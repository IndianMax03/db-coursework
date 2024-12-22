import { Route, Routes, useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { selectSelf } from '../redux/slices/UserSlice';
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
  { path: '*', element: <Navigate to="/login" replace /> },
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

const AppRouter = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(Cookies.get('token') !== undefined);
  const navigate = useNavigate();
  const user = useSelector(selectSelf);

  useEffect(() => {
    const token = Cookies.get('token');
    setIsLoggedIn(token !== undefined);
    if (!isLoggedIn) {
      navigate('/login');
    }
  }, [isLoggedIn, navigate, user]);

  return (
    <div className="w-128">
      <Routes>
        {isLoggedIn
          ? privateRoutes.map((route) => (
              <Route path={route.path} element={route.element} key={route.path} />
            ))
          : publicRoutes.map((route) => (
              <Route path={route.path} element={route.element} key={route.path} />
            ))}
      </Routes>
    </div>
  );
};

export default AppRouter;
