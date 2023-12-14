import { Link } from 'react-router-dom';
import Character from './Character';

const CharacterSelection = ({ characters }) => {
  return (
    <div className="flex border-solid border-2 border-slate-500 rounded-lg">
      {characters.map((character) => character)}
    </div>
  );
};

export default CharacterSelection;
