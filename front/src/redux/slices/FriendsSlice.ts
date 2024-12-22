import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit';
import { RootState } from '../store';
import { getFriendshipRequests } from '../../service/data.service';

interface FriendshipRequest {
  id: number;
  friendshipStatus: string;
}

interface FriendshipRequests {
  income: FriendshipRequest[];
  outcome: FriendshipRequest[];
}

interface FriendState {
  friendshipRequests: FriendshipRequests;
  friends: FriendshipRequest[];
  isLoading: boolean;
  hasError: boolean;
}

const initialFriendState: FriendState = {
  friendshipRequests: { income: [], outcome: [] },
  friends: [],
  isLoading: false,
  hasError: false
};

export const fetchFriendshipRequests = createAsyncThunk<FriendshipRequests, string>(
  'friends/fetchFriendshipRequests',
  async (login) => {
    const res = await getFriendshipRequests(login);
    return res;
  }
);

export const FriendsSlice = createSlice({
  name: 'friends',
  initialState: initialFriendState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchFriendshipRequests.pending, (state) => {
        state.isLoading = true;
        state.hasError = false;
      })
      .addCase(
        fetchFriendshipRequests.fulfilled,
        (state, action: PayloadAction<FriendshipRequests>) => {
          state.friendshipRequests = action.payload;
          const friendsIncoming = state.friendshipRequests.income.filter(
            (req) => req.friendshipStatus === 'approved'
          );
          const friendsOutgoing = state.friendshipRequests.outcome.filter(
            (req) => req.friendshipStatus === 'approved'
          );
          state.friends = [...friendsIncoming, ...friendsOutgoing];
          state.isLoading = false;
        }
      )
      .addCase(fetchFriendshipRequests.rejected, (state) => {
        state.isLoading = false;
        state.hasError = true;
      });
  }
});

export const selectFriendshipRequests = (state: RootState) => state.friends.friendshipRequests;
export const selectFriends = (state: RootState) => state.friends.friends;

export default FriendsSlice.reducer;
