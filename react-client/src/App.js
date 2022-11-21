import React from 'react';
import './App.css';
import Browser from './components/Browser';
import ServerManage from './components/ServerManage';

function App() {
  return (
    <div className="App">
      <Browser />
      <ServerManage />
    </div>
  );
}

export default App;
