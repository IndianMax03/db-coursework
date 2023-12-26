import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { getLobbyByCharacter } from '../../service/data.service';

export const fetchLobbyByCharacter = createAsyncThunk(
  'lobby/fetchLobbyByCharacter',
  async (characterId) => {
    const res = await getLobbyByCharacter(characterId);
    return res;
  }
);

export const LobbySlice = createSlice({
  name: 'lobby',
  initialState: {
    lobby: [],
    isLoading: false,
    hasError: false
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchLobbyByCharacter.pending, (state, action) => {
        state.isLoading = true;
        state.hasError = false;
      })
      .addCase(fetchLobbyByCharacter.fulfilled, (state, action) => {
        state.lobby = action.payload;
        state.isLoading = false;
        state.hasError = false;
      })
      .addCase(fetchLobbyByCharacter.rejected, (state, action) => {
        state.hasError = true;
        state.isLoading = false;
      });
  }
});

export const selectLobby = (state) => state.lobby.lobby;

export default LobbySlice.reducer;
