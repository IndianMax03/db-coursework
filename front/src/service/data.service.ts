import axios from 'axios';

const API_BASE_URL = 'http://localhost:8081/';

export async function getUsers() {
  try {
    const response = await axios.get(API_BASE_URL + 'users');
    return response.data;
  } catch (error) {
    throw error;
  }
}

export const getUser = async (login: string) => {
  try {
    if (!login) {
      return;
    }
    const response = await axios.get(API_BASE_URL + `users/${login}`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getUserRoles = async (login: string) => {
  if (!login) {
    return;
  }
  await axios.get(API_BASE_URL + `users/roles/${login}`);
};

export const getUserCharacters = async (login: string) => {
  try {
    if (!login) {
      return;
    }
    const response = await axios.get(API_BASE_URL + `users/${login}/characters`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getAllGames = async () => {
  try {
    const response = await axios.get(API_BASE_URL + `games`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getUserGames = async (login: string) => {
  try {
    if (!login) {
      return;
    }
    const response = await axios.get(API_BASE_URL + `users/${login}/games`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const createCharacter = async (
  name: string,
  gameSystemId: any,
  img: any,
  userId: any,
  pdf: any
) => {
  const formData = new FormData();
  formData.append('img', img);
  formData.append('name', name);
  formData.append('gameSystemId', gameSystemId);
  formData.append('userId', userId);
  formData.append('pdf', pdf);

  const response = await axios.post(API_BASE_URL + `characters`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
  console.log(response.data);
  return response.data;
};

export const createGame = async (
  name: string,
  gameSystemId: any,
  masterId: any,
  img: any,
  currentStatus: any,
  description: string
) => {
  try {
    console.log(img);
    const formData = new FormData();
    formData.append('img', img);
    formData.append('name', name);
    formData.append('gameSystemId', gameSystemId);
    formData.append('masterId', masterId);
    formData.append('currentStatus', currentStatus);
    formData.append('description', description);

    const response = await axios.post(API_BASE_URL + `games`, formData, {
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

export const getFriendshipRequests = async (login: string) => {
  try {
    if (!login) {
      return;
    }
    const response = await axios.get(API_BASE_URL + `users/${login}/friends`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getLobbyByCharacter = async (characterId: number) => {
  try {
    if (!characterId) {
      return;
    }
    const response = await axios.get(API_BASE_URL + `characters/${characterId}/lobby`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getLobbyByGame = async (gameId: number) => {
  try {
    if (!gameId) {
      return;
    }
    const response = await axios.get(API_BASE_URL + `games/${gameId}/lobby`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const changeKarma = async (
  senderUserId: number,
  receiverUserId: number,
  increase: boolean
) => {
  try {
    const response = await axios.patch(API_BASE_URL + `users/karma`, {
      senderUserId,
      receiverUserId,
      increase
    });
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getReviews = async (login: string) => {
  try {
    if (!login) {
      return;
    }
    const response = await axios.get(API_BASE_URL + `users/${login}/reviews`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const changeRequestStatus = async (id: number, currentStatus: any) => {
  try {
    const response = await axios.patch(API_BASE_URL + 'characters/request/update', {
      id,
      currentStatus
    });
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const sendCharacterLobbyRequest = async (gameId: number, characterId: number) => {
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

export const sendReview = async (
  reviewerId: number,
  recipientId: number,
  rating: number,
  content: string
) => {
  try {
    const response = await axios.post(API_BASE_URL + 'users/reviews', {
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

export const changeGameStatus = async (id: number, currentStatus: any) => {
  try {
    const response = await axios.patch(API_BASE_URL + 'games/status', {
      id,
      currentStatus
    });
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getRoles = async (login: string) => {
  try {
    if (!login) {
      return;
    }
    const response = await axios.get(API_BASE_URL + `users/${login}/roles`);
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const addMasterRole = async (login: string) => {
  try {
    if (!login) {
      return;
    }
    const response = await axios.get(API_BASE_URL + `users/${login}/roles/become-master`);
    return response.data;
  } catch (error) {
    throw error;
  }
};
