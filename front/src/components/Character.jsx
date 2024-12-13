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
    // TODO: fix lobby render only after characted rendered;
    console.log(picture);
    console.log(stats);
    setTimeout(() => {}, 1000);
    dispatch(fetchLobbyByCharacter(characterId));
  }, [characterId, dispatch]);

  // if (loading) {
  //   return <div>Загрузка... </div>;
  // }

  // if (hasError) {
  //   return <div>Ошибка!</div>;
  // }

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
                <img
                alt="character"
                src="/gale.jpg"
                className="w-48 h-48 rounded object-cover p-2"
              ></img>
              )}
      {/* <img src={picture} alt="character" className="w-48 h-48 rounded object-cover p-2" /> */}
    </div>
  );
};

export default Character;
