import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { getAllGames } from '../../service/data.service';

export const fetchGames = createAsyncThunk('games/fetchGames', async () => {
  const res = await getAllGames();
  return res;
});

export const gamesSlice = createSlice({
  name: 'games',
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

export const selectGames = (state) => state.games.games;
export const selectLoading = (state) => state.games.isLoading;
export const selectError = (state) => state.games.hasError;

export default gamesSlice.reducer;
