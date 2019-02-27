import React, { Component } from 'react';
import {API_ROOT, MAP_API_KEY} from "../constants";
import {Col, Steps} from 'antd';
import {Map} from "./Map";

const Step = Steps.Step;
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
        //     setState()
        // }).catch((err) => {
        //     console.log(err);
        //     message.error('Registration Fail');
        // });
    }

    description0 = "Your order have been placed, wait for the next available robot";
    description1 = "We have sent our nearest robot to pick up the item";
    description2 = "Your item has been picked up, delivering to destination";
    description3 = "Your item has arrived the destination";

    render() {
        console.log('confirm', this.props.orderID);
        console.log("confirm response", this.props.response);
        return (
            <div className="Confirmation">
                <div className="text">
                    <h1>
                        Success! Your order have been placed
                    </h1>
                    <h2>
                        Tracking number: {this.props.orderID}
                    </h2>
                </div>

                <Steps current={this.state.step} className="steps">
                    <Step title="Order Received" description={this.description0} />
                    <Step title="Picking up" description={this.description1} />
                    <Step title="Delivering" description={this.description2} />
                    <Step title="Arrived" description={this.description3} />
                </Steps>
                <Map
                    googleMapURL={`https://maps.googleapis.com/maps/api/js?key=${MAP_API_KEY}&libraries=geometry,drawing,places`}
                    loadingElement={<div style={{ height: `100%` }} />}
                    containerElement={<div style={{ height: `900px` }} />}
                    mapElement={<div style={{ height: `100%` }} />}
                    response={this.props.response}
                />
            </div>
        );
    }
}