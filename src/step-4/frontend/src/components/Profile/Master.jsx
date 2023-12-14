import Game from '../Game';
import Review from './Review';
import { Link } from 'react-router-dom';

const Master = () => {
  return (
    <div>
      <div className="space-y-3 mb-5">
        <div>Сюжеты</div>
        <Game
          name="Порядулечная игра"
          gameSystem="DnD"
          creationDate="01.12.2023"
          status="finished"
          gameType="онлайн"
          finishDate="13.12.2023"
          description="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud"
          tags={['DnD', 'долгая']}
        />
        <Game
          name="Порядошная игра"
          gameSystem="DnD"
          creationDate="01.12.2023"
          status="started"
          gameType="онлайн"
          description="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud"
          tags={['DnD', 'долгая']}
        />
        <Game
          name="Порядошная игра"
          gameSystem="DnD"
          creationDate="01.12.2023"
          status="not-started"
          gameType="онлайн"
          description="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud"
          tags={['DnD', 'долгая']}
        />
        <button className="border-solid border-2 bg-slate-500 text-white border-slate-500 rounded-lg  px-2 ">
          <Link to="/game-creation">Создать игру</Link>
        </button>
      </div>
      <div className="space-y-3 mb-5">
        <div> Отзывы </div>
        <Review
          name="Котярыч2"
          rating="5"
          content="Крутое подробный мастер. Мне понравилось. Молодец"
          date="01.12.2023"
        />
        <Review
          name="Котярыч2"
          rating="4"
          content="Хороший сюжет, мне понравилось твое размышление👍 Дополнительное видео очень подошло к твоему сюжету. Увы до 5 звезд не дотянул, но мастер хороший🤩"
          date="01.12.2023"
        />
      </div>
    </div>
  );
};

export default Master;
