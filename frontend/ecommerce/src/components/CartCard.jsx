import React, {useEffect} from 'react'
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { TextField } from '@mui/material';
import {useDispatch, useSelector} from "react-redux";
import {decreaseQty, increaseQty} from "../slice/cartSlice.jsx";

const CartCard = ({data}) => {
    const dispatch = useDispatch();
    const userId = useSelector(authState => authState.auth.userId)
    const cartItems = useSelector(state => state.cart.cartItems.find(item => item.pid == data.pid));
    const{imageLink, description, quantity, price} = cartItems;


  const increaseQuantity = async (cartData) => {
      console.log("Cart Data ",cartData);
      const cartItem = {prodId:cartData.pid, userId:userId, quantity:1};
      await dispatch(increaseQty(cartItem));
  }

  const decreaseQuantity = async (cartData) => {
      console.log("Cart Data ",cartData);
      const cartItem = {prodId:cartData.pid, userId:userId, quantity:1};
      await dispatch(decreaseQty(cartItem));
  }

  return (
    <div className='m-auto'>
      <Card sx={{ maxWidth: '100%' }} className='flex w-100 mt-10'>
      <CardMedia
        component="img"
        alt="green iguana"
        height="140"
        sx={{width:'20%',objectFit:'contain',padding:'5px 0 5px 0'}}
        image={imageLink}
      />
      <CardContent sx={{width:'50%'}} className='content-center'>
        <Typography gutterBottom variant="h5" component="div" >
            {description}
        </Typography>
      </CardContent>
      <CardActions sx={{width:'32%',placeContent: 'center'}} className='flex '>
        <div className='flex' style={{width:'100%'}}>
            <Button variant='contained' size="small" sx={{width:'20%'}} onClick={() => decreaseQuantity(data)}>-</Button><TextField sx={{width:'40%',padding:'0'}} id="outlined-basic" variant="outlined" value={quantity}/>
            <Button variant='contained' size="small" onClick={() => increaseQuantity(data)}>+</Button>
        </div>
        <div style={{width:'50%',fontSize:'large'}} size="large" className='text-center'>{price*quantity} Rs</div>
      </CardActions>
    </Card>
    </div>
  )
}

export default CartCard
