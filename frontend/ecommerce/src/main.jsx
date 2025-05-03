import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import Home from './pages/Home.jsx'
import Login from './pages/Login.jsx'
import Register from './pages/Register.jsx'
import Cart from './pages/Cart.jsx'
import Orders from './pages/Orders.jsx'
import Account from './pages/Account.jsx'
import OrdersPlaced from './pages/OrdersPlaced.jsx'
import { Provider } from 'react-redux'
import { store } from './store/store.jsx'
import PlaceOrder from "./pages/PlaceOrder.jsx";

const router = createBrowserRouter([
  {
    path:"/",
    element:<App/>,
    children:[{
      path:"/",
      element:<Home/>
    },{
      path:"/login",
      element:<Login/>
    },{
      path:"/register",
      element:<Register/>
    },{
      path:"/cart",
      element:<Cart/>
    },{
      path:"/orders",
      element:<Orders/>
    },{
      path:"/account",
      element:<Account/>
    },{
      path:"/order-placed",
      element:<OrdersPlaced/>
    },{
      path:"/place-order",
      element:<PlaceOrder/>
    }]
  }
])


createRoot(document.getElementById('root')).render(
  // <StrictMode>
  <Provider store={store}>
    <RouterProvider router={router}/>
  </Provider>
  // </StrictMode>,
)
