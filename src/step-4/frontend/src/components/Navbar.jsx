import { Link } from 'react-router-dom';
import { CgProfile } from 'react-icons/cg';
import { GrCatalog } from 'react-icons/gr';
import { FaUserFriends } from 'react-icons/fa';
import { CiLogout } from 'react-icons/ci';

const Navbar = () => {
  return (
    <div className="grid h-32 sticky top-10">
      <Link to={'/profile'} className="flex space-x-4">
        <CgProfile className="h-6" />
        <div>Мой профиль</div>
      </Link>
      <Link to={'/catalog'} className="flex space-x-4">
        <GrCatalog className="h-6" />
        <div>Каталог</div>
      </Link>
      <Link to={'/friends'} className="flex space-x-4">
        <FaUserFriends className="h-6" />
        <div>Друзья</div>
      </Link>
      <Link to={'/login'} className="flex space-x-4">
        <CiLogout className="h-6" />
        <div>Выйти</div>
      </Link>
    </div>
  );
};

export default Navbar;
