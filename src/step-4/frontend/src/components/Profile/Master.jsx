import { useDispatch, useSelector } from 'react-redux';
import Game from '../Game';
import Review from './Review';
import { Link } from 'react-router-dom';
import {
  selectError,
  selectLoading,
  selectGames,
  fetchUserGames
} from '../../redux/slices/GameSlice';
import { useEffect } from 'react';

const Master = () => {
  const dispatch = useDispatch();
  const games = useSelector((state) => state.game.games);
  const hasError = useSelector(selectError);
  const loading = useSelector(selectLoading);

  useEffect(() => {
    const login = 'deaad';
    dispatch(fetchUserGames(login));
  }, [dispatch]);

  if (loading) {
    return <div>Загрузка...</div>;
  } else if (hasError) {
    return <div>Произошла ошибка!</div>;
  } else {
    return (
      <div>
        <div>Сюжеты</div>
        <div>
          {games.map((game, index) => (
            <Game
              key={index}
              name={game.name}
              gameSystem={game.gameSystemId}
              creationDate={game.creationDate}
              status={game.currentStatus}
              finishDate={game.finishDate}
              description={game.description}
              tags={[]}
            />
          ))}
        </div>
        <button className="border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg  px-2 ">
          <Link to="/game-creation">Создать игру</Link>
        </button>
        <div className="space-y-3 mb-5">
          <div> Отзывы </div>
          <Review
            name="Котярыч2"
            rating="5"
            content="Крутое подробный мастер. Мне понравилось. Молодец"
            date="01.12.2023"
          />
          <Review
            name="Котярыч2"
            rating="4"
            content="Хороший сюжет, мне понравилось твое размышление👍 Дополнительное видео очень подошло к твоему сюжету. Увы до 5 звезд не дотянул, но мастер хороший🤩"
            date="01.12.2023"
          />
        </div>
      </div>
    );
  }
};
export default Master;
