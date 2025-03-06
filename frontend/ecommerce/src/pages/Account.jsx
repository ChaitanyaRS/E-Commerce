import { Button, FormControl, InputLabel, MenuItem, Select, TextField, Box, Link } from '@mui/material';
import React, { useState } from 'react'
import { Controller, useForm } from 'react-hook-form';
import './Utility.css';
// import { registersUser } from '../api/userApi';
import { useNavigate } from 'react-router-dom';

const Account = () => {
    const { handleSubmit, control, formState: { errors } } = useForm();
    const navigate = useNavigate();
    // const [registrationForm, setRegistrationForm] = useState({ username: '', password: '', email: '', phone_number: '', type: '' })

    const updateProfile = (data) => {
        console.log(data);

        const { firstname, lastname,email,password, phone_number,address,pincode} = data;
        const formData = { firstname, lastname,email,password, phone_number,address,pincode};
        console.log(formData);
        console.log(response);
        navigate('/');
    }

    const handleChange = (e) => {
        setSelectedValue(e.target.value);
    }

    return (
        <div>
             <center className='mt-10'><h2>Create Account</h2></center>
            <Box className="form-control row-flex">
                <form onSubmit={handleSubmit(updateProfile)}>
                    <div className="flex row">
                        <Controller
                            name='firstname'
                            control={control}
                            defaultValue=""

                            render={({ field }) => (
                                <TextField
                                    {...field}
                                    required
                                    className="w-50"
                                    id="outlined-required"
                                    label="First Name"
                                    placeholder='John'
                                    // className='w-100'
                                    error={!!errors.firstname}
                                    helperText={errors.firstname ? errors.firstname.message : ''}
                                />
                            )}
                        />
                        <Controller
                            name='lastname'
                            control={control}
                            defaultValue=""
                            render={({ field }) => (
                                <TextField
                                    {...field}
                                    required
                                    className="w-50"
                                    id="outlined-required"
                                    label="Last Name"
                                    placeholder='Wick'
                                    // className='w-100'
                                    error={!!errors.lastname}
                                    helperText={errors.lastname ? errors.lastname.message : ''}
                                />
                            )}
                        />
                    </div>
                    <div className="flex row">
                        <Controller
                            name='email'
                            control={control}
                            defaultValue=""

                            render={({ field }) => (
                                <TextField
                                    {...field}
                                    required
                                    className="w-50"
                                    id="outlined-required"
                                    label="Email"
                                    placeholder='johnwick@gmail.com'
                                    // className='w-100'
                                    error={!!errors.email}
                                    helperText={errors.email ? errors.email.message : ''}
                                />
                            )}
                        />
                        <Controller
                            name='phone_number'
                            control={control}
                            defaultValue=""
                            render={({ field }) => (
                                <TextField
                                    {...field}
                                    required
                                    className="w-50"
                                    id="outlined-required"
                                    label="Phone Number"
                                    placeholder='John'
                                    // className='w-100'
                                    type='number'
                                    error={!!errors.phone_number}
                                    helperText={errors.phone_number ? errors.phone_number.message : ''}
                                />
                            )}
                        />
                    </div>
                    <div className="flex row">
                        <Controller
                            name='address'
                            control={control}
                            defaultValue=""

                            render={({ field }) => (
                                <TextField
                                    {...field}
                                    required
                                    className="w-50"
                                    id="outlined-required"
                                    label="Address"
                                    placeholder='Hadapsar, Pune'
                                    // className='w-100'
                                    error={!!errors.address}
                                    helperText={errors.address ? errors.address.message : ''}
                                />
                            )}
                        />
                        <Controller
                            name='pincode'
                            control={control}
                            defaultValue=""
                            render={({ field }) => (
                                <TextField
                                    {...field}
                                    required
                                    id="outlined-required"
                                    label="Pincode"
                                    placeholder='411028'
                                    className='w-50'
                                    type='number'
                                    error={!!errors.pincode}
                                    helperText={errors.pincode ? errors.pincode.message : ''}
                                />
                            )}
                        />
                    </div>
                    <div className="row flex">
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
                    {/* <div className="flex mt-10"><Button variant='contained' sx={{ margin: "auto" }} type='submit'>Submit</Button></div> */}
                </form>
            </Box>
        </div>
    )
}

export default Account
