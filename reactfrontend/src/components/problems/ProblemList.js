import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';
import './ProblemList.css';
import useLogout from '../hooks/useLogout';
import Navbar from '../navbar/Navbar';

const ProblemList = () => {
  const navigate = useNavigate();
  const [problems, setProblems] = useState([]);

  useEffect(() => {
    axios
      .get(`${process.env.REACT_APP_API_BASE_URL}/api/problems`,
        {
          headers: {
            "Authorization": 'Bearer ' + localStorage.getItem('token')
          }
        })
      .then(response => {
        setProblems(response.data);
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
        <h1 className="text-center mb-3 h2heading">Problems</h1>
        <hr className="custom-hr mt-3 mb-4"></hr>
        <div>
          <ul>
            <div className="heading-elem">
              <div className=" d-flex  align-items-center">
                <div style={{ flexBasis: '10%' }} className=""><h5 className="">Id</h5></div>
                <div style={{ flexBasis: '60%' }} className=""><h5 className="">Title</h5></div>
                <div style={{ flexBasis: '30%' }} className=""><h5 className="">Difficulty</h5></div>
              </div>
            </div>
            {problems.map(problem => (
              <div key={problem.id}>
                <div className="problem-elem shadow-sm" style={{ backgroundColor: problem.id % 2 === 0 ? '#f0f0f0' : '#ffffff' }}>
                  <div className=" d-flex  align-items-center">
                    <div style={{ flexBasis: '10%' }} className=""><h5 className="">{problem.id}.</h5></div>
                    <div style={{ flexBasis: '60%' }} className=""><h5 className=""><a href={`/problem/${problem.id}`} className="">
                      {problem.title}
                    </a></h5></div>
                    <div style={{ flexBasis: '30%' }} className=""><h5 className="">Easy</h5></div>
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

export default ProblemList;
