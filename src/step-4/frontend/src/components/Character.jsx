const Character = ({ name = 'Astarion', gameSystem = 'DnD', status = 'свободен', lobby }) => {
  return (
    <div className="flex border-solid border-2 border-slate-500 rounded-lg">
      <div className=" w-128 p-3">
        <div>Имя: {name}</div>
        <div>Игровая система: {gameSystem}</div>
        <div>Характеристики: файл</div>
        <div>Статус: {status}</div>

        {lobby ? <div> Перейти в лобби</div> : ''}
      </div>
      <img src="astarion.jpg" alt="character" className="w-48 h-48 rounded object-cover p-2" />
    </div>
  );
};

export default Character;
