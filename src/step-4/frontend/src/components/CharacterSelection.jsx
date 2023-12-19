import Character from './Character';

const CharacterSelection = () => {
  const characters = [<Character />, <Character />];
  return (
    <div className="">
      <div className="text-lg mb-3">Выберите персонажа для подачи заявки</div>
      {characters.map((character) => (
        <div>
          <div> {character} </div>
          <button className=" mb-10 mt-3 border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg  px-2 ">
            Выбрать
          </button>
        </div>
      ))}
    </div>
  );
};

export default CharacterSelection;
