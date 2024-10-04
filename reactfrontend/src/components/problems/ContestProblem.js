import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, Link } from 'react-router-dom';
import CodeMirror from '@uiw/react-codemirror';
import { javascript } from '@codemirror/lang-javascript';
import useLogout from '../hooks/useLogout';
import Navbar from '../navbar/Navbar';

function ContestProblem() {
  const { contestId, problemId } = useParams();
  const [value, setValue] = React.useState("class Main {\
    public static void main(String[] args) {\
        System.out.println(\"Hello World\");\
    }\
}");
  const [problem, setProblem] = useState({});
  const [runStatus, setRunStatus] = useState(''); // State to track run status
  const [output, setOutput] = useState(''); // State to track output

    useEffect(() => {
    axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/problems/${problemId}`,{
        headers: {
          "Authorization": 'Bearer ' + localStorage.getItem('token')
        }
      }).then(response => {
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
        language: 'java',
        contestId: contestId
      },{
        headers: {
          "Authorization": 'Bearer ' + localStorage.getItem('token')
        }
      });

      const submissionId = response.data.id;

      const interval = setInterval(async () => {
        try {
          const response = await axios.get(`${process.env.REACT_APP_API_BASE_URL}/api/submissions/result/${submissionId}`, {
            headers: {
              "Authorization": 'Bearer ' + localStorage.getItem('token')
            }
          });
          if (response.data.status === 'PENDING') {
            setRunStatus('Processing...');
            console.log('Still processing...');
          } else {
            setRunStatus(response.data.status); // Set status to completed
            setOutput(response.data.codeOutput);
            console.log('Result:', response.data);
            clearInterval(interval); // Stop polling once we get the result
          }
        } catch (error) {
          console.error('Error checking submission result:', error);
          clearInterval(interval);
        }
      }, 5000);  // Poll every 3 seconds



      // Handle response from the backend
      console.log('Response:', response.data);
    } catch (error) {
      console.error('Error submitting code:', error);
    }
  };
  
  const handleLogout = useLogout();

  return (
    <div>
      <Navbar handleLogout={handleLogout} />

      <div className="page-continer">
        <h1>{problem.title}</h1>
        <hr className="custom-hr mt-3 mb-4"></hr>
        <h4>Description</h4>
        <p>{problem.description}</p>
        <hr className="custom-hr mt-3 mb-4"></hr>

        <CodeMirror value={value} height="200px" extensions={[javascript({ jsx: true })]} onChange={onChange} />
        {/* <button onClick={handleSubmit} style={{ marginTop: '10px' }}>
          Submit
        </button> */}
        <div className="container" style={{width:200}} >
        {/* <div class="container" style="width: 300px;"> */}
          <button type="button" className="btn btn-primary" onClick={handleSubmit}>Submit</button>
        </div>

        <h4>Input</h4>
        
        <p>{problem.inputFormat}</p>
        <h4>Output</h4>
        <p>{problem.outputFormat}</p>
        <p>{problem.constraints}</p>

        {/* Show Run Status */}
        <div style={{ marginTop: '20px' }}>
          <h4>Status: {runStatus}</h4>
        </div>

        {/* Show Output */}
        {output && (
          <div style={{ marginTop: '20px' }}>
            <h3>Output:</h3>
            <pre>{output}</pre>
          </div>
        )}

      </div>

    </div>
  );
}
export default ContestProblem;
