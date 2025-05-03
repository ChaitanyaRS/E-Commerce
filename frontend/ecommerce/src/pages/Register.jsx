import { Button, FormControl, InputLabel, MenuItem, Select, TextField, Box, Link } from '@mui/material';
import React, { useState } from 'react'
import { Controller, useForm } from 'react-hook-form';
import './Utility.css';
import { useNavigate } from 'react-router-dom';
import { registerApi } from '../api/userApi';

const Register = () => {
    const { handleSubmit, control, formState: { errors } } = useForm();
    const navigate = useNavigate();
    const[errorMsg,setErrorMsg] = useState("");
    // const [registrationForm, setRegistrationForm] = useState({ username: '', password: '', email: '', phone_number: '', type: '' })

    const registerUser = async (data) => {
        console.log(data);

        const { firstName, lastName,email,password, confirmPassword, phoneNumber,address,pincode} = data;
        
        if(password === confirmPassword){
            const formData = { firstName, lastName,email,password, phoneNumber,address,pincode};
            const {status, message} = await registerApi(formData);
            console.log("Status ",status);
            console.log("message ",message);
            
            if(status == 200){
                console.log("response data :",message)
                setErrorMsg("");
                navigate('/login');
            }else{
                console.log(message);
                
                setErrorMsg(message);
            }
        }else{
                setErrorMsg("Confirm password does not match with password !!!");
        }
    }
    return (
        <div>
             <center className='mt-10'><h2>Create Account</h2></center>
            <Box className="form-control row-flex">
                <form onSubmit={handleSubmit(registerUser)}>
                    <div className="flex row"  sx={{gap:'10px'}}>
                        <Controller
                            name='firstName'
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
                            name='lastName'
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
                                    type='email'
                                    error={!!errors.email}
                                    helperText={errors.email ? errors.email.message : ''}
                                />
                            )}
                        />
                        <Controller
                            name='phoneNumber'
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
                    <div className="flex mt-10 flex-col align-center">
                        {errorMsg && <div className='error-msg' style={{color:'red'}}>{errorMsg}</div>}
                        <div><Button variant='contained' type='submit'>Register</Button></div>
                        <div>Already have an account ? <Link href="/login" underline="always">{'Sign in'}</Link>.</div>
                    </div>
                </form>
            </Box>
        </div>
    )
}

export default Register
