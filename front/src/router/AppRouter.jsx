import { Route, Routes, useNavigate } from 'react-router-dom';
import { publicRoutes, privateRoutes } from '.';
import Cookies from 'js-cookie';
import { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { selectSelf } from '../redux/slices/UserSlice';

const AppRouter = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(Cookies.get('token') !== undefined);
  const navigate = useNavigate();
  const user = useSelector(selectSelf);

  useEffect(() => {
    const token = Cookies.get('token');
    setIsLoggedIn(token !== undefined);
    if (!isLoggedIn) {
      // navigate('/login');
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
