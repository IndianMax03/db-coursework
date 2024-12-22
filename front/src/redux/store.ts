import { configureStore } from '@reduxjs/toolkit';
import characterReducer from './slices/CharacterSlice';
import gameReducer from './slices/GameSlice';
import userReducer from './slices/UserSlice';
import usersReducer from './slices/UsersSlice';
import gamesReducer from './slices/GamesSlice';
import friendsReducer from './slices/FriendsSlice';
import lobbyReducer from './slices/LobbySlice';
import reviewReducer from './slices/ReviewSlice';
import rolesReducer from './slices/RolesSlice';

export const store = configureStore({
  reducer: {
    character: characterReducer,
    game: gameReducer,
    user: userReducer,
    users: usersReducer,
    games: gamesReducer,
    friends: friendsReducer,
    lobby: lobbyReducer,
    review: reviewReducer,
    roles: rolesReducer
  }
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
