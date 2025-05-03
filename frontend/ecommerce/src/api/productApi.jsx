import { createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';
import React from 'react'
import { URL } from '../constants/constants';

// export const getAllProductsApi =async () =>createAsyncThunk(
//     'product/getAllProducts',
//     async(form)=>{
//         try {
//             console.log("SSS")
//             const response= await axios.get(URL+"/product/all-products",{
//                 withCredentials: true,
//             });
//
//             const responseData = {status : response.status, data: response.data};
//             console.log("response data in ProductAPI :", response.data)
//             console.log("Just response direct: ",response);
//             // console.log("Just response direct: ",response.payload);
//             return responseData;
//         } catch (error) {
//             console.log("error ",error.response.status);
//             console.log("error message ",error.response);
//             return {status: error.response.status,message:error.response.data};
//         }
//     }
// )

