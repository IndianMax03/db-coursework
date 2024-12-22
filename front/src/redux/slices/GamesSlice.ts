import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit';
import { getAllGames } from '../../service/data.service';
import { RootState } from '../store';

interface Game {
  id: number;
  name: string;
  [key: string]: any;
}

interface GameState {
  games: Game[];
  isLoading: boolean;
  hasError: boolean;
}

const initialGameState: GameState = {
  games: [],
  isLoading: false,
  hasError: false
};

export const fetchGames = createAsyncThunk<Game[]>('games/fetchGames', async () => {
  const res = await getAllGames();
  return res;
});

export const GamesSlice = createSlice({
  name: 'games',
  initialState: initialGameState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchGames.pending, (state) => {
        state.isLoading = true;
        state.hasError = false;
      })
      .addCase(fetchGames.fulfilled, (state, action: PayloadAction<Game[]>) => {
        state.games = action.payload;
        state.isLoading = false;
      })
      .addCase(fetchGames.rejected, (state) => {
        state.isLoading = false;
        state.hasError = true;
      });
  }
});

export const selectGames = (state: RootState) => state.games.games;
export const selectGamesLoading = (state: RootState) => state.games.isLoading;
export const selectGamesError = (state: RootState) => state.games.hasError;

export default GamesSlice.reducer;
