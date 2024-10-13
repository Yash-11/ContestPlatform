import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useLocation } from 'react-router-dom';
import './ProblemList.css';
import Navbar from '../navbar/Navbar';

const useQuery = () => {
  return new URLSearchParams(useLocation().search);
};

const ProblemList = () => {
  const navigate = useNavigate();
  const [problems, setProblems] = useState([]);
  const query = useQuery();
  const page = (parseInt(query.get("page")) || 1);
  console.log(page);


  const size = 10;

  const fetchProblems = async (page, size) => {
    const pageid = page - 1;
    axios
      .get(`${process.env.REACT_APP_API_BASE_URL}/api/problems`,
        {
          headers: {
            "Authorization": 'Bearer ' + localStorage.getItem('token')
          },
          params: { page: pageid, size: size }
        })
      .then(response => {
        // const totalPages = response.data.
        console.log(response.data);
        const totalPages = response.data.totalPages;
        // if (page>totalPages) {
        //   navigate(`?page=${totalPages}`);
        // }

        setProblems(response.data.content);
      })
      .catch(error => {
        navigate('/login');
        console.error('Error fetching doctors:', error);
      });
  };


  useEffect(() => {
    fetchProblems(page, size);
  }, [page]);

  const handlePreviousPage = () => {
    const previousPage = page - 1;
    navigate(`?page=${previousPage}`);
  }

  const handleNextPage = () => {
    const nextPage = page + 1;
    navigate(`?page=${nextPage}`);
  };

  return (

    <div>
      <Navbar handleLogout={null} />

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
                    <div style={{ flexBasis: '60%' }} className=""><h5 className=""><a className='problem-elem-title' href={`/problem/${problem.id}`}>
                      {problem.title}
                    </a></h5></div>
                    <div style={{ flexBasis: '30%' }} className=""><h5 className="">Easy</h5></div>
                  </div>
                </div>
              </div>
            ))}
          </ul>
        </div>

        <div className='d-flex  justify-content-center my-4' >
          <div >
            <button onClick={handlePreviousPage} disabled={page === 0}>
              <i className="fa-solid fa-angle-left"></i>
            </button>
            <button onClick={handleNextPage}>
              <i className="fa-solid fa-angle-right"></i>
            </button>

          </div>
        </div>
      </div>

    </div>
  );
};

export default ProblemList;
