import { Link } from 'react-router-dom';
import Character from './Character';

const Player = () => {
  return (
    <div className="space-y-3 mb-10">
      <div>Персонажи</div>
      <Character name="name" gameSystem="DnD" status="в игре" lobby="1234" />
      <Character />
      <button className="border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg  px-2 ">
        <Link to="/character-creation">Создать персонажа</Link>
      </button>
    </div>
  );
};

export default Player;
