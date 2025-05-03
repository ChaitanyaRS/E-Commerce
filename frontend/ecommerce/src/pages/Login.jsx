import React, { useState } from 'react'
import { useForm, Controller } from "react-hook-form"
import {Box, Button, CircularProgress, Link, TextField} from "@mui/material"
import { loginApi } from '../api/userApi'
import './Utility.css'
import {useDispatch, useSelector} from 'react-redux'
import { useNavigate } from 'react-router-dom'

const Login = () => {
    const { control, handleSubmit, formState: { errors } } = useForm()
    // const loginUser = (data) => console.log(data)
    const [errorMsg, setErrorMsg] = useState("");
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const loginStatus = useSelector(state => state.auth.status)
    const [btnEnabled, setBtnEnabled] = useState(true);

    const loginUser = async (data) => {
        setBtnEnabled(false);
        console.log(data);

        const { email, password } = data;

        const formData = { email, password };
        // const { status, message } = await dispatch(loginApi(formData));
        const responseData = await dispatch(loginApi(formData));
        console.log(responseData.payload)
        const{status, loginObj} = responseData.payload;
        console.log("status: ",status);
        console.log("username: ",loginObj.username);

        if (status == 200) {
            setErrorMsg("");
            navigate('/');
            sessionStorage.setItem("username",loginObj.username)
            setBtnEnabled(true)
        } else {
            console.log(loginObj);
            setBtnEnabled(true)
            setErrorMsg(loginObj);
        }

    }
    if (loginStatus === "loading" || loginStatus === "pending") {
            return (
            <div className="loader-container">
                <center><CircularProgress /></center>
            </div>
        )
    }
    return (
        // <>


        <div>
            <center className='mt-10'><h2>Login</h2></center>
            <Box className="form-control">
                <form onSubmit={handleSubmit(loginUser)}>
                    <div className="flex flex-col" style={{ gap: '10px' }}>
                        <Controller
                            name='email'
                            control={control}
                            defaultValue=""
                            render={({ field }) => (
                                <TextField
                                    {...field}
                                    required
                                    id="outlined-required"
                                    label="Email Id"
                                    placeholder='John'
                                    className="w-100"
                                    error={!!errors.username}
                                    helperText={errors.username ? errors.username.message : ''}
                                />
                            )}
                        />
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
                                    className="w-100"
                                    type='password'
                                    error={!!errors.password}
                                    helperText={errors.password ? errors.password.message : ''}
                                />
                            )}
                        />
                    </div>
                    <div className="flex mt-10 flex-col align-center">
                        {errorMsg && <div className='error-msg' style={{ color: 'red' }}>{errorMsg}</div>}
                        <div><Button disabled={!btnEnabled} variant='contained' type='submit'>Login</Button></div>
                        <div>Don't have an account ? <Link href="/register" underline="always">{'Sign up'}</Link>.</div>
                    </div>
                </form>
            </Box>
        </div>
        // </>
    )
}

export default Login
