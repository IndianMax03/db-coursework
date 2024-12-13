import { useEffect, useState } from 'react';
import Player from './Player';
import Master from './Master';
import { useDispatch, useSelector } from 'react-redux';
import {
  fetchUser,
  selectLoading,
  selectUser,
  selectError,
  selectSelf
} from '../../redux/slices/UserSlice';
import { useParams } from 'react-router-dom';
import Karma from './Karma';
import Friends from '../Friends/Friends';
import pfp from '../../../public/pfp.jpg';

const Profile = () => {
  const { login } = useParams();
  const dispatch = useDispatch();
  const user = useSelector(selectUser);
  const self = useSelector(selectSelf);
  const isMyProfile = self.login === user.login;
  const [role, setRole] = useState('player');

  const loading = useSelector(selectLoading);
  const hasError = useSelector(selectError);

  useEffect(() => {
    dispatch(fetchUser(login));
  }, [dispatch, isMyProfile, login]);

  const handleRoleChange = (newRole) => {
    if (newRole !== role) {
      setRole(newRole);
    }
  };

  if (loading) {
    return <div>Загрузка... </div>;
  }

  if (hasError) {
    return <div>Ошибка!</div>;
  }

  return (
    <div className="w-128flex items-center justify-center">
      <div>
        <div className="flex space-x-20 mb-10">
          {user.picture !== null ? (
            <img
              src={`data:image/png;base64,${user.picture}`}
              alt="profile"
              className=" h-44 w-44 rounded-full"
            ></img>
          ) : (
            <img src={pfp} alt="profile" className=" h-44 w-44 rounded-full"></img>
          )}
          <div className="w-44">
            <div className="flex text-2xl mb-3 space-x-3 justify-left">
              <div>{user.name}</div>
              <Karma receiver={user} sender={self} isMyProfile={isMyProfile} />
            </div>
            <div className=" text-red-600 font-medium">{user.login}</div>
            <div>Часовой пояс: {user.timezone}</div>
            <div>Вконтакте: {user.vkTag} </div>
            <div>Телеграм: @{user.telegramTag}</div>
          </div>
        </div>
        <div className="mb-5">Друзья</div>
        <div className="flex  mb-10">
          <Friends user={user} />
        </div>
        <div className=" flex space-x-5 justify-center mb-5">
          <button
            className={
              'player' === role
                ? 'border-solid border-2 text-white border-slate-500 bg-slate-500 rounded-lg w-full'
                : 'border-solid border-2 border-slate-500 rounded-lg w-full'
            }
            onClick={() => handleRoleChange('player')}
          >
            Игрок
          </button>
          <button
            className={
              'master' === role
                ? 'border-solid border-2 text-white border-slate-500 bg-slate-500 rounded-lg w-full'
                : 'border-solid border-2 border-slate-500 rounded-lg w-full'
            }
            onClick={() => handleRoleChange('master')}
          >
            Мастер
          </button>
        </div>
        {role === 'player' ? (
          <Player user={user} isMyProfile={isMyProfile} />
        ) : (
          <Master user={user} isMyProfile={isMyProfile} />
        )}
      </div>
    </div>
  );
};

export default Profile;
