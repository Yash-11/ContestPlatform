import React, { useState } from 'react';
import axios from 'axios';
import useLogout from '../hooks/useLogout';

import { useNavigate, Link, Navigate } from 'react-router-dom';
import './RegisterContestant.css';
import Navbar from '../navbar/Navbar';

const RegisterContestant = () => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    const contestantData = {
      name: name,
      username: email,
      password: password
    };

    try {
      await axios.post(`${process.env.REACT_APP_API_BASE_URL}/register_contestant`, contestantData);
      alert('Registration successful!');
      navigate('/')
    } catch (error) {
      console.error('There was an error!', error);
      alert('Registration failed. Please try again.');
    }
  };

  const handleLogout = useLogout();

  return (
    <div>
      <Navbar handleLogout={handleLogout} />

      <h2 className='h2heading mt-4'>Register</h2>
      <hr className="custom-hr mt-4"></hr>

      <div className="register-container">
        <div className="login-box">
          {/* <h2>Patient Registration</h2> */}
          <form onSubmit={handleSubmit}>
            <div>
              <label >Name:</label>
              <input
                type="text"
                id="name"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
              />
            </div>
            <div>
              <label >Email:</label>
              <input
                type="email"
                id="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>
            <div>
              <label >Password:</label>
              <input
                type="password"
                id="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>
            <button type="submit" className="btn btn-primary">Register</button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default RegisterContestant;
