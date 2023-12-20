import { Route, Routes } from 'react-router-dom';
import { publicRoutes, privateRoutes } from '.';

const AppRouter = () => {
  return (
    <div className="w-128">
      <Routes>
        {privateRoutes.map((route) => (
          <Route path={route.path} element={route.element} key={route.path} />
        ))}
      </Routes>
    </div>
  );
};

export default AppRouter;
