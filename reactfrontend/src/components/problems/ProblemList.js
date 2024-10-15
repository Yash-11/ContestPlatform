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
  const [loading, setLoading] = useState(true);
  const [problems, setProblems] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const query = useQuery();
  const page = (parseInt(query.get("page")) || 1);
  console.log(query.entries());

  const allParams = {};
  for (let [key, value] of query.entries()) {
    allParams[key] = value;
  }

  const fetchProblems = async () => {
    try {
      const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/problems`,
        {
          params: allParams
        });
      console.log(response);
      // const totalPages = response.data.totalPages;

      setProblems(response.data.content);
    } catch (error) {
      console.error('Error fetching doctors:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async () => {
    query.set('search', searchTerm);
    query.set('page', 1);
    if (searchTerm === "") query.delete('search');
    navigate(`?${query.toString()}`);
  };


  useEffect(() => {
    fetchProblems();
  }, [useLocation().search]);

  const handlePreviousPage = () => {
    query.set('page', page - 1);
    navigate(`?${query.toString()}`);
  }

  const handleNextPage = () => {
    query.set('page', page + 1);
    navigate(`?${query.toString()}`);
  };

  return (

    <div>
      <Navbar handleLogout={null} />

      <div className="container mt-3">
        <div className='d-flex align-items-center mb-3 mx-4'>

          <div className="problem-heading">Problems</div>
          <div className='ml-auto'>
            <input
              type="text"
              placeholder="Search by title"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
            <button className='search-icon' onClick={handleSearch}><i className="fa-solid fa-magnifying-glass"></i></button>
          </div>
        </div>

        <hr className="custom-hr mt-6 mb-4"></hr>
        <div>
          <ul>
            <div className="heading-elem">
              <div className=" d-flex  align-items-center">
                <div style={{ flexBasis: '10%' }} className=""><h5 className="">Id</h5></div>
                <div style={{ flexBasis: '60%' }} className=""><h5 className="">Title</h5></div>
                <div style={{ flexBasis: '30%' }} className=""><h5 className="">Difficulty</h5></div>
              </div>
            </div>
            </ul>
        </div>

        <div>
        <ul>
            {loading ? (
              <div className='d-flex justify-content-center'>
                <div className="loader"></div>
              </div>
            ) : (
              <div>
                {problems.map((problem, id) => (
                  <div key={problem.id}>
                    <div className="problem-elem shadow-sm" style={{ backgroundColor: id % 2 === 0 ? '#f0f0f0' : '#ffffff' }}>
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
              </div>
            )}
          </ul>
        </div>

        <div className='d-flex  justify-content-center my-4' >
          <div >
            <button onClick={handlePreviousPage} disabled={page === 1}>
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
