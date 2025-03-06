import { createSlice } from '@reduxjs/toolkit'
import React from 'react'

const initialState = {
    isAuthenticated: !!localStorage.getItem("username"),
    status: "idle",
    user:null,
    error:null
}

export const authSlice = createSlice({
    name: "auth",
    initialState,
    reducers: {
        logout: (state) => {

        }
    },
    extraReducers:(builder)=>{

    }
})


export const{logout}= authSlice.actions
export default authSlice.reducer