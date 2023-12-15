import axios from 'axios';

const API_BASE_URL = 'http://localhost:8081/';

export const getUsers = async () => {
  return await axios
    .get(API_BASE_URL + 'users/all', {
      headers: {
        token: '123'
      }
    })
    .then((res) => console.log(res.data));
};

export const getUser = async (login) => {
  return await axios
    .get(API_BASE_URL + `users/${login}`, {
      headers: {
        token: '123'
      }
    })
    .then((res) => console.log(res.data));
};

export const getUserRoles = async (login) => {
  await axios
    .get(API_BASE_URL + `users/roles/${login}`, {
      headers: {
        token: '123'
      }
    })
    .then((res) => console.log(res.data));
};

export const getUserCharacters = async (login) => {
  await axios
    .get(API_BASE_URL + `users/characters/${login}`, {
      headers: {
        token: '123'
      }
    })
    .then((res) => console.log(res.data));
};

export const getAllGames = async (login) => {
  await axios
    .get(API_BASE_URL + `games/all`, {
      headers: {
        token: '123'
      }
    })
    .then((res) => console.log(res.data));
};

export const getUserGames = async (login) => {
  await axios
    .get(API_BASE_URL + `users/games/${login}`, {
      headers: {
        token: '123'
      }
    })
    .then((res) => console.log(res.data));
};

export const createCharacter = async (name, gameSystemId, userId, picture, stats) => {
  await axios
    .post(API_BASE_URL + `characters/create`,{
        name,
        gameSystemId,
        userId,
        picture,
        stats
      },{
      headers: {
        token: '123'
      }
    })
    .then((res) => console.log(res.data));
};

export const createGame = async (name, gameSystemId, userId) => {
  await axios
    .post(API_BASE_URL + `games/create`, {
      headers: {
        token: '123'
      }
    })
    .then((res) => console.log(res.data));
};
