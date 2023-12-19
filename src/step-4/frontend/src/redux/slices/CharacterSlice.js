import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { getUserCharacters } from '../../service/data.service';

export const fetchCharacters = createAsyncThunk('character/fetchCharacters', async (login) => {
  const res = await getUserCharacters(login);
  return res;
});

const CharacterSlice = createSlice({
  name: 'character',
  initialState: {
    characters: [],
    isLoading: false,
    hasError: false
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchCharacters.pending, (state, action) => {
        state.isLoading = true;
        state.hasError = false;
      })
      .addCase(fetchCharacters.fulfilled, (state, action) => {
        state.characters = action.payload;
        state.isLoading = false;
        state.hasError = false;
      })
      .addCase(fetchCharacters.rejected, (state, action) => {
        state.hasError = true;
        state.isLoading = false;
      });
  }
});

export const selectCharacters = (state) => state.character.characters;
export const selectLoading = (state) => state.character.isLoading;
export const selectError = (state) => state.character.hasError;

export default CharacterSlice.reducer;
