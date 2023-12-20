import { configureStore } from '@reduxjs/toolkit';
import characterReducer from './slices/CharacterSlice';
import gameReducer from './slices/GameSlice';
import userReducer from './slices/UserSlice';
import usersReducer from './slices/UsersSlice';
import gamesReducer from './slices/GamesSlice';

export const store = configureStore({
  reducer: {
    character: characterReducer,
    game: gameReducer,
    user: userReducer,
    users: usersReducer,
    games: gamesReducer
  }
});
