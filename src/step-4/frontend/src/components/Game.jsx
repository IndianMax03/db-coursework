const Game = ({
  name,
  gameSystem,
  image,
  creationDate,
  status,
  gameType,
  finishDate,
  description,
  tags
}) => {
  return (
    <div className="flex border-solid border-2 border-slate-500 rounded-lg">
      <div className=" w-128 p-3">
        <div>Название: {name}</div>
        <div>Игровая система: {gameSystem}</div>
        <div>Тип игры: {gameType}</div>
        <div>Статус: {status}</div>
        <div>Дата создания: {creationDate} </div>
        <div>Дата завершения: {finishDate} </div>
        <div className="w-72">Описание: {description} </div>
        <div className="flex">
          {tags.map((i) => (
            <div key={i} className="text-slate-500 mr-3">
              #{i}
            </div>
          ))}
        </div>
      </div>
      <img src="gameImage.jpg" alt="character" className="w-48 h-48 rounded object-cover p-2" />
      <div></div>
    </div>
  );
};

export default Game;
