import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import * as api from "lib/api";
import { createThunkReducers } from "lib/createThunkReducers";

export const signIn = createAsyncThunk("signIn", async (param) => {
  try {
    const { data } = await api.signIn(param);
    return data;
  } catch (e) {
    throw e;
  }
});

const initialState = { data: null, error: null };

const loginSlice = createSlice({
  name: "login",
  initialState,
  reducers: {},
  extraReducers: createThunkReducers(initialState, signIn),
});

export default loginSlice;
