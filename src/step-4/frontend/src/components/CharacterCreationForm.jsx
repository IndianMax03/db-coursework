import { useState } from 'react';
import { createCharacter } from '../service/data.service';

const CharacterCreationForm = () => {
  const [name, setName] = useState('');
  const [gameSystem, setGameSystem] = useState('1');
  const [image, setImage] = useState(undefined);
  const [pdf, setPdf] = useState(undefined);
  const options = ['1', '2'];

  const handleCharacterCreation = () => {
    createCharacter(name, 1, 1, null, null);
  };

  return (
    <div className="w-128 flex justify-center">
      <div className=" space-y-3 ">
        <div className="flex justify-between space-x-5">
          <div>Имя</div>
          <input
            onChange={(e) => {
              setName(e.target.value);
            }}
            className="border-2 rounded-lg"
          ></input>
        </div>
        <div className="flex justify-between space-x-5">
          <label for="gameSystem">Игровая система</label>
          <select
            onChange={(e) => {
              setGameSystem(e.target.text);
            }}
            name="gameSystem"
            id="gameSystem"
          >
            {options.map((option) => (
              <option value={option}>{option}</option>
            ))}
          </select>
        </div>
        <div className="flex justify-between space-x-5">
          <div>Загрузить картинку</div>
          <input type="file" accept="image/png, image/jpeg"></input>
        </div>
        <div className="flex justify-between space-x-5">
          <div>Загрузить анкету в формате pdf</div>
          <input type="file" accept="image/png, image/jpeg"></input>
        </div>
        <div className="flex justify-center">
          <button
            onClick={handleCharacterCreation}
            className="border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg  px-2 "
          >
            Создать персонажа
          </button>
        </div>
      </div>
    </div>
  );
};

export default CharacterCreationForm;
