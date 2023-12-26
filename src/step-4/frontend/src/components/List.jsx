import { Link } from 'react-router-dom';

const List = ({ array, position, maxInRow }) => {
  return (
    <div>
      <div className={position === 'horizontal' ? `grid grid-cols-${maxInRow} gap-4` : 'grid'}>
        {array.map((item, i) => (
          <div key={i} className="">
            <Link
              to={`/profile/${item.login}`}
              className="grid justify-center align justify-items-center"
            >
              <img src="pfp.jpg" alt="profile" className=" h-14 rounded-full"></img>
              <div>{item.login}</div>
            </Link>
          </div>
        ))}
      </div>
    </div>
  );
};

export default List;
