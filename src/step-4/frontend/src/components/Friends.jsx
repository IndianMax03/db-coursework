import UserList from './UserList';

const Friends = () => {
  return (
    <div className="">
      <UserList
        userArray={[{ name: 'friend1' }, { name: 'friend2' }]}
        rowCount={2}
        position="horizontal"
      />
    </div>
  );
};

export default Friends;
