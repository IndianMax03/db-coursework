import { Link } from 'react-router-dom';

const Game = ({
  name,
  gameSystem,
  image,
  creationDate,
  status,
  gameType,
  finishDate,
  description,
  tags,
  isMyProfile
}) => {
  return (
    <div className="flex border-solid border-2 border-slate-500 rounded-lg">
      <div className=" w-128 p-3">
        <div className="text-lg">{name}</div>
        <div>Игровая система: {gameSystem}</div>
        <div>Тип игры: {gameType}</div>
        <div>Статус: {status}</div>
        <div>Дата создания: {creationDate} </div>
        {finishDate && <div>Дата завершения: {finishDate} </div>}
        <div className="w-72">Описание: {description} </div>
        <div className="flex">
          {tags.map((i) => (
            <div key={i} className="text-slate-500 mr-3">
              #{i}
            </div>
          ))}
        </div>
        <button className="mt-2 border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg  px-2 ">
          <Link to="/lobby">Перейти в лобби игры</Link>
        </button>
        {status === 'not-started' && !isMyProfile && (
          <div>
            <div className="text-slate-500">Вы можете подать заявку на участие!</div>
          </div>
        )}
      </div>
      <img src="/gameImage.jpg" alt="character" className="w-48 h-48 rounded object-cover p-2" />
      <div></div>
    </div>
  );
};

export default Game;
