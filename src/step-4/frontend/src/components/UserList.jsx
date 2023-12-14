const UserList = ({ userArray, rowCount, position, maxInRow }) => {
  return (
    <div>
      <div className={position === 'horizontal' ? 'flex space-x-9' : 'grid'}>
        {userArray.map((user) => (
          <div>
            <img src="pfp.jpg" alt="profile" className=" h-14 rounded-full"></img>
            <div>{user.name}</div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default UserList;
