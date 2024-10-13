import React, { useEffect, useState } from 'react';
import { getRankingsForContest } from './GetRankings'; // The service created above
import { useParams } from 'react-router-dom';
import Navbar from '../navbar/Navbar';
import './Rankings.css'


const Rankings = () => {
    const { contestId } = useParams();
    const [rankings, setRankings] = useState([]);

    useEffect(() => {
        const fetchRankings = async () => {
            const data = await getRankingsForContest(contestId);
            setRankings(data);
        };

        fetchRankings();
    }, [contestId]);

    return (
        <div>
            <Navbar handleLogout={null} />

            <div className='container mx-5'>
                <div className='ranking-heading my-4'>Rankings for Contest {contestId}</div>
                <div className=''>
                    <div className='d-flex mb-3'>
                        <div style={{ flexBasis: '10%' }} className="item-heading" >Rank</div>
                        <div style={{ flexBasis: '40%' }} className="item-heading" >Contestant Name</div>
                        <div style={{ flexBasis: '30%' }} className="item-heading" >Score</div>
                        <div style={{ flexBasis: '20%' }} className="item-heading" >Submissions</div>
                    </div>
                    {rankings.map((ranking, id) => (
                        <div key={id} className="ranking-elem shadow-sm mx-1" style={{ backgroundColor: ranking.id % 2 === 0 ? '#f0f0f0' : '#ffffff' }}>
                            <div  className="d-flex">
                                <div style={{ flexBasis: '10%' }} className="rankings-item">{id+1}</div>
                                <div style={{ flexBasis: '40%' }} className="rankings-item">{ranking.contestantName.substring(0, ranking.contestantName.indexOf("@"))}</div>
                                <div style={{ flexBasis: '30%' }} className="rankings-item">{ranking.score}</div>
                                <div style={{ flexBasis: '20%' }} className="rankings-item">{ranking.noOfSubmission}</div>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
};

export default Rankings;
