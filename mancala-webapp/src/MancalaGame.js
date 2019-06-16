import React from 'react';
import logo from './logo.svg';
import './App.css';

function MancalaGame() {
  return (
    <div className="MancalaGame">
      <header className="MancalaGame-header">
        <img src={logo} className="MancalaGame-logo" alt="logo" />
        <p>
          Edit <code>src/MancalaGame.js</code> and save to reload.
        </p>
        <a
          className="MancalaGame-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default MancalaGame;
