import React from 'react';
import ReactDOM from 'react-dom';
import PaymentApp from './PaymentApp';

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<PaymentApp />, div);
  ReactDOM.unmountComponentAtNode(div);
});
