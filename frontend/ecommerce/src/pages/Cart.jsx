import React, {useEffect} from 'react'
import "./Cart.css"
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import {CircularProgress, TextField} from '@mui/material';
import AddBoxIcon from '@mui/icons-material/AddBox';
import CartCard from '../components/CartCard';
import {useDispatch, useSelector} from "react-redux";
import {getAllCartItems} from "../slice/cartSlice.jsx";
import {useNavigate} from "react-router-dom";
import './Utility.css'
{/* <Icon baseClassName="fas" className="fa-plus-circle" sx={{ fontSize: 30 }} /> */}


const Cart = () => {
    const {cartItems, cartPrice} = useSelector(state => state.cart);
    const dispatch = useDispatch();
    const userId = useSelector(authState => authState.auth.userId)
    const navigate = useNavigate();
    const isAuthenticated = useSelector(state => state.auth.isAuthenticated)
    const cartLoadStatus = useSelector(state => state.cart.status)

    useEffect(() => {
        if (!isAuthenticated) {
            navigate("/login");
        }else{
            fetchCart();
        }
    },[isAuthenticated, navigate,userId]);


    const fetchCart = async () => {
        console.log(userId)
        await dispatch(getAllCartItems(userId));
        // const cartItems = response.payload;
        console.log("Cart Items in cart page :",cartItems);
    }

    const placeOrder = () =>{


        navigate('/place-order');
    }
    if(cartLoadStatus === 'loading'){
        return (
            <div className="loader-container">
                <center><CircularProgress /></center>
            </div>)
    }
    if(!cartItems )
        return (<div>Cart is Empty!!</div>)
    return (
        <div style={{width: '80%',margin: 'auto'}}>
            {cartItems.map(item => (<CartCard data={item}/>))}
            <div className="place-order"><p>Total Amount : {cartPrice} Rs</p><Button className='place-order-button' variant='contained' onClick={()=> placeOrder()}>Place Order</Button></div>
        </div>
    )
}

export default Cart
