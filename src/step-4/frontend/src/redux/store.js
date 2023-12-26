import { configureStore } from '@reduxjs/toolkit';
import characterReducer from './slices/CharacterSlice';
import gameReducer from './slices/GameSlice';
import userReducer from './slices/UserSlice';
import usersReducer from './slices/UsersSlice';
import gamesReducer from './slices/GamesSlice';
import friendsReducer from './slices/FriendsSlice';
import lobbyReducer from './slices/LobbySlice';

export const store = configureStore({
  reducer: {
    character: characterReducer,
    game: gameReducer,
    user: userReducer,
    users: usersReducer,
    games: gamesReducer,
    friends: friendsReducer,
    lobby: lobbyReducer
  }
});
