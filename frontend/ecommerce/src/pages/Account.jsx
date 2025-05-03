import {Button, FormControl, InputLabel, MenuItem, Select, TextField, Box, Link, CircularProgress} from '@mui/material';
import React, {useEffect, useState} from 'react'
import { Controller, useForm } from 'react-hook-form';
import './Utility.css';
// import { registersUser } from '../api/userApi';
import { useNavigate } from 'react-router-dom';
import {useDispatch, useSelector} from "react-redux";
import {userInfoApi} from '../api/userApi.jsx'

const Account = () => {
    const { handleSubmit, control, formState: { errors } } = useForm();
    const navigate = useNavigate();
    const userId = useSelector(state => state.auth.userId);
    const dispatch = useDispatch();
    const userInfo = useSelector(state => state.auth.user)
    const accountStatus = useSelector(state => state.auth.accountStatus)
    const isAuthenticated = useSelector(state => state.auth.isAuthenticated)
    const userData = { firstName : userInfo.firstName, lastName : userInfo.lastName ,email: userInfo.email ,password:userInfo.password, phone_number:userInfo.phoneNumber,address:userInfo.address,pincode:userInfo.pincode}


    useEffect(() => {
        if (!isAuthenticated) {
            navigate("/login");
        }else{
            fetchUserData();
        }

    },[userId])

    const fetchUserData = async () => {
        if(userId !== undefined){
            const data = await dispatch(userInfoApi(userId));
            console.log("User data in account page ",userInfo);
        }
        else{
            console.error("User is not logged in !!");
            navigate("/login");
        }
    }

    const updateProfile = (data) => {
        const { firstname, lastname,email,password, phone_number,address,pincode} = data;
        const formData = { firstname, lastname,email,password, phone_number,address,pincode};
        console.log("Profile Info: ",formData);
    }

    if(accountStatus === 'loading'){
        return (
            <div className="loader-container">
                <center><CircularProgress /></center>
            </div>
        )
    }
    return (
        <div>
             <center className='mt-10'><h2>Account Information</h2></center>
            <Box className="form-control row-flex">
                <form onSubmit={handleSubmit(updateProfile)}>
                    <div className="flex row row-gap">
                        <Controller
                            name='firstname'
                            control={control}

                            render={({ field }) => (
                                <TextField
                                    {...field}
                                    required
                                    className="w-50"
                                    id="outlined-required"
                                    label="First Name"
                                    defaultValue={userInfo.firstName}
                                    error={!!errors.firstname}
                                    helperText={errors.firstname ? errors.firstname.message : ''}
                                />
                            )}
                        />
                        <Controller
                            name='lastname'
                            control={control}
                            // defaultValue={userInfo.lastName}
                            render={({ field }) => (
                                <TextField
                                    {...field}
                                    required
                                    className="w-50"
                                    id="outlined-required"
                                    label="Last Name"
                                    defaultValue={userInfo.lastName}
                                    error={!!errors.lastname}
                                    helperText={errors.lastname ? errors.lastname.message : ''}
                                />
                            )}
                        />
                    </div>
                    <div className="flex row row-gap">
                        <Controller
                            name='email'
                            control={control}
                            // defaultValue=""

                            render={({ field }) => (
                                <TextField
                                    {...field}
                                    required
                                    className="w-50"
                                    id="outlined-required"
                                    label="Email"
                                    defaultValue={userInfo.email}
                                    error={!!errors.email}
                                    helperText={errors.email ? errors.email.message : ''}
                                />
                            )}
                        />
                        <Controller
                            name='phone_number'
                            control={control}
                            // defaultValue=""
                            render={({ field }) => (
                                <TextField
                                    {...field}
                                    required
                                    className="w-50"
                                    id="outlined-required"
                                    label="Phone Number"
                                    type='number'
                                    defaultValue = {userInfo.phoneNumber}
                                    error={!!errors.phone_number}
                                    helperText={errors.phone_number ? errors.phone_number.message : ''}
                                />
                            )}
                        />
                    </div>
                    <div className="flex row row-gap">
                        <Controller
                            name='address'
                            control={control}

                            render={({ field }) => (
                                <TextField
                                    {...field}
                                    required
                                    className="w-50"
                                    id="outlined-required"
                                    label="Address"
                                    defaultValue={userInfo.address}
                                    error={!!errors.address}
                                    helperText={errors.address ? errors.address.message : ''}
                                />
                            )}
                        />
                        <Controller
                            name='pincode'
                            control={control}
                            defaultValue={userInfo.pincode}
                            render={({ field }) => (
                                <TextField
                                    {...field}
                                    required
                                    id="outlined-required"
                                    label="Pincode"
                                    className='w-50'
                                    type='number'
                                    error={!!errors.pincode}
                                    helperText={errors.pincode ? errors.pincode.message : ''}
                                />
                            )}
                        />
                    </div>
                    <div className="row flex row-gap">
                        <Controller
                            name='password'
                            control={control}
                            defaultValue=""
                            render={({ field }) => (
                                <TextField
                                    {...field}
                                    required
                                    id="outlined-required"
                                    label="Password"
                                    placeholder='****'
                                    className='w-50'
                                    type='password'
                                    error={!!errors.password}
                                    helperText={errors.password ? errors.password.message : ''}
                                />
                            )}
                        />
                        <Controller
                            name='confirmPassword'
                            control={control}
                            defaultValue=""
                            render={({ field }) => (
                                <TextField
                                    {...field}
                                    required
                                    id="outlined-required"
                                    label="Confirm Password"
                                    placeholder='****'
                                    className='w-50'
                                    type='password'
                                    error={!!errors.confirmPassword}
                                    helperText={errors.confirmPassword ? errors.confirmPassword.message : ''}
                                />
                            )}
                        />
                    </div>
                    <div className="flex mt-10 flex-col align-center"><div><Button variant='contained' type='submit'>Save</Button></div>
                    </div>
                </form>
            </Box>
        </div>
    )
}

export default Account
