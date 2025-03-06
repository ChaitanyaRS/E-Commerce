import React from 'react'
import OrderCard from '../components/OrderCard'

const Orders = () => {
  const orderData = [
    {description: 'This is a Dell Laptop.', image_link: 'src\\assets\\dell.jpg', quantity: 2, total_price: 50000 * 2, date: '2025-02-27'},
    {description: 'This is Santro Xing car.', image_link: 'src\\assets\\santro.jpg', quantity: 1, total_price: 1000 * 1, date: '2025-02-27'},
    {description: 'This is My TV.', image_link: 'src\\assets\\lgtv.jpg', quantity: 3, total_price: 2000 * 3, date: '2025-02-27'},
    {description: 'This is iPhone 15.', image_link: 'src\\assets\\iphone.jpg', quantity: 1, total_price: 120000 * 1, date: '2025-02-27'}
  ];

  return (
    <div style={{width: '80%',margin: 'auto'}}>
      {orderData.map(order => (<OrderCard data={order}/>))}
    </div>
  )
}

export default Orders
