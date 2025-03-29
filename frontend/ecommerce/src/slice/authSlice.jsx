import { createSlice } from '@reduxjs/toolkit'
import React from 'react'
import { loginApi } from '../api/userApi';

const initialState = {
    isAuthenticated: !!sessionStorage.getItem("username"),
    status: "idle",
    user:null,
    error:null
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
                // state.user = action.payload; // Store user data if returned from API
                sessionStorage.setItem('username', action.payload);
                console.log("sessionStorage set");
                
            })
            .addCase(loginApi.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
            });
            // .addCase(logoutUser.pending,(state) =>{
            //     state.status = 'loading'
            // })
            // .addCase(logoutUser.fulfilled,(state) =>{
            //     state.status ="succeeded",
            //     state.isAuthenticated= false,
            //     sessionStorage.removeItem('username');
            //     console.log("local Storage removed");
            //     state.user = null;
                
            // })
            // .addCase(logoutUser.rejected,(state,action)=>{
            //     state.status = "failed",
            //     state.error = action.error.message;
            // });
    },
})


export const{logout}= authSlice.actions
export default authSlice.reducer