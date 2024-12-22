import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit';
import { getReviews } from '../../service/data.service';
import { RootState } from '../store';

interface Review {
  picture: string;
  content: string;
  date: string;
  rating: number;
  name: string;
}

interface ReviewState {
  reviews: Review[];
  isLoading: boolean;
  hasError: boolean;
}

const initialState: ReviewState = {
  reviews: [],
  isLoading: false,
  hasError: false
};

export const fetchReviews = createAsyncThunk<Review[], string>(
  'review/fetchReviews',
  async (login) => {
    const res = await getReviews(login);
    return res;
  }
);

export const ReviewSlice = createSlice({
  name: 'review',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchReviews.pending, (state) => {
        state.isLoading = true;
        state.hasError = false;
      })
      .addCase(fetchReviews.fulfilled, (state, action: PayloadAction<Review[]>) => {
        state.reviews = action.payload;
        state.isLoading = false;
        state.hasError = false;
      })
      .addCase(fetchReviews.rejected, (state) => {
        state.hasError = true;
        state.isLoading = false;
      });
  }
});

export const selectReviews = (state: RootState) => state.review.reviews;

export default ReviewSlice.reducer;
