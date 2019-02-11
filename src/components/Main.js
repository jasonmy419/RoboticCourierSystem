import React from 'react';
import { OrderInfo } from './OrderInfo';
import { Map} from './Map';
export class Main extends React.Component {
    render() {
        return (
            <div className="main">
                <OrderInfo/>
                <Map/>
            </div>
        );
    }
}