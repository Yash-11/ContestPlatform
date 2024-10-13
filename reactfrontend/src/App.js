// App.js
import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import Login from './components/account/LoginContestant';
import AuthRoute from './components/authroute/AuthRoute';
import RegisterContestant from './components/account/RegisterContestant'
import ProblemList from './components/problems/ProblemList';
import ProblemPage from './components/problems/ProblemPage';
import ContestList from './components/contests/ContestList';
import ContestPage from './components/contests/ContestPage';
import ContestProblem from './components/problems/ContestProblem';
import Rankings from './components/ranking/Rankings';

function App() {
  return (

<Router>
<div>
  <Routes>
    <Route path="/" element={<Navigate to="/problems" />} />
    <Route path="/login" element={<Login />} />
    <Route path="/register_contestant" element={<RegisterContestant />} />
    <Route path="/problems" element={<ProblemList />} />
    <Route path="/contests" element={<ContestList />} />
    <Route path="/problem/:problemId" element={<ProblemPage />} />
    <Route path="/contest/:contestId" element={<ContestPage />} />
    <Route path="/contest/:contestId/problem/:problemId" element={<ContestProblem />} />
    <Route path="/contest/:contestId/rankings" element={<Rankings />} />
  </Routes>
</div>
</Router>
  );
}

export default App;
