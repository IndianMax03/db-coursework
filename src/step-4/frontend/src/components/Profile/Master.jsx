import { useDispatch, useSelector } from 'react-redux';
import Game from '../Game';
import { Link } from 'react-router-dom';
import { selectError, selectLoading, fetchUserGames } from '../../redux/slices/GameSlice';
import { useEffect } from 'react';
import Reviews from '../Reviews/Reviews';

const Master = ({ user, isMyProfile }) => {
  const dispatch = useDispatch();
  const games = useSelector((state) => state.game.games);
  const hasError = useSelector(selectError);
  const loading = useSelector(selectLoading);

  useEffect(() => {
    dispatch(fetchUserGames(user.login));
  }, [dispatch, user.login]);

  if (loading) {
    return <div>Загрузка...</div>;
  } else if (hasError) {
    return <div>Произошла ошибка!</div>;
  } else {
    return (
      <div className="space-y-3">
        <div>Сюжеты</div>
        {games.length === 0 ? (
          <div> Игры отсутствуют! </div>
        ) : (
          games.map((game, index) => (
            <Game
              gameId={game.gameId}
              key={index}
              name={game.name}
              gameSystem={game.gameSystemId}
              creationDate={game.creationDate}
              status={game.currentStatus}
              finishDate={game.finishDate}
              description={game.description}
              tags={[]}
              isMyProfile={isMyProfile}
            />
          ))
        )}
        {isMyProfile && (
          <button className="border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg  px-2 ">
            <Link to="/game-creation">Создать игру</Link>
          </button>
        )}
        <div className="space-y-3 mb-5">
          <div> Отзывы </div>
          <Reviews user={user} />
        </div>
      </div>
    );
  }
};
export default Master;
