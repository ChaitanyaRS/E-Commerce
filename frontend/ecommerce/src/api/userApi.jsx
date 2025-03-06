import { createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';
import React from 'react'

export const registerApi =async (form) =>{
    try{
        const response = await axios.post("http://localhost:8080/user/register",form);
        return {status: response.status,message:response.data};
    }catch(error){
        console.log("error ",error.response.status);
        
        return {status: error.response.status,message:error.response.data};
        
    }
}

export const loginApi = createAsyncThunk(
    'auth/loginUser',
    async(form)=>{
        try {
            const response= await axios.post("http://localhost:8080/user/login",form,{
                withCredentials: true,
            });
            console.log("Response :", response);
        } catch (error) {
            
        }
    }
)
