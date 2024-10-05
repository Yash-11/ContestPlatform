import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation, useNavigate, Link } from 'react-router-dom';
import './ContestList.css';
import useLogout from '../hooks/useLogout';
import Navbar from '../navbar/Navbar';

const ContestList = () => {
  const navigate = useNavigate();
  const [contests, setContests] = useState([]);
  

  useEffect(() => {
    axios
    .get(`${process.env.REACT_APP_API_BASE_URL}/api/contests`,
      {
        headers: {
          "Authorization": 'Bearer ' + localStorage.getItem('token')
        }
      })
    .then(response => {
      console.log(response.data);      
      setContests(response.data);
    })
    .catch(error => {
      navigate('/login');
      console.error('Error fetching doctors:', error);
    });
  }, []);


  const handleLogout = useLogout();

  return (

    <div>
      <Navbar handleLogout={handleLogout} />

      <div className="container mt-3">
        <h1 className="text-center mb-3 h2heading">Contests</h1>
        <hr className="custom-hr mt-3 mb-4"></hr>


        <div>
          <ul>
            {contests.map(contest => (
              <div className="contest-elem shadow-sm">
                <div className='d-flex align-items-center'>

                  <div style={{ flexBasis: '15%' }}>
                    <img 
                    className='contest-img'
                      src={require('../../assets/contestimg.png')}
                      alt="contestimg"
                      style={{ height: '50px' }}
                    />
                  </div>
                  <div style={{ flexBasis: '70%' }}>
                    <div>{contest.name}</div>
                    <div>{contest.startTime}</div>
                    {/* <a href={`/contest/${contest.id}`}>{contest.name}</a>
                    <p>{contest.startTime}</p> */}
                  </div>
                  <div >
                    <div className='enter-btn'>
                      <Link className="" to={`/contest/${contest.id}`}>Enter</Link>
                      {/* <a href={`/contest/${contest.id}`}>Enter</a> */} 
                    </div>
                  </div>
                </div>
              </div>
              // <li key={contest.id}>
              // </li>
            ))}
          </ul>
        </div>
      </div>

    </div>
  );
};

export default ContestList;
