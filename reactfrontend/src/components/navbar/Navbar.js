import React from 'react';
import { Link } from 'react-router-dom';
import useLogout from '../hooks/useLogout';
import './Navbar.css';

const Navbar = ({ handleLogout_ }) => {
  var user;
  if (localStorage.getItem('token')) user = 1;
  else user = null;
  const handleLogout = useLogout();
  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light container-fluid">
      <Link className="navbar-brand" to="/">
        <img
          src={require('../../assets/logo.png')}
          alt="AlgoArena Logo"
          style={{ height: '50px' }}
        />
      </Link>

      <a className='nv-link mx-2' href={'/problems'}>
        <div className=''>
          Problems
        </div>
      </a>


      <a className='nv-link mx-2' href={'/contests'}>
        <div className=''>
          Contest
        </div>
      </a>

      <div className="collapse navbar-collapse">
        <ul className="navbar-nav ml-auto">
          {!user ? (
            <>
              <li className="nav-item">
                <Link className="nav-link" to="/login">
                  Login
                </Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/register_contestant">
                  Register
                </Link>
              </li>
            </>
          ) : (
            <li className="nav-item">
              {/* <button className="logout-btn nav-link" onClick={handleLogout}>
                Logout
              </button> */}
              <button type="button" className="logout-btn" onClick={handleLogout}>Logout</button>
            </li>
          )}
        </ul>
      </div>
    </nav>
  );
};

export default Navbar;
