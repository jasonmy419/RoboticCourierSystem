import React from 'react';
import { OrderInfo } from './OrderInfo';
import { Map} from './Map';
import { Register } from "./Register"
import { Login } from "./Login"

export class Main extends React.Component {
    render() {
        return (
            <div className="main">
                <Register/>
            </div>
        );
    }
}