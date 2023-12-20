import { Link } from 'react-router-dom';

const List = ({ array, rowCount, position, maxInRow }) => {
  return (
    <div>
      <div className={position === 'horizontal' ? 'flex space-x-9' : 'grid'}>
        {array.map((item, i) => (
          <div key={i} className="align-center">
            <Link to={`profile/${item.login}`}>
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
