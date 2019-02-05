import React from 'react';
import { UserInformation } from './UserInformation';
import { CardInformation } from './CardInformation';

export class PaymentMain extends React.Component {
    render() {
        return (
            <div className="main">
                <UserInformation/>
                <CardInformation/>
            </div>
        );
    }
}