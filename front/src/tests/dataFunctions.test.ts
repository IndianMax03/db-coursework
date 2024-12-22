import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import {
  addMasterRole,
  changeGameStatus,
  changeKarma,
  changeRequestStatus,
  createCharacter,
  getAllGames,
  getFriendshipRequests,
  getLobbyByCharacter,
  getLobbyByGame,
  getReviews,
  getRoles,
  getUser,
  getUserCharacters,
  getUserGames,
  getUserRoles,
  createGame,
  getUsers,
  sendCharacterLobbyRequest,
  sendReview
} from '../service/data.service';

const mock = new MockAdapter(axios);
const API_BASE_URL = 'http://localhost:8081/';
describe('API functions', () => {
  afterEach(() => {
    mock.reset();
  });

  describe('getUsers', () => {
    it('should return a list of users on success', async () => {
      const mockData = [
        {
          id: 1,
          name: 'maxim',
          login: 'maxim',
          password: '123',
          timezone: '+1',
          telegramTag: 'maxim',
          vkTag: 'maxim'
        }
      ];
      mock.onGet(`${API_BASE_URL}users`).reply(200, mockData);

      const result = await getUsers();
      expect(result).toEqual(mockData);
    });

    it('should throw an error on failure', async () => {
      mock.onGet(`${API_BASE_URL}users`).reply(500);

      await expect(getUsers()).rejects.toThrow();
    });
  });

  describe('getUser', () => {
    it('should return a user object when login is provided', async () => {
      const mockData = {
        id: 1,
        name: 'maxim',
        login: 'maxim',
        password: '123',
        timezone: '+1',
        telegramTag: 'maxim',
        vkTag: 'maxim'
      };
      const login = 'maxim';
      mock.onGet(`${API_BASE_URL}users/${login}`).reply(200, mockData);

      const result = await getUser(login);
      expect(result).toEqual(mockData);
    });

    it('should return undefined when login is not provided', async () => {
      const result = await getUser('');
      expect(result).toBeUndefined();
    });

    it('should throw an error on failure', async () => {
      const login = 'maxim';
      mock.onGet(`${API_BASE_URL}users/${login}`).reply(500);

      await expect(getUser(login)).rejects.toThrow();
    });
  });

  describe('getUserRoles', () => {
    it('should return undefined when login is not provided', async () => {
      const result = await getUserRoles('');
      expect(result).toBeUndefined();
    });

    it('should call the correct endpoint when login is provided', async () => {
      const login = 'maxim';
      mock.onGet(`${API_BASE_URL}users/roles/${login}`).reply(200);

      await getUserRoles(login);
      expect(mock.history.get[0].url).toBe(`${API_BASE_URL}users/roles/${login}`);
    });
  });

  describe('createCharacter', () => {
    it('should create a character and return the response data', async () => {
      const mockData = {
        id: 1,
        name: 'Мишка косолапый',
        gameSystemId: 1,
        userId: 1,
        pdf: 'file.pdf'
      };
      const formData = {
        name: 'Мишка косолапый',
        gameSystemId: 1,
        img: 'image.png',
        userId: 123,
        pdf: 'file.pdf'
      };

      mock.onPost(`${API_BASE_URL}characters`).reply(201, mockData);

      const result = await createCharacter(
        formData.name,
        formData.gameSystemId,
        formData.img,
        formData.userId,
        formData.pdf
      );

      expect(result).toEqual(mockData);
    });

    it('should throw an error on failure', async () => {
      const formData = {
        name: 'Мишка косолапый',
        gameSystemId: 1,
        img: 'image.png',
        userId: 123,
        pdf: 'file.pdf'
      };
      mock.onPost(`${API_BASE_URL}characters`).reply(500);

      await expect(
        createCharacter(
          formData.name,
          formData.gameSystemId,
          formData.img,
          formData.userId,
          formData.pdf
        )
      ).rejects.toThrow();
    });
  });

  describe('getAllGames', () => {
    it('should return a list of games on success', async () => {
      const mockData = [
        { id: 1, name: 'Game 1', gameSystemId: 1, masterId: 1, description: 'description' }
      ];
      mock.onGet(`${API_BASE_URL}games`).reply(200, mockData);

      const result = await getAllGames();
      expect(result).toEqual(mockData);
    });

    it('should throw an error on failure', async () => {
      mock.onGet(`${API_BASE_URL}games`).reply(500);

      await expect(getAllGames()).rejects.toThrow();
    });
  });

  describe('getUserCharacters', () => {
    it('should return a list of characters for a user', async () => {
      const mockData = [
        { id: 1, name: 'Мишка косолапый', gameSystemId: 1, userId: 1, pdf: 'file.pdf' }
      ];
      const login = 'maxim';
      mock.onGet(`${API_BASE_URL}users/${login}/characters`).reply(200, mockData);

      const result = await getUserCharacters(login);
      expect(result).toEqual(mockData);
    });

    it('should return undefined if no login is provided', async () => {
      const result = await getUserCharacters('');
      expect(result).toBeUndefined();
    });

    it('should throw an error on failure', async () => {
      const login = 'maxim';
      mock.onGet(`${API_BASE_URL}users/${login}/characters`).reply(500);

      await expect(getUserCharacters(login)).rejects.toThrow();
    });
  });

  describe('getUserGames', () => {
    it('should return a list of games for a user', async () => {
      const mockData = [
        { id: 1, name: 'Game 1', gameSystemId: 1, masterId: 1, description: 'description' }
      ];
      const login = 'maxim';
      mock.onGet(`${API_BASE_URL}users/${login}/games`).reply(200, mockData);

      const result = await getUserGames(login);
      expect(result).toEqual(mockData);
    });

    it('should return undefined if no login is provided', async () => {
      const result = await getUserGames('');
      expect(result).toBeUndefined();
    });

    it('should throw an error on failure', async () => {
      const login = 'maxim';
      mock.onGet(`${API_BASE_URL}users/${login}/games`).reply(500);

      await expect(getUserGames(login)).rejects.toThrow();
    });
  });

  describe('getFriendshipRequests', () => {
    it('should return a list of friendship requests on success', async () => {
      const mockData = [{ id: 1, senderId: 2, receiverId: 1 }];
      const login = 'maxim';
      mock.onGet(`${API_BASE_URL}users/${login}/friends`).reply(200, mockData);

      const result = await getFriendshipRequests(login);
      expect(result).toEqual(mockData);
    });

    it('should return undefined if no login is provided', async () => {
      const result = await getFriendshipRequests('');
      expect(result).toBeUndefined();
    });

    it('should throw an error on failure', async () => {
      const login = 'maxim';
      mock.onGet(`${API_BASE_URL}users/${login}/friends`).reply(500);

      await expect(getFriendshipRequests(login)).rejects.toThrow();
    });
  });

  describe('getLobbyByCharacter', () => {
    it('should return lobby data when characterId is provided', async () => {
      const mockData = { id: 1, gameId: 1, format: 'online' };
      const characterId = 1;
      mock.onGet(`${API_BASE_URL}characters/${characterId}/lobby`).reply(200, mockData);

      const result = await getLobbyByCharacter(characterId);
      expect(result).toEqual(mockData);
    });

    it('should return undefined if no characterId is provided', async () => {
      const result = await getLobbyByCharacter(null);
      expect(result).toBeUndefined();
    });

    it('should throw an error on failure', async () => {
      const characterId = 1;
      mock.onGet(`${API_BASE_URL}characters/${characterId}/lobby`).reply(500);

      await expect(getLobbyByCharacter(characterId)).rejects.toThrow();
    });
  });

  describe('getLobbyByGame', () => {
    it('should return lobby data when gameId is provided', async () => {
      const mockData = { id: 1, gameId: 1, format: 'online' };
      const gameId = 1;
      mock.onGet(`${API_BASE_URL}games/${gameId}/lobby`).reply(200, mockData);

      const result = await getLobbyByGame(gameId);
      expect(result).toEqual(mockData);
    });

    it('should throw an error if gameId is not provided', async () => {
      const result = await getLobbyByGame(null);
      expect(result).toBeUndefined();
    });

    it('should throw an error on failure', async () => {
      const gameId = 1;
      mock.onGet(`${API_BASE_URL}games/${gameId}/lobby`).reply(500);

      await expect(getLobbyByGame(gameId)).rejects.toThrow();
    });
  });

  describe('changeKarma', () => {
    it('should change karma successfully', async () => {
      const mockData = { success: true };
      const senderUserId = 1;
      const receiverUserId = 2;
      const increase = true;
      mock.onPatch(`${API_BASE_URL}users/karma`).reply(200, mockData);

      const result = await changeKarma(senderUserId, receiverUserId, increase);
      expect(result).toEqual(mockData);
    });

    it('should throw an error on failure', async () => {
      const senderUserId = 1;
      const receiverUserId = 2;
      const increase = true;
      mock.onPatch(`${API_BASE_URL}users/karma`).reply(500);

      await expect(changeKarma(senderUserId, receiverUserId, increase)).rejects.toThrow();
    });
  });

  describe('getReviews', () => {
    it('should return reviews for a user', async () => {
      const mockData = [
        {
          reviewerId: 1,
          recipientId: 2,
          rating: 5,
          content: 'Блин вообще крутой мастер никогда таких не встречал!'
        }
      ];
      const login = 'maxim';
      mock.onGet(`${API_BASE_URL}users/${login}/reviews`).reply(200, mockData);

      const result = await getReviews(login);
      expect(result).toEqual(mockData);
    });

    it('should return undefined if no login is provided', async () => {
      const result = await getReviews('');
      expect(result).toBeUndefined();
    });

    it('should throw an error on failure', async () => {
      const login = 'maxim';
      mock.onGet(`${API_BASE_URL}users/${login}/reviews`).reply(500);

      await expect(getReviews(login)).rejects.toThrow();
    });
  });

  describe('changeRequestStatus', () => {
    it('should change request status successfully', async () => {
      const mockData = { success: true };
      const id = 1;
      const currentStatus = 'approved';
      mock.onPatch(`${API_BASE_URL}characters/request/update`).reply(200, mockData);

      const result = await changeRequestStatus(id, currentStatus);
      expect(result).toEqual(mockData);
    });

    it('should throw an error on failure', async () => {
      const id = 1;
      const currentStatus = 'approved';
      mock.onPatch(`${API_BASE_URL}characters/request/update`).reply(500);

      await expect(changeRequestStatus(id, currentStatus)).rejects.toThrow();
    });
  });

  describe('sendCharacterLobbyRequest', () => {
    it('should send a character lobby request successfully', async () => {
      const mockData = { success: true };
      const gameId = 1;
      const characterId = 1;
      mock.onPost(`${API_BASE_URL}characters/request`).reply(200, mockData);

      const result = await sendCharacterLobbyRequest(gameId, characterId);
      expect(result).toEqual(mockData);
    });

    it('should throw an error on failure', async () => {
      const gameId = 1;
      const characterId = 1;
      mock.onPost(`${API_BASE_URL}characters/request`).reply(500);

      await expect(sendCharacterLobbyRequest(gameId, characterId)).rejects.toThrow();
    });
  });

  describe('sendReview', () => {
    it('should send a review successfully', async () => {
      const mockData = { success: true };
      const reviewerId = 1;
      const recipientId = 2;
      const rating = 5;
      const content = 'Блин вообще крутой мастер никогда таких не встречал!';
      mock.onPost(`${API_BASE_URL}users/reviews`).reply(200, mockData);

      const result = await sendReview(reviewerId, recipientId, rating, content);
      expect(result).toEqual(mockData);
    });

    it('should throw an error on failure', async () => {
      const reviewerId = 1;
      const recipientId = 2;
      const rating = 5;
      const content = 'Блин вообще крутой мастер никогда таких не встречал!';
      mock.onPost(`${API_BASE_URL}users/reviews`).reply(500);

      await expect(sendReview(reviewerId, recipientId, rating, content)).rejects.toThrow();
    });
  });

  describe('changeGameStatus', () => {
    it('should change game status successfully', async () => {
      const mockData = { success: true };
      const id = 1;
      const currentStatus = 'not-started';
      mock.onPatch(`${API_BASE_URL}games/status`).reply(200, mockData);

      const result = await changeGameStatus(id, currentStatus);
      expect(result).toEqual(mockData);
    });

    it('should throw an error on failure', async () => {
      const id = 1;
      const currentStatus = 'not-started';
      mock.onPatch(`${API_BASE_URL}games/status`).reply(500);

      await expect(changeGameStatus(id, currentStatus)).rejects.toThrow();
    });
  });

  describe('getRoles', () => {
    it('should return roles for a user', async () => {
      const mockData = [{ role: 'admin' }];
      const login = 'maxim';
      mock.onGet(`${API_BASE_URL}users/${login}/roles`).reply(200, mockData);

      const result = await getRoles(login);
      expect(result).toEqual(mockData);
    });

    it('should return undefined if no login is provided', async () => {
      const result = await getRoles('');
      expect(result).toBeUndefined();
    });

    it('should throw an error on failure', async () => {
      const login = 'maxim';
      mock.onGet(`${API_BASE_URL}users/${login}/roles`).reply(500);

      await expect(getRoles(login)).rejects.toThrow();
    });
  });

  describe('addMasterRole', () => {
    it('should add master role to a user', async () => {
      const mockData = { success: true };
      const login = 'maxim';
      mock.onGet(`${API_BASE_URL}users/${login}/roles/become-master`).reply(200, mockData);

      const result = await addMasterRole(login);
      expect(result).toEqual(mockData);
    });

    it('should return undefined if no login is provided', async () => {
      const result = await addMasterRole('');
      expect(result).toBeUndefined();
    });

    it('should throw an error on failure', async () => {
      const login = 'maxim';
      mock.onGet(`${API_BASE_URL}users/${login}/roles/become-master`).reply(500);

      await expect(addMasterRole(login)).rejects.toThrow();
    });
  });

  describe('createGame', () => {
    it('should create game', async () => {
      const mockData = {
        name: 'Game 1',
        gameSystemId: 1,
        masterId: 1,
        description: 'description',
        img: 'image.png',
        currentStatus: 'not-started'
      };
      const formData = {
        name: 'Game 1',
        gameSystemId: 1,
        masterId: 1,
        description: 'description',
        img: 'image.png',
        currentStatus: 'not-started'
      };

      mock.onPost(`${API_BASE_URL}games`).reply(201, mockData);

      const result = await createGame(
        formData.name,
        formData.gameSystemId,
        formData.img,
        formData.masterId,
        formData.description,
        formData.currentStatus
      );

      expect(result).toEqual(mockData);
    });

    it('should throw an error on failure', async () => {
      const formData = {
        name: 'Game 1',
        gameSystemId: 1,
        masterId: 1,
        description: 'description',
        img: 'image.png',
        currentStatus: 'not-started'
      };
      mock.onPost(`${API_BASE_URL}games`).reply(500);

      await expect(
        createGame(
          formData.name,
          formData.gameSystemId,
          formData.img,
          formData.masterId,
          formData.description,
          formData.currentStatus
        )
      ).rejects.toThrow();
    });
  });
});
