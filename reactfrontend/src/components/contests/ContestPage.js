import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, Link } from 'react-router-dom';
import useLogout from '../hooks/useLogout';
import Navbar from '../navbar/Navbar';

function ContestPage() {
    const { contestId } = useParams();
    const [problems, setProblems] = useState([]);
    const [contest, setContest] = useState({});

    useEffect(() => {
        axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/contests/${contestId}`,
            {
                headers: {
                  "Authorization": 'Bearer ' + localStorage.getItem('token')
                }
              }
        ).then(response => {
            setContest(response.data);
            setProblems(response.data.problems)
            console.log(response.data);

        });
    }, [contestId]);

    const handleLogout = useLogout();

    return (
        <div>
            <Navbar handleLogout={handleLogout} />
            <div className="page-continer">
                <h1 className="mt-2">{contest.name}</h1>
                <hr className="custom-hr mt-3 mb-4"></hr>
                <p>{contest.description}</p>
                <hr className="custom-hr mt-3 mb-4"></hr>
            <div>
                <ul>
                    {problems.map(problem => (
                        <div key={problem.id}>
                        <div className="problem-elem shadow-sm">
                          <div className=" d-flex  align-items-center">
                            <div style={{ flexBasis: '10%' }} className=""><h5 className="">{problem.id}.</h5></div>
                            <div style={{ flexBasis: '60%' }} className=""><h5 className=""><a href={`/contest/${contestId}/problem/${problem.id}`} className="">
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
}

export default ContestPage;