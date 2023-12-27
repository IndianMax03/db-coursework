import axios from 'axios';

const API_BASE_URL = 'http://localhost:8081/';

export async function getUsers() {
  try {
    const response = await axios.get(API_BASE_URL + 'users/all');
    return response.data;
  } catch (error) {
    throw error;
  }
}

export const getUser = async (login) => {
  try {
    const response = await axios.get(API_BASE_URL + `users/${login}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getUserRoles = async (login) => {
  await axios.get(API_BASE_URL + `users/roles/${login}`);
};

export const getUserCharacters = async (login) => {
  try {
    const response = await axios.get(API_BASE_URL + `users/characters/${login}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getAllGames = async () => {
  try {
    const response = await axios.get(API_BASE_URL + `games/all`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getUserGames = async (login) => {
  try {
    const response = await axios.get(API_BASE_URL + `users/games/${login}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const createCharacter = async (name, gameSystemId, userId, picture, stats) => {
  // const formData = new FormData();

  // const blob = new Blob([picture], { type: 'image/jpeg' });
  // formData.append('picture', blob, 'character_image.jpg');

  // formData.append('name', name);
  // formData.append('gameSystemId', gameSystemId);
  // formData.append('userId', userId);
  // formData.append('stats', stats);

  // return await axios.post(
  //   API_BASE_URL + `characters/create`, formData,
  //   {
  //     headers: {
  //       'Content-Type' : 'multipart/form-data',
  //       token: '123'
  //     }
  //   }
  // );

  return await axios.post(API_BASE_URL + `characters/create`, {
    name,
    gameSystemId,
    userId,
    picture,
    stats
  });
};

export const createGame = async (
  name,
  gameSystemId,
  masterId,
  picture,
  currentStatus,
  finishDate,
  description
) => {
  await axios.post(API_BASE_URL + `games/create`, {
    name,
    gameSystemId,
    masterId,
    picture,
    currentStatus,
    finishDate,
    description
  });
};

export const getFriendshipRequests = async (login) => {
  try {
    const response = await axios.get(API_BASE_URL + `users/friends/${login}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getLobbyByCharacter = async (characterId) => {
  try {
    const response = await axios.get(API_BASE_URL + `characters/lobby/${characterId}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getLobbyByGame = async (gameId) => {
  try {
    const response = await axios.get(API_BASE_URL + `games/lobby/${gameId}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const changeKarma = async (senderUserId, receiverUserId, increase) => {
  try {
    const response = await axios.patch(API_BASE_URL + `users/update/karma`, {
      senderUserId,
      receiverUserId,
      increase
    });
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getReviews = async (login) => {
  try {
    const response = await axios.get(API_BASE_URL + `users/review/all/${login}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const changeRequestStatus = async (id, currentStatus) => {
  try {
    const response = await axios.patch(API_BASE_URL + 'characters/update/request', {
      id,
      currentStatus
    });
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const sendCharacterLobbyRequest = async (gameId, characterId) => {
  try {
    const response = await axios.post(API_BASE_URL + 'characters/request', {
      gameId,
      characterId
    });
    return response.data;
  } catch (error) {
    throw error;
  }
};
