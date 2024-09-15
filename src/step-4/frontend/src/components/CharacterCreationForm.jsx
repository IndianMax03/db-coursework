import { useState } from 'react';
import { createCharacter } from '../service/data.service';
import { useDispatch } from 'react-redux';
import { dataUrlToByteArray } from '../util/image.converter';
import { byteArrayToImage } from '../util/image.converter';
import { useNavigate } from 'react-router-dom';
import { getGameSystem } from '../util/enumHandler';

const CharacterCreationForm = () => {
  const [name, setName] = useState('');
  const [gameSystem, setGameSystem] = useState('1');
  const [pdf, setPdf] = useState(undefined);
  const options = ['1', '2'];
  const [imageByteArray, setImageByteArray] = useState([]);
  const navigate = useNavigate();

  const handleCharacterCreation = () => {
    const user = localStorage.getItem('myProfile');
    if (true) {
      createCharacter(name, 1, 1, imageByteArray, null);
      navigate(`/profile/${user.login}`);
    }
  };

  const handleImageInput = (event) => {
    const file = event.target.files[0];

    if (file) {
      const reader = new FileReader();

      reader.onloadend = () => {
        const dataUrl = reader.result;
        setImageByteArray(dataUrlToByteArray(dataUrl));
      };
      reader.readAsDataURL(file);
    }
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
              <option value={option}>{getGameSystem(option)}</option>
            ))}
          </select>
        </div>
        <div className="flex justify-between space-x-5">
          <div>Загрузить картинку</div>
          <input type="file" accept="image/png, image/jpeg" onChange={handleImageInput}></input>
        </div>
        <div className="flex justify-between space-x-5">
          <div>Загрузить анкету в формате pdf</div>
          <input type="file" accept="image/png, image/jpeg"></input>
        </div>
        <div className="flex justify-center">
          <button
            onClick={handleCharacterCreation}
            className="border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg  px-2"
          >
            Создать персонажа
          </button>
        </div>
      </div>
    </div>
  );
};

export default CharacterCreationForm;
