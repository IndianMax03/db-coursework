import { useState } from 'react';
import { createGame } from '../service/data.service';
import { getGameSystem } from '../util/enumHandler';
import { useSelector } from 'react-redux';
import { selectSelf } from '../redux/slices/UserSlice';
import { useNavigate } from 'react-router-dom';

const GameCreationForm = () => {
  const gameTypes = ['online', 'offline'];
  const status = 'not-started';
  const options = ['1', '2'];
  const [name, setName] = useState('');
  const [gameSystem, setGameSystem] = useState('1');
  // const [gameType, setGameType] = useState('');
  const [img, setImage] = useState(null);
  const [description, setDescription] = useState('');
  const self = useSelector(selectSelf);
  const navigate = useNavigate();

  const handleGameCreation = () => {
    console.log(img);
    createGame(name, parseInt(gameSystem), self.id, img, status, description).then(
      navigate(`/profile/${self.login}`)
    );
  };

  const handleImageChange = (event) => {
    const file = event.target.files[0];
    setImage(file);
  };

  return (
    <div className="w-128 flex justify-center">
      <div className=" space-y-3 ">
        <div className="flex justify-between space-x-5">
          <div>Название</div>
          <input
            className="border-2 rounded-lg"
            onChange={(e) => {
              setName(e.target.value);
            }}
          ></input>
        </div>
        <div className="flex justify-between space-x-5">
          <label for="gameSystem">Игровая система</label>
          <select name="gameSystem" id="gameSystem">
            {options.map((option) => (
              <option value={option}>{getGameSystem(option)}</option>
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
          <textarea
            className="flex border-solid border-2 border-slate-500 rounded-lg w-full"
            value={description}
            onChange={(e) => {
              setDescription(e.target.value);
            }}
          ></textarea>
        </div>

        <div className="flex justify-between space-x-5">
          <div>Загрузить картинку</div>
          <input type="file" onChange={handleImageChange} />
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
