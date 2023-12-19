import { configureStore } from '@reduxjs/toolkit';
import characterReducer from './slices/CharacterSlice';
import gameReducer from './slices/GameSlice';

export const store = configureStore({
  reducer: {
    character: characterReducer,
    game: gameReducer
  }
});
