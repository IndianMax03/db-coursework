import { useDispatch, useSelector } from 'react-redux';
import List from '../List';
import { selectSelf } from '../../redux/slices/UserSlice';
import { useEffect } from 'react';
import {
  fetchFriendshipRequests,
  selectFriends,
  selectFriendshipRequests
} from '../../redux/slices/FriendsSlice';

const Friends = ({ user }) => {
  const dispatch = useDispatch();
  const friends = useSelector(selectFriends);

  useEffect(() => {
    dispatch(fetchFriendshipRequests(user.login));
  }, [dispatch, user.login]);

  return (
    <div className="">
      <List array={friends} maxInRow={4} position="horizontal" />
    </div>
  );
};

export default Friends;
