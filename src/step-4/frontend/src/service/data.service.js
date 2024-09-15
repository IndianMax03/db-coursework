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

export const createCharacter = async (name, gameSystemId, img, userId, pdf) => {
  const formData = new FormData();
  formData.append('img', img);
  formData.append('name', name);
  formData.append('gameSystemId', gameSystemId);
  formData.append('userId', userId);
  formData.append('pdf', pdf);

  const response = await axios.post(API_BASE_URL + `characters/create`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
  console.log(response.data);
  return response.data;
};

export const createGame = async (name, gameSystemId, masterId, img, currentStatus, description) => {
  try {
    console.log(img);
    const formData = new FormData();
    formData.append('img', img);
    formData.append('name', name);
    formData.append('gameSystemId', gameSystemId);
    formData.append('masterId', masterId);
    formData.append('currentStatus', currentStatus);
    formData.append('description', description);

    const response = await axios.post(API_BASE_URL + `games/create`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
    console.log(response.data);
    return response.data;
  } catch (error) {
    throw error;
  }
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

export const sendReview = async (reviewerId, recipientId, rating, content) => {
  try {
    const response = await axios.post(API_BASE_URL + 'users/review/leave', {
      reviewerId,
      recipientId,
      rating,
      content
    });
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const changeGameStatus = async (id, currentStatus) => {
  try {
    const response = await axios.patch(API_BASE_URL + 'games/update/status', {
      id,
      currentStatus
    });
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getRoles = async (login) => {
  try {
    const response = await axios.get(API_BASE_URL + `users/roles/${login}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const addMasterRole = async (login) => {
  try {
    const response = await axios.get(API_BASE_URL + `users/roles/become-master/${login}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};
