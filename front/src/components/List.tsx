import { Link } from 'react-router-dom';
import pfp from '../../public/pfp.jpg';

interface Props {
  array: any;
  position: string;
  maxInRow: number;
}

const List: React.FC<Props> = ({ array, position, maxInRow }) => {
  return (
    <div>
      <div className={position === 'horizontal' ? `grid grid-cols-${maxInRow} gap-4` : 'grid'}>
        {array.map((item: any, i: number) => (
          <div key={i} className="">
            <Link
              to={`/profile/${item.login}`}
              className="grid justify-center align justify-items-center"
            >
              {item.picture !== null ? (
                <img
                  src={`data:image/png;base64,${item.picture}`}
                  alt="profile"
                  className=" h-14 w-14 rounded-full"
                ></img>
              ) : (
                <img src={pfp} alt="profile" className=" h-14 w-14 rounded-full"></img>
              )}
              <div>{item.login}</div>
            </Link>
          </div>
        ))}
      </div>
    </div>
  );
};

export default List;
