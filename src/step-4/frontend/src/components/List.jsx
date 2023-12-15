const List = ({ array, rowCount, position, maxInRow }) => {
  return (
    <div>
      <div className={position === 'horizontal' ? 'flex space-x-9' : 'grid'}>
        {array.map((item) => (
          <div>
            <img src="pfp.jpg" alt="profile" className=" h-14 rounded-full"></img>
            <div>{item.name}</div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default List;
