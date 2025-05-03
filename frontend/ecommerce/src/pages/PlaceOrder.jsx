import React, {useState} from "react";
import {Controller, useForm} from "react-hook-form";
import {useNavigate} from "react-router-dom";
import Box from "@mui/material/Box";
import {Button, Link, TextField} from "@mui/material";
import './Utility.css';
import {useDispatch, useSelector} from "react-redux";
import {placeOrderForUser} from "../slice/orderSlice.jsx";
import {v4 as uuidv4} from "uuid";
import {toast, ToastContainer} from "react-toastify";

const PlaceOrder = ({orderData}) => {
    const{handleSubmit, control, formState:{errors}} = useForm();
    const[errorMsg,setErrorMsg] = useState("");
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const {cartItems,cartPrice} = useSelector((state) => state.cart);
    const userId = sessionStorage.getItem("userId");

    const placeOrderFn = async (data) =>{
        const key = uuidv4()
        console.log("Key :",key);

        console.log("Place Order data :",data);
        console.log("Cart items in order ",cartItems)
        const orderItems = cartItems.map(item => ({
            prodId:item.pid,
            quantity:item.quantity,
            totalPrice:item.quantity*item.price,
            userId: userId,
        }))
        console.log("Order Items: ",orderItems);
        const order = {userId:userId,address:data.address,contactNo:data.contactNo,pincode:data.pincode, items:orderItems,totalPrice:cartPrice};
        console.log("Order data to send :",order)
        // OrderDto (Need to send)
        // private int userId = session storage;
        // private String address = data form;
        // private long contactNo = data form ;
        // private int pincode = data from;
        // private List<OrderItem> items = cartItems;
        // private double totalPrice cartItems;

        // private int prodId;
        // private int quantity;
        // private double totalPrice;
        // private int userId;

        //data(from the form.)
        // address:"hadapsar"
        // contactNo:"123131"
        // name:"Chaitanya"
        // paymentMode:"Cash On Delivery"

        // cartItem
        // private List<CartItemDto> cartItemDto;
        // private double price;

        // CartItemDto
        // private int pId;
        // private String productName;
        // private String description;
        // private double price;
        // private int quantity;
        // private String category;
        // private String imageLink;
        // private int ratings;


        const response = await dispatch(placeOrderForUser([order,key]));
        console.log("response in place order " ,response)
        if(response.payload.status === 200)
            navigate('/orders')
        else
            toast.error("Something went wrong !!")
    }
    return (
        <div>
            <Box>
                <form onSubmit={handleSubmit(placeOrderFn)} style={{width: 'fit-content',margin: 'auto'  }}>
                    <div>
                        <Controller
                            name="name"
                            control={control}
                            defaultValue={sessionStorage.getItem("username")}
                            render={({field})=>(
                                <TextField
                                    {...field}
                                    required
                                    label="Name"
                                    error={!!errors.name}
                                    helperText={errors.name ? errors.name.message : ''}
                                    // className="w-100"
                                />
                            )}
                        />
                    </div>
                    <div>
                        <Controller
                            name="address"
                            control={control}

                            render={({field})=>(
                                <TextField
                                    {...field}
                                    required
                                    label="Address"
                                    placeholder="Hadapsar"
                                    error={!!errors.address}
                                    helperText={errors.address ? errors.address.message : ''}
                                />
                            )}
                        />
                    </div>
                    <div>
                        <Controller
                            name="contactNo"
                            control={control}

                            render={({field})=>(
                                <TextField
                                    {...field}
                                    required
                                    type="number"
                                    label="Contact Number"
                                    placeholder="123456789"
                                    error={!!errors.contactNo}
                                    helperText={errors.contactNo ? errors.contactNo.message : ''}
                                />
                            )}
                        />
                    </div>
                    <div>
                        <Controller
                            name="pincode"
                            control={control}

                            render={({field})=>(
                                <TextField
                                    {...field}
                                    required
                                    type="number"
                                    label="Pin Code"
                                    placeholder="411020"
                                    error={!!errors.pincode}
                                    helperText={errors.pincode ? errors.pincode.message : ''}
                                />
                            )}
                        />
                    </div>
                    <div>
                        <Controller
                            name="paymentMode"
                            control={control}
                            defaultValue="Cash On Delivery"

                            render={({field})=>(
                                <TextField
                                    {...field}
                                    required
                                    disabled
                                    label="Payment Mode"
                                    error={!!errors.paymentMode}
                                    helperText={errors.paymentMode ? errors.paymentMode.message : ''}
                                />
                            )}
                        />
                    </div>
                    <div className="flex mt-10 flex-col align-center">
                        {errorMsg && <div className='error-msg' style={{color:'red'}}>{errorMsg}</div>}
                        <div><Button variant='contained' type='submit'>Place Order</Button></div>
                    </div>
                </form>
            </Box>
            <ToastContainer />
        </div>

    )
}

export default PlaceOrder
