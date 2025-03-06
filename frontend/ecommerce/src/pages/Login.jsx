import React from 'react'
import { useForm, Controller } from "react-hook-form"
import { Box, Button, Link, TextField } from "@mui/material"
import './Utility.css'

const Login = () => {
    const { control, handleSubmit, formState: { errors } } = useForm()
    const loginUser = (data) => console.log(data)

    return (
        // <>
        <div>
            <center className='mt-10'><h2>Login</h2></center>
            <Box className="form-control">
                <form onSubmit={handleSubmit(loginUser)}>
                    <div className="flex flex-col"  style={{gap:'10px'}}>
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
                    <div className="flex mt-10 flex-col align-center"><div><Button variant='contained' type='submit'>Login</Button></div>
                        <div>Don't have an account ? <Link href="/register" underline="always">{'Sign up'}</Link>.</div>
                    </div>
                </form>
            </Box>
        </div>
        // </>
    )
}

export default Login
