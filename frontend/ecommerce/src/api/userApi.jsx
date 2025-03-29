import { createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';
import React from 'react'
import { URL } from '../constants/constants';

export const registerApi =async (form) =>{
    try{
        const response = await axios.post(URL+"/user/register",form,{
            withCredentials: true,
        });
        return {status: response.status,message:response.data};
    }catch(error){

        console.log("error ",error.response.status);
        console.log("error message ",error.response);
        return {status: error.response.status,message:error.response.data};
        
    }
}

export const loginApi = createAsyncThunk(
    'auth/loginUser',
    async(form)=>{
        try {
            const response= await axios.post(URL+"/user/login",form,{
                withCredentials: true,
            });

            const responseData = {status : response.status, username: response.data};
            return responseData;
        } catch (error) {
            console.log("error ",error.response.status);
            console.log("error message ",error.response);
            return {status: error.response.status,message:error.response.data};
        }
    }
)
