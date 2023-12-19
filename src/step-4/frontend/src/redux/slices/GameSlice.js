import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { getUserGames } from '../../service/data.service';

export const fetchGames = createAsyncThunk('game/fetchGames', async (login) => {
  const res = await getUserGames(login);
  return res;
});

export const gameSlice = createSlice({
  name: 'game',
  initialState: {
    games: [],
    isLoading: false,
    hasError: false
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchGames.pending, (state, action) => {
        state.isLoading = true;
        state.hasError = false;
      })
      .addCase(fetchGames.fulfilled, (state, action) => {
        state.games = action.payload;
        state.isLoading = false;
        state.hasError = false;
      })
      .addCase(fetchGames.rejected, (state, action) => {
        state.hasError = true;
        state.isLoading = false;
      });
  }
});

export const selectGames = (state) => state.game.games;
export const selectLoading = (state) => state.game.isLoading;
export const selectError = (state) => state.game.hasError;

export default gameSlice.reducer;
