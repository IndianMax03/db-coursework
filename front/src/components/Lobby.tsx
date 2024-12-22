import { useDispatch, useSelector } from 'react-redux';
import { Link, useParams } from 'react-router-dom';
import { fetchLobbyByGame, selectLobby } from '../redux/slices/LobbySlice';
import { useEffect, useState } from 'react';
import { selectSelf } from '../redux/slices/UserSlice';
import { changeGameStatus, changeRequestStatus } from '../service/data.service';
import { getGameStatusValue } from '../util/enumHandler';

const Lobby = () => {
  const { lobbyId } = useParams();
  const self = useSelector(selectSelf);
  const dispatch = useDispatch();
  const lobby = useSelector(selectLobby);
  const options = ['started', 'not-started', 'finished'];
  const [gameStatus, setGameStatus] = useState(lobby?.game?.currentStatus);
  const isMyLobby = self.login === lobby?.master?.login;
  const approvedRequests = lobby?.requests?.filter((request) => {
    return request.requestStatus === 'approved';
  });
  const onReviewRequests = lobby?.requests?.filter((request) => {
    return request.requestStatus === 'on-review';
  });
  const [update, setUpdate] = useState(true);

  useEffect(() => {
    console.log(lobbyId);
    dispatch(fetchLobbyByGame(lobbyId));
  }, [dispatch, lobbyId, update]);

  const handleRequestStatusChange = (requestId, status) => {
    changeRequestStatus(requestId, status);
    setUpdate(!update);
  };

  const handleGameStatusChange = () => {
    changeGameStatus(lobby.game.id, gameStatus);
  };

  if (!lobby || !lobby.master || !lobby.game) {
    return <div>Loading</div>;
  }

  return (
    <div className="space-y-5 p-5 border-solid border-2 border-slate-500 rounded-lg">
      <div className="text-lg">Игра "{lobby?.game?.name}"</div>
      <div className="space-x-10">
        <label for="gameStatus">Cтатус игры:</label>
        <select
          name="gameStatus"
          id="gameStatus"
          value={gameStatus}
          onChange={(e) => {
            setGameStatus(e.target.value);
            console.log(gameStatus);
          }}
        >
          {options.map((option) => (
            <option value={option} key={option}>
              {getGameStatusValue(option)}
            </option>
          ))}
        </select>
        <button
          className="mt-2 border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg  px-2 "
          onClick={handleGameStatusChange}
        >
          Изменить статус
        </button>
      </div>

      <div>Мастер </div>
      <div className="flex space-x-10">
        {lobby?.master?.picture && (
          <img
            src={`data:image/png;base64,${lobby?.master?.picture}`}
            alt="master"
            className=" h-20 w-20 rounded-full object-cover"
          />
        )}
        {/* <img
          src={lobby.master.image}
          className=" h-20 w-20 rounded-full object-cover "
          alt=""
        ></img> */}
        <div>{lobby.master.name} </div>
      </div>
      <div>Игроки</div>
      {approvedRequests.length === 0 ? (
        <div>Пока что нет принятых персонажей</div>
      ) : (
        approvedRequests.map((character, i) => (
          <div className="flex space-x-10" key={i}>
            {character.picture && (
              <img
                src={`data:image/png;base64,${character.picture}`}
                alt="character"
                className=" h-20 w-20 rounded-full object-cover"
              />
            )}
            {/* <img
              src={character.picture}
              alt=""
              className=" h-20 w-20 rounded-full object-cover "
            ></img> */}
            <div className="">
              <div>{character.name} </div>
            </div>
          </div>
        ))
      )}
      <div>Текущие заявки</div>
      {onReviewRequests.length === 0 ? (
        <div>Пока что нет заявок на участие</div>
      ) : (
        onReviewRequests.map((character, i) => (
          <div className="flex space-x-10" key={i}>
            {character.picture && (
              <img
                src={`data:image/png;base64,${character.picture}`}
                alt="character"
                className=" h-20 w-20 rounded-full object-cover"
              />
            )}
            {/* <img
              src={character.picture}
              alt=""
              className=" h-20 w-20 rounded-full object-cover "
            ></img> */}
            <div className="">
              <div>{character.name} </div>
              <div className="flex space-x-5">
                {isMyLobby && (
                  <button
                    className="mt-2 border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg  px-2 "
                    onClick={() => handleRequestStatusChange(character.lobbyRequestId, 'approved')}
                  >
                    Принять
                  </button>
                )}
                {isMyLobby && (
                  <button
                    className="mt-2 border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg  px-2 "
                    onClick={() => handleRequestStatusChange(character.lobbyRequestId, 'rejected')}
                  >
                    Отклонить
                  </button>
                )}
              </div>
            </div>
          </div>
        ))
      )}
      {lobby.game.currentStatus === 'not-started' && !isMyLobby && (
        <button className="mt-2 border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg  px-2 ">
          <Link to={`/character-selection/${lobby.game.id}`}>Подать заявку на участие</Link>
        </button>
      )}
    </div>
  );
};

export default Lobby;
