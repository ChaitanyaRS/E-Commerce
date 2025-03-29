import { useEffect, useState } from 'react'
import viteLogo from '/vite.svg'
import './App.css'
import Navbar from './components/Navbar'
import { Outlet, useNavigate } from 'react-router-dom'
import { useSelector } from 'react-redux'

function App() {
  const isAuthenticated = useSelector(authState => authState.auth.isAuthenticated);
  const state = useSelector(loginState => loginState.auth.state);
  const navigate = useNavigate();

  // useEffect(() => {
  //   if (!isAuthenticated) {
  //     navigate("/login");
  //   }
  // })


  return (
    <><Navbar/>
        <main>
          <Outlet/>
        </main>
    </>
  )
}

export default App
