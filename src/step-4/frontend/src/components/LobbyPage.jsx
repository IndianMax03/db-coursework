import Lobby from './Lobby';

const LobbyPage = () => {
  return (
    <div>
      <Lobby
        game={{ name: 'Порядулечная', status: 'not-started' }}
        master={{ name: 'МастерКотярыч', image: 'pfp.jpg' }}
        players={[
          { name: 'Котярыч1', character: { name: 'Astarion', image: 'astarion.jpg' } },
          { name: 'Котярыч2', character: { name: 'Gale', image: 'gale.jpg' } }
        ]}
      />
    </div>
  );
};

export default LobbyPage;
