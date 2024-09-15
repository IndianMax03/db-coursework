import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { getRoles } from '../../service/data.service';

export const fetchRoles = createAsyncThunk('roles/fetchRoles', async (login) => {
  const res = await getRoles(login);
  return res;
});

export const RolesSlice = createSlice({
  name: 'roles',
  initialState: {
    roles: [],
    hasMasterRole: false,
    isLoading: false,
    hasError: false
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchRoles.pending, (state, action) => {
        state.isLoading = true;
        state.hasError = false;
      })
      .addCase(fetchRoles.fulfilled, (state, action) => {
        state.reviews = action.payload;
        state.hasMasterRole = state.reviews.length !== 0;
        state.isLoading = false;
        state.hasError = false;
      })
      .addCase(fetchRoles.rejected, (state, action) => {
        state.hasError = true;
        state.isLoading = false;
      });
  }
});

export const selectRoles = (state) => state.roles.roles;
export const selectHasMasterRole = (state) => state.roles.hasMasterRole;


export default RolesSlice.reducer;
