// import React from 'react'
import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Menu from '@mui/material/Menu';
import MenuIcon from '@mui/icons-material/Menu';
import Container from '@mui/material/Container';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import Tooltip from '@mui/material/Tooltip';
import MenuItem from '@mui/material/MenuItem';
import AdbIcon from '@mui/icons-material/Adb';
import { deepOrange } from '@mui/material/colors';
import Cookies from 'js-cookie';
import { useNavigate } from 'react-router-dom';

// const pages = [{name:'Home',link:'/home'}, {name:'Category',link:}, 'Cart'];
// const settings = ['Account', 'Orders', 'Logout'];
const firstLetter = "C"

const Navbar = () => {
  const navigate = useNavigate();
  const username = !!sessionStorage.getItem("username");

  const [anchorElNav, setAnchorElNav] = React.useState(null);
  const [anchorElUser, setAnchorElUser] = React.useState(null);

  const handleOpenNavMenu = (event) => {
    setAnchorElNav(event.currentTarget);
  };
  const handleOpenUserMenu = (event) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
    handleLogout();
  };

  const handleLogout = () => {
    Cookies.remove("token");
    sessionStorage.removeItem("username");
    navigate("/login");
  }

  return (
    <AppBar position="static">
      <Container maxWidth="xl">
        <Toolbar disableGutters>
          {/* <AdbIcon sx={{ display: { xs: 'none', md: 'flex' }, mr: 1 }} /> */}
          <Typography
            variant="h6"
            noWrap
            component="a"
            href="#app-bar-with-responsive-menu"
            sx={{
              mr: 2,
              display: { xs: 'none', md: 'flex' },
              fontFamily: 'monospace',
              fontWeight: 700,
              letterSpacing: '.3rem',
              color: 'inherit',
              textDecoration: 'none',
            }}
          >
           Ecommerce
          </Typography>

          <Box sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' } }}>
            <IconButton
              size="large"
              aria-label="account of current user"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              onClick={handleOpenNavMenu}
              color="inherit"
            >
              <MenuIcon />
            </IconButton>
            <Menu
              id="menu-appbar"
              anchorEl={anchorElNav}
              anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'left',
              }}
              keepMounted
              transformOrigin={{
                vertical: 'top',
                horizontal: 'left',
              }}
              open={Boolean(anchorElNav)}
              onClose={handleCloseNavMenu}
              sx={{ display: { xs: 'block', md: 'none' } }}
            >
                <MenuItem component='a' href='/' key='home' onClick={handleCloseNavMenu}>
                  <Typography sx={{ textAlign: 'center' }}>Home</Typography>
                </MenuItem>
                <MenuItem component='a' href='/' key='category' onClick={handleCloseNavMenu}>
                  <Typography sx={{ textAlign: 'center' }}>Category</Typography>
                </MenuItem>
                <MenuItem component='a' href='/cart' key='cart' onClick={handleCloseNavMenu}>
                  <Typography sx={{ textAlign: 'center' }}>Cart</Typography>
                </MenuItem>
            </Menu>
          </Box>
          <AdbIcon sx={{ display: { xs: 'flex', md: 'none' }, mr: 1 }} />
          <Typography
            variant="h5"
            noWrap
            component="a"
            href="#app-bar-with-responsive-menu"
            sx={{
              mr: 2,
              display: { xs: 'flex', md: 'none' },
              flexGrow: 1,
              fontFamily: 'monospace',
              fontWeight: 700,
              letterSpacing: '.3rem',
              color: 'inherit',
              textDecoration: 'none',
            }}
          >
            LOGO
          </Typography>
          <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' } }}>
        
              <Button
                key='home'
                onClick={handleCloseNavMenu}
                sx={{ my: 2, color: 'white', display: 'block' }}
                component='a' href='/'
              >
                Home
              </Button>
              <Button
                key='category'
                onClick={handleCloseNavMenu}
                sx={{ my: 2, color: 'white', display: 'block' }}
                component='a' href='/'
              >
                Category
              </Button>
              <Button
                key='cart'
                onClick={handleCloseNavMenu}
                sx={{ my: 2, color: 'white', display: 'block' }}
                component='a' href='/cart'
              >
                Cart
              </Button>
          </Box>
          <Box sx={{ flexGrow: 0 }}>
            <Tooltip title="Open settings">
              <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                <Avatar>{firstLetter}</Avatar>
              </IconButton>
            </Tooltip>
            <Menu
              sx={{ mt: '45px' }}
              id="menu-appbar"
              anchorEl={anchorElUser}
              anchorOrigin={{
                vertical: 'top',
                horizontal: 'right',
              }}
              keepMounted
              transformOrigin={{
                vertical: 'top',
                horizontal: 'right',
              }}
              open={Boolean(anchorElUser)}
              onClose={handleCloseUserMenu}
            >
                <MenuItem key='account' component='a' href='/account' onClick={handleCloseUserMenu}>
                  <Typography sx={{ textAlign: 'center' }}>Account</Typography>
                </MenuItem>
                <MenuItem key='orders' component='a' href='/orders' onClick={handleCloseUserMenu}>
                  <Typography sx={{ textAlign: 'center' }}>Orders</Typography>
                </MenuItem>
                {username &&
                <MenuItem key='logout' onClick={handleCloseUserMenu}>
                  <Typography sx={{ textAlign: 'center' }}>Logout</Typography>
                </MenuItem>}
            </Menu>
          </Box>
        </Toolbar>
      </Container>
    </AppBar>
  );
}

export default Navbar
