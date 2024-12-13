import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { getUser } from '../../service/data.service';
import { login as enter, register } from '../../auth/auth.service';

export const fetchUser = createAsyncThunk('user/fetchUser', async (login) => {
  const res = await getUser(login);
  return res;
});

export const fetchSelf = createAsyncThunk('user/fetchSelf', async (data) => {
  let res;
  if (data.isLoggingIn) {
    res = await enter(data.login, data.password);
  } else {
    res = await register(
      data.login,
      data.name,
      data.img,
      data.password,
      data.timezone,
      data.telegramTag,
      data.vkTag
    );
  }

  return res;
});

const UserSlice = createSlice({
  name: 'user',
  initialState: {
    user: {},
    self: {},
    isLoading: false,
    hasError: false,
    isSelfLoading: false,
    hasSelfError: false
  },
  reducers: {
    logout: (state) => {
      state.user = {};
      state.self = {};
      state.isLoading = false;
      state.hasError = false;
      state.isSelfLoading = false;
      state.hasSelfError = false;
    }
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchUser.pending, (state, action) => {
        state.isLoading = true;
        state.hasError = false;
      })
      .addCase(fetchSelf.pending, (state, action) => {
        state.isSelfLoading = true;
        state.hasSelfError = false;
      })
      .addCase(fetchUser.fulfilled, (state, action) => {
        state.user = action.payload;
        state.isLoading = false;
        state.hasError = false;
      })
      .addCase(fetchSelf.fulfilled, (state, action) => {
        state.self = action.payload;
        state.isSelfLoading = false;
        state.hasSelfError = false;
      })
      .addCase(fetchUser.rejected, (state, action) => {
        state.hasError = true;
        state.isLoading = false;
      })
      .addCase(fetchSelf.rejected, (state, action) => {
        state.hasSelfError = true;
        state.isSelfLoading = false;
      });
  }
});

export const selectUser = (state) => state.user.user;
export const selectSelf = (state) => state.user.self;
export const selectLoading = (state) => state.user.isLoading;
export const selectError = (state) => state.user.hasError;
export const selectSelfError = (state) => state.user.hasSelfError;
export const selectSelfLoading = (state) => state.user.isSelfLoading;
export const { logout } = UserSlice.actions;

export default UserSlice.reducer;
