import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit';
import { getRoles } from '../../service/data.service';
import { RootState } from '../store';

interface Role {
  id: number;
  name: string;
}

interface RolesState {
  roles: Role[];
  hasMasterRole: boolean;
  isLoading: boolean;
  hasError: boolean;
}

const initialState: RolesState = {
  roles: [],
  hasMasterRole: false,
  isLoading: false,
  hasError: false
};

export const fetchRoles = createAsyncThunk<Role[], string>('roles/fetchRoles', async (login) => {
  const res = await getRoles(login);
  return res;
});

export const RolesSlice = createSlice({
  name: 'roles',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchRoles.pending, (state) => {
        state.isLoading = true;
        state.hasError = false;
      })
      .addCase(fetchRoles.fulfilled, (state, action: PayloadAction<Role[]>) => {
        state.roles = action.payload;
        state.hasMasterRole = state.roles.length > 0;
        state.isLoading = false;
        state.hasError = false;
      })
      .addCase(fetchRoles.rejected, (state) => {
        state.hasError = true;
        state.isLoading = false;
      });
  }
});

export const selectRoles = (state: RootState) => state.roles.roles;
export const selectHasMasterRole = (state: RootState) => state.roles.hasMasterRole;

export default RolesSlice.reducer;
