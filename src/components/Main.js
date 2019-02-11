import React from 'react';
import { OrderInfo } from './OrderInfo';
import { Map} from './Map';
import { Register} from "./Register"

export class Main extends React.Component {
    render() {
        return (
            <div className="main">
                <Register/>
            </div>
        );
    }
}