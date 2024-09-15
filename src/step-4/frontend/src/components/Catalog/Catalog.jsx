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

  const handleCatalogChange = (type) => {
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
    <div className>
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
      {/* <div> Теги </div>
      <div>
        <input id="default-checkbox" type="checkbox" value="" />
        <label for="default-checkbox" className="ms-2 ">
          DnD
        </label>
      </div> */}
      {/* <div>
        <input id="default-checkbox" type="checkbox" value="" />
        <label for="default-checkbox" className="ms-2 ">
          Долгая
        </label>
      </div> */}
      {search === 'games' ? (
        games.map((game, index) => (
          <div className="mb-3">
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
          </div>
        ))
      ) : (
        <List array={users} position="horizontal" maxInRow={4} />
      )}
    </div>
  );
};

export default Catalog;
