import Game from './Game';
import Review from './Review';

const Master = () => {
  return (
    <div>
      <div className="space-y-3 mb-5">
        <div>Сюжеты</div>
        <Game
          name="name"
          gameSystem="DnD"
          creationDate="01.12.2023"
          status="закончена"
          gameType="онлайн"
          finishDate="13.12.2023"
          description="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud"
        />
        <Game
          name="name"
          gameSystem="DnD"
          creationDate="01.12.2023"
          status="закончена"
          gameType="онлайн"
          finishDate="13.12.2023"
          description="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud"
        />
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
