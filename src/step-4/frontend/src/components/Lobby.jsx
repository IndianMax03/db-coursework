import { Link } from 'react-router-dom';

const Lobby = ({ game, master, players }) => {
  return (
    <div className="space-y-5 p-5 border-solid border-2 border-slate-500 rounded-lg">
      <div className="text-lg">Игра {game.name}</div>
      <div>Мастер </div>
      <div className="flex space-x-10">
        <img src={master.image} className=" h-20 w-20 rounded-full object-cover " alt=""></img>{' '}
        <div>{master.name} </div>
      </div>
      <div>Игроки</div>
      {players.map((player) => (
        <div className="flex space-x-10">
          <img
            src={player.character.image}
            alt=""
            className=" h-20 w-20 rounded-full object-cover "
          ></img>
          <div className="">
            <div>{player.name} </div>
            <div>{player.character.name} </div>
          </div>
        </div>
      ))}
      {game.status === 'not-started' ? (
        <button className="mt-2 border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg  px-2 ">
          <Link to="/character-selection">Подать заявку на участие</Link>
        </button>
      ) : (
        ''
      )}
    </div>
  );
};

export default Lobby;
