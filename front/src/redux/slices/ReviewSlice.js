import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { getReviews } from '../../service/data.service';

export const fetchReviews = createAsyncThunk('review/fetchReviews', async (login) => {
  const res = await getReviews(login);
  return res;
});

export const ReviewSlice = createSlice({
  name: 'review',
  initialState: {
    reviews: [],
    isLoading: false,
    hasError: false
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchReviews.pending, (state, action) => {
        state.isLoading = true;
        state.hasError = false;
      })
      .addCase(fetchReviews.fulfilled, (state, action) => {
        state.reviews = action.payload;
        state.isLoading = false;
        state.hasError = false;
      })
      .addCase(fetchReviews.rejected, (state, action) => {
        state.hasError = true;
        state.isLoading = false;
      });
  }
});

export const selectReviews = (state) => state.review.reviews;

export default ReviewSlice.reducer;
