import React from 'react'
import "./Cart.css"
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { TextField } from '@mui/material';
import AddBoxIcon from '@mui/icons-material/AddBox';
import CartCard from '../components/CartCard';
{/* <Icon baseClassName="fas" className="fa-plus-circle" sx={{ fontSize: 30 }} /> */}


const Cart = () => {
    const cartData = [{image_link: 'src\\assets\\dell.jpg', description: 'This is a Dell Laptop.', quantity: 5, price: 50000}, {image_link: 'src\\assets\\santro.jpg', description: 'This is Santro Xing car.', quantity: 3, price: 1000}, {image_link: 'src\\assets\\lgtv.jpg', description: 'This is My TV.', quantity: 13, price: 2000}, {image_link: 'src\\assets\\iphone.jpg', description: 'This is iPhone 15.', quantity: 8, price: 120000}]; //add api to get cummulative data.
    const totalAmount = 100000//Add APi for this

  return (
    <div style={{width: '80%',margin: 'auto'}}>
      {cartData.map(item => (<CartCard data={item}/>))}  
    <div className="place-order"><p>Total Amount : {totalAmount} Rs</p><Button className='place-order-button' variant='contained'>Place Order</Button></div>
    </div>
  )
}

export default Cart
