import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import * as api from "lib/api";
import { createThunkReducers } from "lib/createThunkReducers";

export const signUp = createAsyncThunk("signUp", async (param) => {
  try {
    const { data } = await api.signUp(param);
    return data;
  } catch (e) {
    throw e;
  }
});

const initialState = { data: null, error: null };

const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {},
  extraReducers: createThunkReducers(initialState, signUp),
});

export default userSlice;
