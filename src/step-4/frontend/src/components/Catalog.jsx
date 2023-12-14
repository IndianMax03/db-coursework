import { useState } from 'react';
import Game from './Game';
const Catalog = () => {
  const [search, setSearch] = useState('games');
  return (
    <div className>
      <div className="flex space-x-10 mb-5">
        <div> Поиск </div>
        <input className="border-2 rounded-lg" placeholder="название"></input>
      </div>
      <div className=" flex space-x-5 justify-center mb-5">
        <button
          className={
            'games' === search
              ? 'border-solid border-2 text-white border-slate-500 bg-slate-500 rounded-lg w-full'
              : 'border-solid border-2 border-slate-500 rounded-lg w-full'
          }
        >
          Игры
        </button>
        <button
          className={
            'players' === search
              ? 'border-solid border-2 text-white border-slate-500 bg-slate-500 rounded-lg w-full'
              : 'border-solid border-2 border-slate-500 rounded-lg w-full'
          }
        >
          Игроки
        </button>
      </div>
      <div> Теги </div>

      <Game
        name="name"
        gameSystem="DnD"
        creationDate="01.12.2023"
        status="закончена"
        gameType="онлайн"
        finishDate="13.12.2023"
        description="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud"
        tags={['DnD', 'долгая']}
      />
      <Game
        name="name"
        gameSystem="DnD"
        creationDate="01.12.2023"
        status="закончена"
        gameType="онлайн"
        finishDate="13.12.2023"
        description="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud"
        tags={['DnD', 'долгая']}
      />
    </div>
  );
};

export default Catalog;
