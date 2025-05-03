import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import Cookies from "js-cookie";
import axios from "axios";
import {URL} from "../constants/constants.js";
import React from 'react'

export const placeOrderForUser = createAsyncThunk (
    "order/placeOrder",
    async([order,key])=>{
        try {
            console.log("Called placeOrder");
            const token = Cookies.get('token');
            console.log("Token ",token);
            console.log("order data in slice ", order);
            console.log("key in slice",key)
            const response= await axios.post(URL+"/orders/place-order",order,{
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Idempotency-key':key
                },
                withCredentials: true,
            });

            console.log("response in order slice",response)
            const responseData = response.data;
            const status = response.status;
            return {responseData,status};
        } catch (error) {
            console.log("error ",error.response.status);
            console.log("error message ",error.response);
            return error.response.data;
        }
    }
)

export const getOrderItems = createAsyncThunk(
    "order/getOrderItems",
    async(userId)=>{
        try {
            console.log("Called getOrders");
            const token = Cookies.get('token');
            console.log("Token ",token);
            const response= await axios.get(URL+`/orders/get-orders/${userId}`,{
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
                withCredentials: true,
            });

            console.log("response in order slice",response)
            const responseData = response.data;
            const status = response.status;
            return {responseData,status};
        } catch (error) {
            console.log("error ",error.response.status);
            console.log("error message ",error.response);
            return error.response.data;
        }
    }
)

const initialState={
    orders: [],
    status:"idle",
    error:null
}

export const orderSlice = createSlice({
    name:"order",
    initialState,

    extraReducers:(builder)=>{
        builder
            .addCase(placeOrderForUser.pending, (state,action) => {
                state.status = 'loading';
            })
            .addCase(placeOrderForUser.fulfilled, (state,action) => {
                state.status = 'succeeded';
                state.orders = action.payload;
            })
            .addCase(placeOrderForUser.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
            })
            .addCase(getOrderItems.pending, (state,action) => {
                state.status = 'loading';
            })
            .addCase(getOrderItems.fulfilled, (state,action) => {
                state.status = 'succeeded';
                state.orders = action.payload.responseData;
                console.log("orders data in slice :",state.orders)
            })
            .addCase(getOrderItems.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
            });
    }
})
export default orderSlice.reducer;