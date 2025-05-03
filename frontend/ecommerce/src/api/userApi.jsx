import { createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';
import React from 'react'
import { URL } from '../constants/constants';
import Cookies from "js-cookie";

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
            // console.log("Login response object :", response.data);
            const responseData = {status : response.status, loginObj: response.data};
            return responseData;
        } catch (error) {
            console.log("error ",error.response.status);
            console.log("error message ",error.response);
            return {status: error.response.status,message:error.response.data};
        }
    }
)

export const userInfoApi = createAsyncThunk(
    'auth/userInfoApi',
    async(userid)=>{

        try {
            const token = Cookies.get("token");

            console.log("Token in profile info ",token);
            const urlString= URL+"/user/account-info/"+userid;
            console.log("url string is ",urlString);
            const response= await axios.get(URL+"/user/account-info/"+userid,{
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                withCredentials: true,
            });
            console.log("Account Info :", response.data);
            const responseData = response.data;
            return responseData;
        } catch (error) {
            console.log("error ",error.response.status);
            console.log("error message ",error.response);
            return {status: error.response.status,message:error.response.data};
        }
    }
)


