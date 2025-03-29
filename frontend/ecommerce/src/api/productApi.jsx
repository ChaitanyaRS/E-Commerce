import { createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';
import React from 'react'
import { URL } from '../constants/constants';

export const getAllProductsApi =async () =>{
    try{
        const response = await axios.post(URL+"/product/all-products",{
            withCredentials: true,
        });
        console.log("response products : ",response);
        
    }catch(error){
        console.log("error ",error.response.status);
        console.log("error message ",error.response);
        
    }
}