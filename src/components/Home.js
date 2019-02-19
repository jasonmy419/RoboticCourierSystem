import React from 'react';
import { Map } from './Map';
import { OrderInfo } from "./OrderInfo"
import { Col } from 'antd';

export class Home extends React.Component{
    state = { response : ''}

    handleResponse = (responseValue) => {
        this.setState({response: responseValue});
        console.log(this.state.response);
    }

    render(){
        return(
            <div className="maps">
                <Col span={14}>
                   <Map response={this.state.response}/>
                </Col>
                <Col span={10}>
                    <OrderInfo handleResponse={this.handleResponse}/>
                </Col>
            </div>
        )
    }
}