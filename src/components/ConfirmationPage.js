import React, { Component } from 'react';
import Steps, { Step } from 'rc-steps';
import { API_ROOT } from "../constants";
import 'rc-steps/assets/index.css';
import 'rc-steps/assets/iconfont.css';

export class ConfirmationPage extends Component {

    state = {
        step:1
    };

    componentWillMount() {
        console.log('mount');
        // fetch(`${API_ROOT}/tracking?orderID=${this.props.orderID}`, {
        //     method: 'GET'
        // }).then((response) => {
        //     if(response){
        //         return response.text();
        //     }
        //     throw new Error(response.statusText);
        // }).then((data) => {
        //     message.success('Registration Success');
        // }).catch((err) => {
        //     console.log(err);
        //     message.error('Registration Fail');
        // });
    }

    description = "这里可以添加描述";

    render() {
        console.log('render');
        return (
            <div>
                <h1>
                    Success! Your order have been placed
                </h1>
                <h2>
                    Tracking number: 123
                </h2>
                <Steps progressDot size="big" current={this.state.step}>
                    <Step title="Order Received" description={this.description} />
                    <Step title="Picked up" description={this.description} />
                    <Step title="Delivering" description={this.description} />
                    <Step title="Arrived" description={this.description} />
                </Steps>
            </div>
        );
    }
}