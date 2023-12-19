import { useState } from 'react';
import { createGame } from '../service/data.service';

const GameCreationForm = () => {
  const gameTypes = ['online', 'offline'];
  const status = 'not-started';
  const options = ['1', '2'];
  const [name, setName] = useState('');
  const [gameSystem, setGameSystem] = useState('1');
  const [gameType, setGameType] = useState('');
  const [image, setImage] = useState(undefined);
  const date = Date.now();
  const [description, setDescription] = useState('');

  const handleGameCreation = () => {
    createGame(name, gameSystem, 1, null, null, null, description);
  };
  return (
    <div className="w-128 flex justify-center">
      <div className=" space-y-3 ">
        <div className="flex justify-between space-x-5">
          <div>Название</div>
          <input className="border-2 rounded-lg"></input>
        </div>
        <div className="flex justify-between space-x-5">
          <label for="gameSystem">Игровая система</label>
          <select name="gameSystem" id="gameSystem">
            {options.map((option) => (
              <option value={option}>{option}</option>
            ))}
          </select>
        </div>
        <div className="flex justify-between space-x-5">
          <label for="gameSystem">Формат игры</label>
          <select name="gameSystem" id="gameSystem">
            {gameTypes.map((type) => (
              <option value={type}>{type}</option>
            ))}
          </select>
        </div>
        <div className="flex justify-between space-x-5">
          <div>Описание</div>
          <input className="border-2 rounded-lg" type="text"></input>
        </div>

        <div className="flex justify-between space-x-5">
          <div>Загрузить картинку</div>
          <input type="file" accept="image/png, image/jpeg"></input>
        </div>
        <div className="flex justify-center">
          <button
            onClick={handleGameCreation}
            className="border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg  px-2 "
          >
            Создать игру
          </button>
        </div>
      </div>
    </div>
  );
};

export default GameCreationForm;
