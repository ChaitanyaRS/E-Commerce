import { configureStore } from '@reduxjs/toolkit'
import React from 'react'
import authReducer from "./../slice/authSlice"

export const store = configureStore({
    reducer:{
        auth:authReducer
    },
})
