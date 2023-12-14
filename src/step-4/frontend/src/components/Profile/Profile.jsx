import { useState } from 'react';
import Player from './Player';
import Master from './Master';
import List from '../List';

const Profile = () => {
  const [role, setRole] = useState('player');

  const handleRoleChange = (newRole) => {
    if (newRole !== role) {
      setRole(newRole);
    }
  };

  return (
    <div className="flex items-center justify-center">
      <div>
        <div className="flex space-x-20 mb-10">
          <img src="pfp.jpg" alt="profile" className=" h-44 rounded-full"></img>
          <div className="w-44">
            <div className="flex text-2xl mb-5 space-x-5 justify-between">
              <div>Котярыч</div> <div className=" text-red-600 font-medium">+1234</div>
            </div>
            <div>Часовой пояс: Москва</div>
            <div>Вконтакте: kitten </div>
            <div>Телеграм: @kitten</div>
          </div>
        </div>
        <div className="mb-5">Друзья</div>
        <div className="flex  mb-10">
          <List
            array={[
              { name: 'friend1' },
              { name: 'friend2' },
              { name: 'friend2' },
              { name: 'friend2' },
              { name: 'friend2' },
              { name: 'friend2' }
            ]}
            rowCount={2}
            position="horizontal"
          />
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
        {role === 'player' ? <Player /> : <Master />}
      </div>
    </div>
  );
};

export default Profile;
