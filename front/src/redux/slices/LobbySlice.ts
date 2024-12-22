import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit';
import { getLobbyByCharacter, getLobbyByGame } from '../../service/data.service';
import { RootState } from '../store';

interface LobbyState {
  lobby: any[];
  isLoading: boolean;
  hasError: boolean;
}

const initialState: LobbyState = {
  lobby: [],
  isLoading: false,
  hasError: false
};

export const fetchLobbyByCharacter = createAsyncThunk<any[], string>(
  'lobby/fetchLobbyByCharacter',
  async (characterId) => {
    const res = await getLobbyByCharacter(characterId);
    return res;
  }
);

export const fetchLobbyByGame = createAsyncThunk<any[], string>(
  'lobby/fetchLobbyByGame',
  async (gameId) => {
    const res = await getLobbyByGame(gameId);
    return res;
  }
);

// Slice
export const LobbySlice = createSlice({
  name: 'lobby',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchLobbyByCharacter.pending, (state) => {
        state.isLoading = true;
        state.hasError = false;
      })
      .addCase(fetchLobbyByGame.pending, (state) => {
        state.isLoading = true;
        state.hasError = false;
      })
      .addCase(fetchLobbyByCharacter.fulfilled, (state, action: PayloadAction<any[]>) => {
        state.lobby = action.payload;
        state.isLoading = false;
        state.hasError = false;
      })
      .addCase(fetchLobbyByGame.fulfilled, (state, action: PayloadAction<any[]>) => {
        state.lobby = action.payload;
        state.isLoading = false;
        state.hasError = false;
      })
      .addCase(fetchLobbyByCharacter.rejected, (state) => {
        state.isLoading = false;
        state.hasError = true;
      })
      .addCase(fetchLobbyByGame.rejected, (state) => {
        state.isLoading = false;
        state.hasError = true;
      });
  }
});

// Selectors
export const selectLobby = (state: RootState) => state.lobby.lobby;
export const selectLobbyLoading = (state: RootState) => state.lobby.isLoading;
export const selectLobbyError = (state: RootState) => state.lobby.hasError;

export default LobbySlice.reducer;
