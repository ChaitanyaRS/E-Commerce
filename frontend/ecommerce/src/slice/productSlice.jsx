import {createAsyncThunk, createSlice} from '@reduxjs/toolkit'
import React from 'react'
// import {  getAllProductsApi} from '../api/productApi.jsx';
import { URL } from '../constants/constants';
import axios from "axios";
import Cookies from "js-cookie";

export const getAllProducts = createAsyncThunk(
    'product/getAllProducts',
    async()=>{
        try {
            console.log("Called getAllProducts");
            const token = Cookies.get('token');
            console.log("Token ",token);
            const response= await axios.get(URL+"/product/all-products",{
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                withCredentials: true,
            });

            const responseData = response.data;
            return responseData;
        } catch (error) {
            console.log("error ",error.response.status);
            console.log("error message ",error.response);
            return error.response.data;
        }
    }
)

const initialState = {
    products : [],
    status:"idle",
    error:null
}

export const productSlice = createSlice({
    name: "product",
    initialState,

    extraReducers: (builder) => {
        builder
            .addCase(getAllProducts.pending, (state,action) => {
                state.status = 'loading';
            })
            .addCase(getAllProducts.fulfilled, (state,action) => {
                state.status = 'succeeded';
                state.products = action.payload;
            })
            .addCase(getAllProducts.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
            });
    },
})


// export const{productsLoad}= productSlice.actions
export default productSlice.reducer;