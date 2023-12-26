import List from '../List';

const Friends = () => {
  return (
    <div className="">
      <List array={[{ name: 'friend1' }, { name: 'friend2' }]} maxInRow={4} position="horizontal" />
    </div>
  );
};

export default Friends;
