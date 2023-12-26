import { Link } from 'react-router-dom';

const Character = ({ name, gameSystemId, status, lobby }) => {
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

  return (
    <div className="flex border-solid border-2 border-slate-500 rounded-lg">
      <div className=" w-128 p-3">
        <div>Имя: {name}</div>
        <div>Игровая система: {gameSystem()}</div>
        <div>Характеристики: файл</div>
        <div>Статус: {statusValue()}</div>
        {lobby ? (
          <button className="mt-10 border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg  px-2 ">
            <Link to="/lobby">Перейти в лобби персонажа</Link>
          </button>
        ) : (
          ''
        )}
      </div>
      <img src="/astarion.jpg" alt="character" className="w-48 h-48 rounded object-cover p-2" />
    </div>
  );
};

export default Character;
