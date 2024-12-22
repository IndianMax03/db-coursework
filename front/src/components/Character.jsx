import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import {
  fetchLobbyByCharacter,
  selectLobby,
  selectLobbyError,
  selectLobbyLoading
} from '../redux/slices/LobbySlice';
import { useEffect } from 'react';
import { getGameSystem, getCharacterStatusValue } from '../util/enumHandler';

const Character = ({ name, gameSystemId, status, characterId, button, picture, stats }) => {
  const dispatch = useDispatch();
  const lobby = useSelector(selectLobby);

  const loading = useSelector(selectLobbyLoading);
  const hasError = useSelector(selectLobbyError);

  useEffect(() => {
    setTimeout(() => {}, 1000);
    if (status !== 'free') {
      dispatch(fetchLobbyByCharacter(characterId));
    }
  }, [characterId, dispatch, status]);

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
        <div>Игровая система: {getGameSystem(gameSystemId)}</div>
        <div>Характеристики: файл</div>
        <div>Статус: {getCharacterStatusValue(status)}</div>
        {lobby && lobby.game && status === 'busy' && (
          <button className="mt-10 border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg  px-2 ">
            <Link to={`/lobby/${lobby.game.id}`}>Перейти в лобби персонажа</Link>
          </button>
        )}
        {button}
      </div>

      {picture !== null ? (
        <img
          alt="character"
          src={`data:image/png;base64,${picture}`}
          className="w-48 h-48 rounded object-cover p-2"
        ></img>
      ) : (
        <img alt="character" src="/gale.jpg" className="w-48 h-48 rounded object-cover p-2"></img>
      )}
    </div>
  );
};

export default Character;
