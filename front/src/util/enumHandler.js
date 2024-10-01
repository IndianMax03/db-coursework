export const getCharacterStatusValue = (status) => {
  switch (status) {
    case 'busy':
      return 'занят';
    case 'free':
      return 'свободен';
    default:
      return 'не определен';
  }
};

export const getGameSystem = (gameSystemId) => {
  gameSystemId = parseInt(gameSystemId);
  switch (gameSystemId) {
    case 1:
      return 'Dungeons&Dragons';
    case 2:
      return 'Pathfinder';
    default:
      return 'не определена';
  }
};
export const getGameStatusValue = (status) => {
  switch (status) {
    case 'not-started':
      return 'не началась';
    case 'started':
      return 'началась';
    case 'finished':
      return 'закончилась';
    default:
      return 'не определен';
  }
};
