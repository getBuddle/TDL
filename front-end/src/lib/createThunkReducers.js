export const createThunkReducers = (initialState, thunk) => {
  return {
    [thunk.pending.type]: (state, action) => {
      state = initialState;
    },
    [thunk.fulfilled.type]: (state, action) => {
      state.data = action.payload;
      state.error = initialState.error;
    },
    [thunk.rejected.type]: (state, action) => {
      state.data = initialState.data;
      state.error = action.error;
    },
  };
};
