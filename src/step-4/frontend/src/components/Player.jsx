import Character from './Character';

const Player = () => {
  return (
    <div className="space-y-3">
      <div>Персонажи</div>
      <Character name="name" gameSystem="DnD" status="в игре" lobby="1234" />
      <Character />
    </div>
  );
};

export default Player;
