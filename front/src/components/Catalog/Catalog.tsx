import { useEffect, useState } from 'react';
import Game from '../Game';
import { useDispatch, useSelector } from 'react-redux';
import { fetchGames, selectGames } from '../../redux/slices/GamesSlice';
import { fetchUsers, selectUsers } from '../../redux/slices/UsersSlice';
import List from '../List';

const Catalog = () => {
  const [search, setSearch] = useState('games');
  const dispatch = useDispatch();
  const games = useSelector(selectGames);
  const users = useSelector(selectUsers);

  const handleCatalogChange = (type: any) => {
    if (search === type) {
      return;
    }
    setSearch(type);
  };

  useEffect(() => {
    dispatch(fetchGames());
    dispatch(fetchUsers());
  }, [dispatch]);

  return (
    <div>
      <div className="flex space-x-10 mb-5">
        <div> Поиск </div>
        <input className="border-2 rounded-lg" placeholder="Введите название"></input>
      </div>
      <div className=" flex space-x-5 justify-center mb-5">
        <button
          onClick={() => {
            handleCatalogChange('games');
          }}
          className={
            'games' === search
              ? 'border-solid border-2 text-white border-slate-500 bg-slate-500 rounded-lg w-full'
              : 'border-solid border-2 border-slate-500 rounded-lg w-full'
          }
        >
          Игры
        </button>
        <button
          onClick={() => {
            handleCatalogChange('players');
          }}
          className={
            'players' === search
              ? 'border-solid border-2 text-white border-slate-500 bg-slate-500 rounded-lg w-full'
              : 'border-solid border-2 border-slate-500 rounded-lg w-full'
          }
        >
          Игроки
        </button>
      </div>

      {search === 'games' ? (
        games.map((game, index: number) => (
          <div className="mb-3" key={index}>
            <Game
              name={game.name}
              gameId={game.id}
              gameSystem={game.gameSystemId}
              creationDate={game.creationDate}
              status={game.currentStatus}
              finishDate={game.finishDate}
              picture={game.picture}
              description={game.description}
              tags={[]}
            />
          </div>
        ))
      ) : (
        <List array={users} position="horizontal" maxInRow={4} />
      )}
    </div>
  );
};

export default Catalog;
