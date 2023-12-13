import { Link } from 'react-router-dom';

const Navbar = () => {
  return (
    <div className="grid h-32 sticky top-10">
      <Link to={'/profile'}>Мой профиль</Link>
      <Link to={'/catalog'}>Каталог</Link>
      <Link to={'/friends'}>Друзья</Link>
      <Link>Выйти</Link>
    </div>
  );
};

export default Navbar;
