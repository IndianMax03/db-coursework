import axios from 'axios';

const API_BASE_URL = 'http://localhost:8081/';

export async function getUsers() {
  return await axios.get(API_BASE_URL + 'users/all', {
    headers: {
      token: '123'
    }
  });
}

export const getUser = async (login) => {
  return await axios.get(API_BASE_URL + `users/${login}`, {
    headers: {
      token: '123'
    }
  });
};

export const getUserRoles = async (login) => {
  await axios.get(API_BASE_URL + `users/roles/${login}`, {
    headers: {
      token: '123'
    }
  });
};

export const getUserCharacters = async (login) => {
  try {
    const response = await axios.get(API_BASE_URL + `users/characters/${login}`, {
      headers: {
        token: '123'
      }
    });
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getAllGames = async () => {
  try {
    const response = await axios.get(API_BASE_URL + `games/all`, {
      headers: {
        token: '123'
      }
    });
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const getUserGames = async (login) => {
  try {
    const response = await axios.get(API_BASE_URL + `users/games/${login}`, {
      headers: {
        token: '123'
      }
    });
    return response.data;
  } catch (error) {
    throw error;
  }
};

export const createCharacter = async (name, gameSystemId, userId, picture, stats) => {
  return await axios.post(
    API_BASE_URL + `characters/create`,
    {
      name,
      gameSystemId,
      userId,
      picture,
      stats
    },
    {
      headers: {
        token: '123'
      }
    }
  );
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
  await axios.post(
    API_BASE_URL + `games/create`,
    {
      name,
      gameSystemId,
      masterId,
      picture,
      currentStatus,
      finishDate,
      description
    },
    {
      headers: {
        token: '123'
      }
    }
  );
};
