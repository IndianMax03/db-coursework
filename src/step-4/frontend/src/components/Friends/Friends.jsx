import List from '../List';

const Friends = () => {
  return (
    <div className="">
      <List
        array={[{ name: 'friend1' }, { name: 'friend2' }]}
        rowCount={2}
        position="horizontal"
      />
    </div>
  );
};

export default Friends;
