import {createAsyncThunk, createSlice} from '@reduxjs/toolkit'
import React from 'react'
// import {  getAllProductsApi} from '../api/productApi.jsx';
import { URL } from '../constants/constants';
import axios from "axios";
import Cookies from "js-cookie";

export const getAllCartItems = createAsyncThunk(
    'cart/getAllCartItems',
    async(userId)=>{
        try {
            console.log("Called getAllCartItems");
            const token = Cookies.get('token');
            console.log("Token ",token);
            console.log("User id :",userId);
            const response= await axios.get(URL+"/cart/get-cartitems/"+userId,{
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                withCredentials: true,
            });

            const responseData = response.data;
            console.log("response data in cartSlice :", response.data)
            console.log("Just response direct from cart slice: ",response);
            // console.log("Just response direct: ",response.payload);
            return responseData;
        } catch (error) {
            console.log("error ",error.response.status);
            console.log("error message ",error.response);
            return error.response.data;
        }
    }
)

export const addItemToCart = createAsyncThunk(
    'cart/addItemToCart',
    async(productForm)=>{
        try {
            console.log("Called addToCart");
            const token = Cookies.get('token');
            console.log("Token ",token);
            console.log("Product Form :",productForm);
            const response= await axios.post(URL+"/cart/add-item",productForm,{
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                withCredentials: true,
            });

            const responseData = response.data;
            console.log("response data in cartSlice addCart :", response.data)
            console.log("Just response direct from cartSlice addCart: ",response);
            // console.log("Just response direct: ",response.payload);
            return responseData;
        } catch (error) {
            console.log("error ",error.response.status);
            console.log("error message ",error.response);
            return error.response.data;
        }
    }
)

export const increaseQty = createAsyncThunk(
    'cart/increaseQty',
    async(productForm)=>{
        try {
            console.log("Called increase qty");
            const token = Cookies.get('token');
            console.log("Token ",token);
            console.log("Product Form :",productForm);
            const response= await axios.post(URL+"/cart/increase-qty",productForm,{
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                withCredentials: true,
            });

            const responseData = response.data;
            console.log("response data in cartSlice addCart :", response.data)
            console.log("Just response direct from cartSlice addCart: ",response);
            // console.log("Just response direct: ",response.payload);
            return responseData;
        } catch (error) {
            console.log("error ",error.response.status);
            console.log("error message ",error.response);
            return error.response.data;
        }
    }
)

export const decreaseQty = createAsyncThunk(
    'cart/decreaseQty',
    async(productForm)=>{
        try {
            console.log("Called decrease qty");
            const token = Cookies.get('token');
            console.log("Token ",token);
            console.log("Product Form :",productForm);
            const response= await axios.post(URL+"/cart/decrease-qty",productForm,{
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                withCredentials: true,
            });

            const responseData = response.data;
            console.log("response data in cartSlice decreaseItem :", response.data)
            console.log("Just response direct from cartSlice decreaseItem: ",response);
            // console.log("Just response direct: ",response.payload);
            return responseData;
        } catch (error) {
            console.log("error ",error.response.status);
            console.log("error message ",error.response);
            return error.response.data;
        }
    }
)

const initialState = {
    cartItems: [],
    cartPrice:0,
    status:"idle",
    error:null
}

export const cartSlice = createSlice({
    name: "cart",
    initialState,

    extraReducers: (builder) => {
        builder
            .addCase(getAllCartItems.pending, (state,action) => {
                state.status = 'loading';
            })
            .addCase(getAllCartItems.fulfilled, (state,action) => {
                state.status = 'succeeded';
                console.log("Cart action :",action);
                state.cartItems = action.payload.cartItemDto;
                state.cartPrice = action.payload.price;
                console.log("Cart Payload :",action.payload);
            })
            .addCase(getAllCartItems.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
            })
            .addCase(addItemToCart.pending, (state,action) => {
                state.status = 'loading';
            })
            .addCase(addItemToCart.fulfilled, (state,action) => {
                state.status = 'succeeded';
                // console.log("Cart action :",action);
                // state.cartItems = action.payload.cartItemDto;
                // state.cartPrice = action.payload.price;
                console.log("Cart Payload :",action.payload);
            })
            .addCase(addItemToCart.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
            })
            .addCase(increaseQty.pending, (state,action) => {
                state.status = 'loading';
            })
            .addCase(increaseQty.fulfilled, (state,action) => {
                state.status = 'succeeded';
                // console.log("Cart action :",action);
                state.cartItems = action.payload.cartItemDto;
                state.cartPrice = action.payload.price;
                console.log("Cart Payload :",action.payload);
            })
            .addCase(increaseQty.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
            })
            .addCase(decreaseQty.pending, (state,action) => {
                state.status = 'loading';
            })
            .addCase(decreaseQty.fulfilled, (state,action) => {
                state.status = 'succeeded';
                // console.log("Cart action :",action);
                state.cartItems = action.payload.cartItemDto;
                state.cartPrice = action.payload.price;
                console.log("Cart Payload :",action.payload);
            })
            .addCase(decreaseQty.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
            });
    },
})


// export const{productsLoad}= productSlice.actions
export default cartSlice.reducer;