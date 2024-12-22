import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit';
import { RootState } from '../store';
import { getUserCharacters } from '../../service/data.service';

interface CharacterState {
  characters: Character[];
  freeCharacters: Character[];
  isLoading: boolean;
  hasError: boolean;
}

interface Character {
  id: number;
  name: string;
  currentStatus: string;
  gameSystemId: number;
  picture: string | null;
  stats: object;
}

const initialState: CharacterState = {
  characters: [],
  freeCharacters: [],
  isLoading: false,
  hasError: false
};

export const fetchCharacters = createAsyncThunk<Character[], string>(
  'character/fetchCharacters',
  async (login) => {
    const res = await getUserCharacters(login);
    return res;
  }
);

const CharacterSlice = createSlice({
  name: 'character',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchCharacters.pending, (state) => {
        state.isLoading = true;
        state.hasError = false;
      })
      .addCase(fetchCharacters.fulfilled, (state, action: PayloadAction<Character[]>) => {
        state.characters = action.payload;
        state.freeCharacters = state.characters.filter(
          (character) => character.currentStatus === 'free'
        );
        state.isLoading = false;
      })
      .addCase(fetchCharacters.rejected, (state) => {
        state.hasError = true;
        state.isLoading = false;
      });
  }
});

export const selectCharacters = (state: RootState) => state.character.characters;
export const selectFreeCharacters = (state: RootState) => state.character.freeCharacters;
export const selectLoading = (state: RootState) => state.character.isLoading;
export const selectError = (state: RootState) => state.character.hasError;

export default CharacterSlice.reducer;
