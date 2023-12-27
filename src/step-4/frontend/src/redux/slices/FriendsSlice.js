import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { getFriendshipRequests } from '../../service/data.service';

export const fetchFriendshipRequests = createAsyncThunk(
  'friends/fetchFriendshipRequests',
  async (login) => {
    const res = await getFriendshipRequests(login);
    return res;
  }
);

export const FriendsSlice = createSlice({
  name: 'friends',
  initialState: {
    friendshipRequests: [],
    isLoading: false,
    hasError: false
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchFriendshipRequests.pending, (state, action) => {
        state.isLoading = true;
        state.hasError = false;
      })
      .addCase(fetchFriendshipRequests.fulfilled, (state, action) => {
        state.friendshipRequests = action.payload;
        state.isLoading = false;
        state.hasError = false;
      })
      .addCase(fetchFriendshipRequests.rejected, (state, action) => {
        state.hasError = true;
        state.isLoading = false;
      });
  }
});

export const selectFriendshipRequests = (state) => state.friends.friendshipRequests;

export default FriendsSlice.reducer;
