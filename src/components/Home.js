import React from 'react';
import { Map } from './Map';
import { OrderInfo } from "./OrderInfo"
import { Col } from 'antd';

export class Home extends React.Component{
    render(){
        return(
            <div className="maps">
                <Col span={14}>
                   <Map/>
                </Col>
                <Col span={10}>
                    <OrderInfo/>
                </Col>
            </div>
        )
    }
}