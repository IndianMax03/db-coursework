import { useDispatch, useSelector } from 'react-redux';
import List from '../List';
import { selectSelf } from '../../redux/slices/UserSlice';
import { useEffect } from 'react';

const Friends = () => {
  // const dispatch = useDispatch();
  // const user = useSelector(selectSelf);

  // useEffect(() => {
  //   dispatch(fetch);
  // });

  return (
    <div className="">
      <List array={[{ name: 'friend1' }, { name: 'friend2' }]} maxInRow={4} position="horizontal" />
    </div>
  );
};

export default Friends;
