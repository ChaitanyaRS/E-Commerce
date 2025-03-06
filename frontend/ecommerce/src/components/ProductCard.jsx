import React from 'react'
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import CardActionArea from '@mui/material/CardActionArea';
import CardActions from '@mui/material/CardActions';

const ProductCard = ({data}) => {
// const [cou, setfirst] = useState();
// const data = [{pid: 4, description: 'This is a Dell Laptop.', image_link: 'https://upload.wikimedia.org/wikipedia/commons/7/79/Dell_Inspiron_14_5000_Series.jpg', price: 50000, product_name: 'Laptop', quantity: 5, c_id: 2, r_id: 5}, {pid: 5, description: 'This is Santro Xing car.', image_link: 'https://upload.wikimedia.org/wikipedia/commons/d/d5/Hyundai_Santro_Xing.jpg', price: 1000, product_name: 'Car', quantity: 3, c_id: 1, r_id: 5}, {pid: 6, description: 'This is My TV.', image_link: 'https://upload.wikimedia.org/wikipedia/commons/5/5f/Samsung_LED_television.jpg', price: 2000, product_name: 'TV', quantity: 13, c_id: 2, r_id: 4}, {pid: 7, description: 'This is iPhone 15.', image_link: 'https://upload.wikimedia.org/wikipedia/commons/7/7e/IPhone_15_Pro_3D.png', price: 120000, product_name: 'Mobile', quantity: 8, c_id: 2, r_id: 5}, {pid: 8, description: 'This is a Sony Headphone.', image_link: 'https://upload.wikimedia.org/wikipedia/commons/e/e4/Sony_MDR-1R_headphones.jpg', price: 15000, product_name: 'Electronics', quantity: 20, c_id: 2, r_id: 4}, {pid: 9, description: 'This is a Whirlpool Refrigerator.', image_link: 'https://upload.wikimedia.org/wikipedia/commons/d/d2/Whirlpool_Refrigerator_%28cropped%29.jpg', price: 30000, product_name: 'Appliances', quantity: 4, c_id: 2, r_id: 4}, {pid: 10, description: 'This is a Nikon DSLR Camera.', image_link: 'https://upload.wikimedia.org/wikipedia/commons/2/2d/Nikon_D3300_01.jpg', price: 45000, product_name: 'Camera', quantity: 7, c_id: 2, r_id: 5}, {pid: 11, description: 'This is a Samsung Smartwatch.', image_link: 'https://upload.wikimedia.org/wikipedia/commons/e/e3/Samsung_Gear_S2_Classic.jpg', price: 18000, product_name: 'Wearables', quantity: 15, c_id: 2, r_id: 4}, {pid: 12, description: 'This is a Bosch Washing Machine.', image_link: 'https://upload.wikimedia.org/wikipedia/commons/4/4e/Washing_machine_Bosch.jpg', price: 40000, product_name: 'Appliances', quantity: 6, c_id: 2, r_id: 3}, {pid: 13, description: 'This is a Honda Activa.', image_link: 'https://upload.wikimedia.org/wikipedia/commons/2/24/Honda_Activa.jpg', price: 75000, product_name: 'Two Wheeler', quantity: 10, c_id: 1, r_id: 5}];

const {pid, description, image_link, price, product_name, quantity, r_id} = data;
// const data = [{pid: 4, description: 'This is a Dell Laptop.', image_link: 'src\\assets\\dell.jpg', price: 50000, product_name: 'Laptop', quantity: 5, c_id: 2, r_id: 5}, {pid: 5, description: 'This is Santro Xing car.', image_link: 'src\\assets\\santro.jpg', price: 1000, product_name: 'Car', quantity: 3, c_id: 1, r_id: 5}, {pid: 6, description: 'This is My TV.', image_link: 'src\\assets\\lgtv.jpg', price: 2000, product_name: 'TV', quantity: 13, c_id: 2, r_id: 4}, {pid: 7, description: 'This is iPhone 15.', image_link: 'src\\assets\\iphone.jpg', price: 120000, product_name: 'Mobile', quantity: 8, c_id: 2, r_id: 5}];
    

  return (
    <div>
        <Card sx={{ maxWidth: 250 ,maxHeight:500,minWidth:250}}>
      <CardActionArea>
        <CardMedia
          sx={{objectFit:'fill'}}
          component="img"
          height="180"
          image={image_link}
          alt={product_name}
        />
        <CardContent>
          <Typography className='text-center' gutterBottom variant="h5" component="div">
            <div className="flex flex-col">
            <div>{product_name}</div>
            <div>{price} Rs</div>
            </div>
          </Typography>
        </CardContent>
      </CardActionArea>
      <CardActions>
        <div className='text-center w-100'>
        <Button  variant='contained' size="large" color="primary">
          Add to Cart
        </Button>
        </div>
      </CardActions>
    </Card>
    </div>
  )
}

export default ProductCard
