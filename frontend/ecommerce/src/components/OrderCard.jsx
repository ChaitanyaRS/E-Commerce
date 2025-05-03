import React from 'react'
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { TextField } from '@mui/material';
import { format } from 'date-fns';

const OrderCart = ({data}) => {

  // {pid: 4, description: 'This is a Dell Laptop.', imageLink: 'src\\assets\\dell.jpg', price: 50000, product_name: 'Laptop', quantity: 5, c_id: 2, r_id: 5}
//add api to get this cummilitive data.
  return (
    <div className='m-auto'>
      <Card sx={{ maxWidth: '100%' }} className='flex w-100 mt-10'>
      <CardMedia 
        component="img"
        alt="green iguana"
        height="140"
        sx={{width:'20%',objectFit:'contain',padding:'5px 0 5px 0'}}
        image={data.imageLink}
      />
      <CardContent sx={{width:'50%'}} className='content-center'>
        <Typography gutterBottom variant="h5" component="div" > 
        {data.productName} x {data.quantity}
        </Typography>
      </CardContent> 
      <CardActions sx={{width:'32%',placeContent: 'center'}} className='flex '>
        <div style={{width:'50%',fontSize:'large'}} size="large" className='text-center'>{data.price} Rs</div>
        <div style={{width:'50%',fontSize:'large'}} size="large" className='text-center'>{format(new Date(data.timestamp * 1000), 'yyyy-MM-dd')}</div>
      </CardActions>
    </Card>
    </div>
  )
}

export default OrderCart
