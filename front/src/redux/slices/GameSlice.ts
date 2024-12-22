import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit';
import { getUserGames } from '../../service/data.service';
import { RootState } from '../store';

interface Game {
  id: number;
  name: string;
  gameSystemId: string;
  creationDate: string;
  currentStatus: string;
  finishDate: string | null;
  description: string;
  picture: string;
}

interface GameState {
  games: Game[];
  isLoading: boolean;
  hasError: boolean;
}

const initialState: GameState = {
  games: [],
  isLoading: false,
  hasError: false
};

export const fetchUserGames = createAsyncThunk<Game[], string>(
  'game/fetchUserGames',
  async (login) => {
    const res = await getUserGames(login);
    return res;
  }
);

export const gameSlice = createSlice({
  name: 'game',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchUserGames.pending, (state) => {
        state.isLoading = true;
        state.hasError = false;
      })
      .addCase(fetchUserGames.fulfilled, (state, action: PayloadAction<Game[]>) => {
        state.games = action.payload;
        state.isLoading = false;
        state.hasError = false;
      })
      .addCase(fetchUserGames.rejected, (state) => {
        state.hasError = true;
        state.isLoading = false;
      });
  }
});

export const selectGames = (state: RootState) => state.game.games;
export const selectLoading = (state: RootState) => state.game.isLoading;
export const selectError = (state: RootState) => state.game.hasError;

export default gameSlice.reducer;
