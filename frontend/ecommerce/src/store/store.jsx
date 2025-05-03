import { configureStore } from '@reduxjs/toolkit'
import React from 'react'
import authReducer from "./../slice/authSlice"
import productReducer from "./../slice/productSlice"
import cartReducer from "./../slice/cartSlice"
import orderReducer from "./../slice/orderSlice"

export const store = configureStore({
    reducer:{
        auth:authReducer,
        product:productReducer,
        cart:cartReducer,
        order:orderReducer
    },
})
