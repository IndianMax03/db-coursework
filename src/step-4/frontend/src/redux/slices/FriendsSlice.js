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
    friends: [],
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
        const friendsIncoming = state.friendshipRequests.income.filter((request) => {
          return request.friendshipStatus === 'approved';
        });
        const friendsOutcoming = state.friendshipRequests.outcome.filter((request) => {
          return request.friendshipStatus === 'approved';
        });
        state.friends = friendsIncoming.concat(friendsOutcoming);
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
export const selectFriends = (state) => state.friends.friends;

export default FriendsSlice.reducer;
