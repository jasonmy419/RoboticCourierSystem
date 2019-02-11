import React, { Component } from 'react';
import { PaymentTopBar } from './PaymentTopBar';
import { PaymentMain } from './PaymentMain';

class PaymentApp extends Component {
  render() {
    return (
      <div className="PaymentApp">
        <PaymentTopBar/>
        <PaymentMain/>
      </div>
    );
  }
}

export default PaymentApp;
