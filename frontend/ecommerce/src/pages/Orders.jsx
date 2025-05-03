import React, {useEffect} from 'react'
import OrderCard from '../components/OrderCard'
import {getAllProducts} from "../slice/productSlice.jsx";
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {getOrderItems} from "../slice/orderSlice.jsx";
import './Utility.css'
import {CircularProgress} from "@mui/material";

const Orders = () => {
  const navigate = useNavigate();
  const isAuthenticated = useSelector(state => state.auth.isAuthenticated)
  const dispatch = useDispatch();
  const{orders,status} = useSelector(state => state.order);
  const userId = sessionStorage.getItem("userId");

  useEffect(() => {
  if (!isAuthenticated) {
    navigate("/login");
  }else{
    fetchOrders();
    console.log("orders data in useEffect:",orders)
  }
},[isAuthenticated, navigate]);

const fetchOrders = async () =>{
 const orderdata=  await dispatch(getOrderItems(userId));
  console.log("Orders data in orders page. ",orderdata )
}


  // const orderData = [
  //   {description: 'This is a Dell Laptop.', image_link: 'src\\assets\\dell.jpg', quantity: 2, total_price: 50000 * 2, date: '2025-02-27'},
  //   {description: 'This is Santro Xing car.', image_link: 'src\\assets\\santro.jpg', quantity: 1, total_price: 1000 * 1, date: '2025-02-27'},
  //   {description: 'This is My TV.', image_link: 'src\\assets\\lgtv.jpg', quantity: 3, total_price: 2000 * 3, date: '2025-02-27'},
  //   {description: 'This is iPhone 15.', image_link: 'src\\assets\\iphone.jpg', quantity: 1, total_price: 120000 * 1, date: '2025-02-27'}
  // ];

  if(status === 'loading'){
    return (
        <div className="loader-container">
          <center><CircularProgress /></center>
        </div>
    )
  }

  return (
    <div style={{width: '80%',margin: 'auto'}}>
      {orders.map(order => (<OrderCard data={order}/>))}
    </div>
  )
}

export default Orders
