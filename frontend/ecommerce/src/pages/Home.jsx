import React, { useEffect } from 'react'
import ProductCard from '../components/ProductCard'
import "./../pages/Utility.css"
import { useNavigate } from 'react-router-dom'
import { useDispatch, useSelector } from 'react-redux'
import { getAllProductsApi } from '../api/productApi'
import { useState } from 'react'

const Home = () => {

  const navigate = useNavigate();
  const isAuthenticated = useSelector(state => state.auth.isAuthenticated)
  const dispatch = useDispatch();
  const [products, setProducts] = useState()

  useEffect(() => {
    if (!isAuthenticated) {
      navigate("/login");
    }else{
      fetchProducts();
      console.log(products);
    }
  });

  const fetchProducts = async () =>{
    const products = await dispatch(getAllProductsApi)
    setProducts(products)
  }

  const data = [{ pid: 4, description: 'This is a Dell Laptop.', image_link: 'src\\assets\\dell.jpg', price: 50000, product_name: 'Laptop', quantity: 5, c_id: 2, r_id: 5 }, { pid: 5, description: 'This is Santro Xing car.', image_link: 'src\\assets\\santro.jpg', price: 1000, product_name: 'Car', quantity: 3, c_id: 1, r_id: 5 }, { pid: 6, description: 'This is My TV.', image_link: 'src\\assets\\lgtv.jpg', price: 2000, product_name: 'TV', quantity: 13, c_id: 2, r_id: 4 }, { pid: 7, description: 'This is iPhone 15.', image_link: 'src\\assets\\iphone.jpg', price: 120000, product_name: 'Mobile', quantity: 8, c_id: 2, r_id: 5 }, { pid: 8, description: 'This is a Sony Headphone.', image_link: 'src\\assets\\sony.jpg', price: 15000, product_name: 'Electronics', quantity: 20, c_id: 2, r_id: 4 }, { pid: 9, description: 'This is a Whirlpool Refrigerator.', image_link: 'src\\assets\\fridge.jpg', price: 30000, product_name: 'Appliances', quantity: 4, c_id: 2, r_id: 4 }, { pid: 10, description: 'This is a Nikon DSLR Camera.', image_link: 'src\\assets\\nikon.jpg', price: 45000, product_name: 'Camera', quantity: 7, c_id: 2, r_id: 5 }, { pid: 11, description: 'This is a Samsung Smartwatch.', image_link: 'src\\assets\\watch.jpg', price: 18000, product_name: 'Wearables', quantity: 15, c_id: 2, r_id: 4 }];

  return (
    <>
      <div className='mt-10' style={{ padding: 15 }}>
        <div className='m-auto flex' style={{ gap: 50, flexWrap: 'wrap' }}>
          {data.map(prod => (<ProductCard data={prod} />))}
        </div>
      </div>
    </>
  )
}

export default Home
