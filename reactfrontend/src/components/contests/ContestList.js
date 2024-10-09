import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation, useNavigate, Link } from 'react-router-dom';
import './ContestList.css';
import useLogout from '../hooks/useLogout';
import Navbar from '../navbar/Navbar';

const ContestList = () => {
  const navigate = useNavigate();
  // const [contests, setContests] = useState([]);
  const [currentContests, setCurrentContests] = useState([]);
  const [pastContests, setPastContests] = useState([]);



  useEffect(() => {
    const token = localStorage.getItem('token');
    const headers = {
      "Authorization": `Bearer ${token}`
    };

    // Fetch past contests
    axios
      .get(`${process.env.REACT_APP_API_BASE_URL}/api/contests/past`, { headers })
      .then(response => {
        setPastContests(response.data);
      })
      .catch(error => {
        console.error('Error fetching past contests:', error);
        navigate('/login');
      });

    // Fetch current contests
    axios
      .get(`${process.env.REACT_APP_API_BASE_URL}/api/contests/current`, { headers })
      .then(response => {
        setCurrentContests(response.data);
      })
      .catch(error => {
        console.error('Error fetching current contests:', error);
        navigate('/login');
      });
  }, [navigate]);





  const handleLogout = useLogout();

  return (

    <div>
      <Navbar handleLogout={handleLogout} />

      <div className="container mt-3">
        <div className="mb-3 contest-heading mx-5">Contests</div>
        <hr className="custom-hr mt-3 mb-4"></hr>


        <div>
          <ul>
            {currentContests.map(contest => (
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
                  </div>
                  <div >
                    <div className='enter-btn'>
                      <Link className="" to={`/contest/${contest.id}`}>Enter</Link>
                    </div>
                  </div>
                </div>
              </div>
              // <li key={contest.id}>
              // </li>
            ))}
          </ul>
        </div>

        <div className="mb-3 contest-heading mx-5">Past Contests</div>
        <hr className="custom-hr mt-3 mb-4"></hr>
        <div>
          <ul>
            {pastContests.map(contest => (
              <div key={contest.id} className="contest-elem shadow-sm mb-2">
                <div className="d-flex align-items-center">
                  <div style={{ flexBasis: '15%' }}>
                    <img
                      className="contest-img"
                      src={require('../../assets/contestimg.png')}
                      alt="contestimg"
                      style={{ height: '50px' }}
                    />
                  </div>
                  <div style={{ flexBasis: '70%' }}>
                    <div>{contest.name}</div>
                    <div>{contest.startTime}</div>
                  </div>

                  <div >
                    <div className='enter-btn'>
                      <Link className="" to={`/contest/${contest.id}`}>Enter</Link>
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </ul>
        </div>
      </div>

    </div>
  );
};

export default ContestList;
