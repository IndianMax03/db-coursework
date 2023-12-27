import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import {
  fetchLobbyByCharacter,
  selectLobby,
  selectLobbyError,
  selectLobbyLoading
} from '../redux/slices/LobbySlice';
import { useEffect } from 'react';

const Character = ({ name, gameSystemId, status, characterId }) => {
  const dispatch = useDispatch();
  const lobby = useSelector(selectLobby);
  const loading = useSelector(selectLobbyLoading);
  const hasError = useSelector(selectLobbyError);

  useEffect(() => {
    // TODO: fix lobby render only after characted rendered;
    setTimeout(() => {}, 1000);
    dispatch(fetchLobbyByCharacter(characterId));
  }, [characterId, dispatch]);

  const statusValue = () => {
    switch (status) {
      case 'busy':
        return 'занят';
      case 'free':
        return 'свободен';
      default:
        return 'не определен';
    }
  };

  const gameSystem = () => {
    switch (gameSystemId) {
      case 1:
        return 'Dungeons&Dragons';
      case 2:
        return 'Pathfinder';
      default:
        return 'не определена';
    }
  };
  if (loading) {
    return <div>Загрузка... </div>;
  }

  if (hasError) {
    return <div>Ошибка!</div>;
  }
  return (
    <div className="flex border-solid border-2 border-slate-500 rounded-lg">
      <div className=" w-128 p-3">
        <div>Имя: {name}</div>
        <div>Игровая система: {gameSystem()}</div>
        <div>Характеристики: файл</div>
        <div>Статус: {statusValue()}</div>
        {lobby && (
          <button className="mt-10 border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg  px-2 ">
            <Link to={`/lobby/${lobby.game.id}`}>Перейти в лобби персонажа</Link>
          </button>
        )}
      </div>
      <img src="/astarion.jpg" alt="character" className="w-48 h-48 rounded object-cover p-2" />
    </div>
  );
};

export default Character;
