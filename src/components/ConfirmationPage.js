import React, { Component } from 'react';
import {API_ROOT, MAP_API_KEY, USER_ID} from "../constants";
import {Col, message, Steps} from 'antd';
import {Map} from "./Map";
import {Lottery} from "./Lottery"

const Step = Steps.Step;
export class ConfirmationPage extends Component {

    state = {
        step:1
    };

    componentWillMount() {
        console.log('mount',JSON.stringify({
            order_id: this.props.orderID
        }));
        fetch(`${API_ROOT}/tracking`, {
            method: 'POST',
            body: JSON.stringify({
                order_id: this.props.orderID
            })
        }).then((response) => {
            if(response){
                return response.text();
            }
            throw new Error(response.statusText);
        }).then((data) => {
            console.log(data);
            return JSON.parse(data);
        }).then((json) => {
            console.log(json["Delivery"]);
            //TODO set step based on response

            // if (json["Delivery"] === "IN TRANSIT") {
            //     this.setState({step:1})
            // } else {
            //     this.setState({step:2})
            // }

        }).catch((err) => {
            console.log(err);
            message.error('Error getting order status');
        });
    }

    description0 = "Your order have been placed, our robot will soon pick up your item";
    description1 = "We are delivering your order";
    description2 = "Your item has arrived the destination";
    componentWillUnmount() {
        this.props.handlerIsRouteChosen(false);
        this.props.handlerIsPaymentSucceed(false);
    }

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
                    <Step title="Delivering" description={this.description1} />
                    <Step title="Arrived" description={this.description2} />
                </Steps>
                {this.props.coupon ? <Lottery handleCouponDraw = {this.props.handleCouponDraw}/> : null}
                <Map
                    googleMapURL={`https://maps.googleapis.com/maps/api/js?key=${MAP_API_KEY}&libraries=geometry,drawing,places`}
                    loadingElement={<div style={{ height: `100%` }} />}
                    containerElement={<div style={{ height: `600px` }} />}
                    mapElement={<div style={{ height: `100%` }} />}
                    response={this.props.response}
                />
            </div>
        );
    }
}