import { createSlice } from '@reduxjs/toolkit'
import React from 'react'
import {loginApi, userInfoApi} from '../api/userApi';

const initialState = {
    isAuthenticated: !!sessionStorage.getItem("username"),
    status: "idle",
    accountStatus : "idle",
    userId:sessionStorage.getItem("userId") === undefined ? null : sessionStorage.getItem("userId"),
    error:null,
    user: {}
}

export const authSlice = createSlice({
    name: "auth",
    initialState,

    extraReducers: (builder) => {
        builder
            .addCase(loginApi.pending, (state) => {
                state.status = 'loading';
            })
            .addCase(loginApi.fulfilled, (state, action) => {
                state.status = 'succeeded';
                state.isAuthenticated = true;
                sessionStorage.setItem('username', action.payload.loginObj.username);
                sessionStorage.setItem('userId', action.payload.loginObj.userId);
                console.log("sessionStorage set");
            })
            .addCase(loginApi.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
            })
            .addCase(userInfoApi.pending, (state) => {
                state.accountStatus = 'loading';
            })
            .addCase(userInfoApi.fulfilled, (state, action) => {
                state.accountStatus = 'succeeded';
                state.user = action.payload;
                console.log("User Info set");
            })
            .addCase(userInfoApi.rejected, (state, action) => {
                state.accountStatus = 'failed';
                state.error = action.error.message;
            });
    },
})


export const{logout}= authSlice.actions
export default authSlice.reducer