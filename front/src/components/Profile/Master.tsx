import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Game from '../Game';
import { Link, useNavigate } from 'react-router-dom';
import { fetchUserGames, selectError, selectLoading } from '../../redux/slices/GameSlice';
import Reviews from '../Reviews/Reviews';
import { addMasterRole } from '../../service/data.service';
import { fetchRoles, selectHasMasterRole } from '../../redux/slices/RolesSlice';
import { selectSelf } from '../../redux/slices/UserSlice';
import { RootState, AppDispatch } from '../../redux/store';

interface MasterProps {
  user: {
    id: number;
    login: string;
  };
  isMyProfile: boolean;
}

const Master: React.FC<MasterProps> = ({ user, isMyProfile }) => {
  const dispatch = useDispatch<AppDispatch>();
  const games = useSelector((state: RootState) => state.game.games);
  const hasError = useSelector(selectError);
  const loading = useSelector(selectLoading);
  const hasMasterRole = useSelector(selectHasMasterRole);
  const navigate = useNavigate();
  const self = useSelector(selectSelf);

  useEffect(() => {
    dispatch(fetchUserGames(user.login));
    dispatch(fetchRoles(user.login));
  }, [dispatch, user.login, hasMasterRole]);

  const handleAddMasterRole = () => {
    addMasterRole(user.login);
    navigate(`/profile/${self.login}`);
  };

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
          games.map((game: any) => (
            <Game
              gameId={game.id}
              key={game.id}
              name={game.name}
              gameSystem={game.gameSystemId}
              creationDate={game.creationDate}
              status={game.currentStatus}
              finishDate={game.finishDate}
              description={game.description}
              tags={[]} // Assuming tags are handled elsewhere
              picture={game.picture}
              isMyProfile={isMyProfile}
            />
          ))
        )}
        {!hasMasterRole && isMyProfile && (
          <div>
            <div> У вас нет роли мастера! </div>
            <button
              onClick={handleAddMasterRole}
              className="border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg px-2"
            >
              Стать мастером
            </button>
          </div>
        )}
        {isMyProfile && hasMasterRole && (
          <button className="border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg px-2">
            <Link to="/game-creation">Создать игру</Link>
          </button>
        )}
        <div className="space-y-3 mb-5">
          <div> Отзывы </div>
          <Reviews user={user} isMyProfile={isMyProfile} />
        </div>
      </div>
    );
  }
};

export default Master;
