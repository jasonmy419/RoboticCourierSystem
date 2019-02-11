import React from 'react';
import ReactDOM from 'react-dom';
import App from './components/App';
import ConfirmationPage from './components/App';

// it('renders without crashing', () => {
//   const div = document.createElement('div');
//   ReactDOM.render(<App />, div);
//   ReactDOM.unmountComponentAtNode(div);
// });

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<ConfirmationPage />, div);
  //ReactDOM.unmountComponentAtNode(div);
});
