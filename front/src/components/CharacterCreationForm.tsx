import { useState } from 'react';
import { createCharacter } from '../service/data.service';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { getGameSystem } from '../util/enumHandler';
import { selectSelf } from '../redux/slices/UserSlice';

const CharacterCreationForm = () => {
  const [name, setName] = useState('');
  const [gameSystem, setGameSystem] = useState('1');
  const [pdf, setPdf] = useState(undefined);
  const options = ['1', '2'];
  const [selectedImage, setSelectedImage] = useState(null);
  const navigate = useNavigate();
  const self = useSelector(selectSelf);

  const handleCharacterCreation = () => {
    createCharacter(name, parseInt(gameSystem), selectedImage, self.id, pdf);
    navigate(`/profile/${self.login}`);
  };

  const handleImageChange = (event: any) => {
    const file = event.target.files[0];
    setSelectedImage(file);
  };

  const handlePdfChange = (event: any) => {
    const file = event.target.files[0];
    setPdf(file);
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
              setGameSystem(e.target.value);
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
          <div>Загрузить картинку персонажа</div>
          <input type="file" onChange={handleImageChange} />
        </div>
        <div className="flex justify-between space-x-5">
          <div>Загрузить анкету в формате pdf</div>
          <input type="file" onChange={handlePdfChange} />
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
