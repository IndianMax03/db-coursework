import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { getLobbyByCharacter, getLobbyByGame } from '../../service/data.service';

export const fetchLobbyByCharacter = createAsyncThunk(
  'lobby/fetchLobbyByCharacter',
  async (characterId) => {
    const res = await getLobbyByCharacter(characterId);
    return res;
  }
);

export const fetchLobbyByGame = createAsyncThunk('lobby/fetchLobbyByGame', async (gameId) => {
  const res = await getLobbyByGame(gameId);
  return res;
});

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
      .addCase(fetchLobbyByGame.pending, (state, action) => {
        state.isLoading = true;
        state.hasError = false;
      })
      .addCase(fetchLobbyByCharacter.fulfilled, (state, action) => {
        state.lobby = action.payload;
        state.isLoading = false;
        state.hasError = false;
      })
      .addCase(fetchLobbyByGame.fulfilled, (state, action) => {
        state.lobby = action.payload;
        state.isLoading = false;
        state.hasError = false;
      })
      .addCase(fetchLobbyByCharacter.rejected, (state, action) => {
        state.hasError = true;
        state.isLoading = false;
      })
      .addCase(fetchLobbyByGame.rejected, (state, action) => {
        state.hasError = true;
        state.isLoading = false;
      });
  }
});

export const selectLobby = (state) => state.lobby.lobby;
export const selectLobbyLoading = (state) => state.lobby.isLoading;
export const selectLobbyError = (state) => state.lobby.hasError;

export default LobbySlice.reducer;
