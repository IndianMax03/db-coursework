import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { getUsers } from '../../service/data.service';

export const fetchUsers = createAsyncThunk('users/fetchUsers', async () => {
  const res = await getUsers();
  return res;
});

export const UsersSlice = createSlice({
  name: 'users',
  initialState: {
    users: [],
    isLoading: false,
    hasError: false
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchUsers.pending, (state, action) => {
        state.isLoading = true;
        state.hasError = false;
      })
      .addCase(fetchUsers.fulfilled, (state, action) => {
        state.users = action.payload;
        state.isLoading = false;
        state.hasError = false;
      })
      .addCase(fetchUsers.rejected, (state, action) => {
        state.hasError = true;
        state.isLoading = false;
      });
  }
});

export const selectUsers = (state) => state.users.users;
export const selectLoading = (state) => state.users.isLoading;
export const selectError = (state) => state.users.hasError;

export default UsersSlice.reducer;
