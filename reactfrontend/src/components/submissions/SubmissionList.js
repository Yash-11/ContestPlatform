import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './SubmissionList.css';
import { useParams, useNavigate, Link } from 'react-router-dom';
import CodeMirror from '@uiw/react-codemirror';
import { javascript } from '@codemirror/lang-javascript';
import Navbar from '../navbar/Navbar';

const headers = {
  headers: {
    "Authorization": 'Bearer ' + localStorage.getItem('token')
  }
};

function SubmissionList() {
  const navigate = useNavigate();
  const { problemId } = useParams();
  const [value, setValue] = React.useState("");
  const [loading, setLoading] = useState(true);
  const [submissions, setSubmissions] = useState([]);
  const [selectedSubmission, setSelectedSubmission] = useState(null);

  useEffect(() => {
    const getSubmissions = async () => {
      try {
        const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/submissions`, {
          headers: {
            "Authorization": 'Bearer ' + localStorage.getItem('token')
          },
          params: { problemId: problemId }
        });
        console.log(response.data);
        setSelectedSubmission(response.data[0]);
        setSubmissions(response.data);
      } catch (error) {
        console.error('Error fetching doctors:', error);
      } finally {
        setLoading(false);
      }
    }
    getSubmissions();
  }, [problemId])

  const handleSubmissionClick = (submission) => {
    console.log("clicked");

    setSelectedSubmission(submission); // Set the clicked submission as selected
    // setCode(submission.code); // Update the code in the editor based on the clicked submission
  };

  return (
    <div>
      <Navbar handleLogout={null} />

      <div className="continer mx-5  ">
        <div className='Submissions-title mb-3'>Submissions</div>


        <div className='content-wrapper'>

          <ul className='submission-section'>

            <div className="submission-elem-title">
              <div className=" d-flex  align-items-center">
                <div style={{ flexBasis: '70%' }} className=""><h5 className="">Status</h5></div>
                <div style={{ flexBasis: '30%' }} className=""><h5 className="">Language</h5></div>
              </div>
            </div>

            {loading ? (
              <div className='d-flex justify-content-center'>
                <div className="loader"></div>
              </div>
            ) : (
              submissions.length === 0 ? (
                <div className='d-flex align-items-center justify-content-center h-50'>
                  <div className='no-submissions'>No submissions</div>
                </div>
              ) : (
                <div>
                  {submissions.map((submission, id) => (
                    <div key={submission.id} className='submission-elm-btn' onClick={() => handleSubmissionClick(submission)}>
                      <div className="submission-elem shadow-sm" style={{ backgroundColor: id % 2 === 0 ? '#f0f0f0' : '#ffffff' }}>
                        <div className=" d-flex  align-items-center">
                          <div style={{ flexBasis: '70%', color: submission.status === 'SUCCESS' ? 'green' : 'red' }} className="">{submission.status}</div>
                          <div style={{ flexBasis: '30%' }} className="">{submission.language}</div>
                        </div>
                      </div>
                    </div>
                  ))}
                </div>
              )
            )}
          </ul>

          <div className='editor code-section'>

            <CodeMirror value={selectedSubmission ? selectedSubmission.code : ""} height="200px" extensions={[javascript({ jsx: true })]} editable={false} />
          </div>
        </div>


      </div>

    </div>
  );

}
export default SubmissionList;
