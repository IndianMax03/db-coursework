import { useState } from 'react';
import Player from './Player';
import Master from './Master';

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
        <div className="flex space-x-20">
          <img src="pfp.jpg" alt="profile" className=" h-44 rounded-full"></img>
          <div>
            <div>карма значок</div>
            <div>Личная информация</div>
            <div>
              <div>Котярыч</div>
              <div>Часовой пояс</div>
            </div>
            <div>Ссылки</div>
            <div>
              <div>Вконтакте </div>
              <div>Телеграм</div>
            </div>
          </div>
        </div>
        <div>Друзья</div>
        <div className="flex space-x-4">
          <img src="pfp.jpg" alt="profile" className=" h-14 rounded-full"></img>
          <img src="pfp.jpg" alt="profile" className=" h-14 rounded-full"></img>
          <img src="pfp.jpg" alt="profile" className=" h-14 rounded-full"></img>
          <img src="pfp.jpg" alt="profile" className=" h-14 rounded-full"></img>
        </div>
        <div className=" flex justify-between bg-slate-500">
          <button onClick={() => handleRoleChange('player')}>Игрок</button>
          <button onClick={() => handleRoleChange('master')}>Мастер</button>
        </div>
        {role === 'player' ? <Player /> : <Master />}
      </div>
    </div>
  );
};

export default Profile;
