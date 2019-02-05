import React from 'react';
import logo from './logo.svg';

export class PaymentTopBar extends React.Component {
    render() {
        return (
            <header className="PaymentApp-header">
                <img src={logo} className="PaymentApp-logo" alt="logo" />
                <div className="PaymentApp-title">Payment</div>
            </header>
        );
    }
}