import { useSelector } from 'react-redux';
import { selectSelf } from '../../redux/slices/UserSlice';
import Friends from './Friends';

const FriendsPage = () => {
  const user = useSelector(selectSelf);

  return (
    <div>
      <Friends user={user} />
    </div>
  );
};

export default FriendsPage;
