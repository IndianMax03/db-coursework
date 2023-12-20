import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { getUser } from '../../service/data.service';
import { login as enter } from '../../auth/auth.service';

export const fetchUser = createAsyncThunk('user/fetchUser', async (login) => {
  const res = await getUser(login);
  return res;
});

export const fetchSelf = createAsyncThunk('user/fetchSelf', async (login, password) => {
    const res = await enter(login, password);
    return res;
  });


const UserSlice = createSlice({
  name: 'user',
  initialState: {
    user: {},
    self: {},
    isLoading: false,
    hasError: false
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchUser.pending, (state, action) => {
        state.isLoading = true;
        state.hasError = false;
      })
      .addCase(fetchSelf.pending, (state, action) => {
        state.isLoading = true;
        state.hasError = false;
      })
      .addCase(fetchUser.fulfilled, (state, action) => {
        state.user = action.payload;
        state.isLoading = false;
        state.hasError = false;
      })
      .addCase(fetchSelf.fulfilled, (state, action) => {
        state.self = action.payload;
        state.isLoading = false;
        state.hasError = false;
      })
      .addCase(fetchUser.rejected, (state, action) => {
        state.hasError = true;
        state.isLoading = false;
      })
      .addCase(fetchSelf.rejected, (state, action) => {
        state.hasError = true;
        state.isLoading = false;
      });
  }
});

export const selectUser = (state) => state.user.user;
export const selectSelf = (state) => state.user.self;
export const selectLoading = (state) => state.user.isLoading;
export const selectError = (state) => state.user.hasError;

export default UserSlice.reducer;
