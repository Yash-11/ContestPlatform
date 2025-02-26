import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './ProblemPage.css';
import { useParams, useNavigate, Link } from 'react-router-dom';
import CodeMirror from '@uiw/react-codemirror';
import { javascript } from '@codemirror/lang-javascript';
import useLogout from '../hooks/useLogout';
import Navbar from '../navbar/Navbar';

const headers = {
  headers: {
    "Authorization": 'Bearer ' + localStorage.getItem('token')
  }
};

function ProblemPage() {
  const navigate = useNavigate();
  const { problemId } = useParams();
  const [value, setValue] = React.useState("class Main {\n    public static void main(String[] args) {\n      System.out.println(\"Hello World\");\n  }\n}");
  const [problem, setProblem] = useState({});
  const [runStatus, setRunStatus] = useState('');
  const [output, setOutput] = useState('');

  useEffect(() => {
    axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/problems/${problemId}`).then(response => {
      setProblem(response.data);
    });
  }, [problemId]);

  const onChange = React.useCallback((val, viewUpdate) => {
    console.log('val:', val);
    setValue(val);
  }, []);

  const handleSubmit = async () => {
    try {
      // Sending the code (value) to the backend
      const response = await axios.post(`${process.env.REACT_APP_API_BASE_URL}/api/submissions/submit`, {
        problemId: problemId,
        code: value,
        language: 'java'
      }, headers);

      const submissionId = response.data.id;
      setRunStatus('PENDING...');

      const interval = setInterval(async () => {
        try {
          const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/submissions/result/${submissionId}`, {
            headers: {
              "Authorization": 'Bearer ' + localStorage.getItem('token')
            }
          });
          if (response.data.status === 'PENDING') {
            setRunStatus('PENDING...');
            console.log('Still processing...');
          } else {
            setRunStatus(response.data.status); // Set status to completed
            setOutput(response.data.codeOutput);
            console.log('Result:', response.data);
            clearInterval(interval); // Stop polling once we get the result
          }
        } catch (error) {
          navigate('/login');
          console.error('Error checking submission result:', error);
          clearInterval(interval);
        }
      }, 5000);

      console.log('Response:', response.data);
    } catch (error) {
      navigate('/login');
      console.error('Error submitting code:', error);
    }
  };

  return (
    <div>
      <Navbar handleLogout={null} />

      <div className="page-continer">
        <div className='d-flex align-items-center'>
          <div className='title mb-3'>{problem.id}. {problem.title}</div>
          <Link className='ml-auto submission-link' to={`/problem/${problemId}/submissions`}>

            <div className='submission-btn'>Submissions</div>
          </Link>
        </div>


        <p>{problem.description}</p>

        <div className='editor'>

          <CodeMirror value={value} height="200px" extensions={[javascript({ jsx: true })]} onChange={onChange} />
          <div className="container mt-2" style={{ width: 200 }} >
            <button type="button" className="  submit-btn" onClick={handleSubmit}>Submit</button>
          </div>
        </div>

        <div>

          <div className='input-heading'>Input</div>
          <div className="input-box">
            <div className='mx-3'>
              {problem.inputFormat}
            </div>
          </div>
        </div>

        <div className='mt-2'>
          <div className='input-heading'>Expected Output</div>
          <div className="input-box">
            <div className='mx-3'>
              {problem.outputFormat}
            </div>
          </div>
        </div>

        {/* Show Run Status */}
        <div className='mt-2'>
          <h4 style={{ color: runStatus === 'SUCCESS' ? 'green' : runStatus === 'FAILED' ? 'red' : 'orange' }}>
            Status: {runStatus}
          </h4>
        </div>

        {/* Show Output */}
        <div className='mt-2 mb-3'>
          <div className='input-heading'>Output</div>
          <div className="input-box">
            <div className='mx-3'>
              {output}
            </div>
          </div>
        </div>
        {/* {output && (
          
        )} */}

      </div>

    </div>
  );

}
export default ProblemPage;
