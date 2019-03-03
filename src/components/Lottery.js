import React, { Component } from 'react';
import {API_ROOT, USER_ID} from "../constants";
import {Button, message} from 'antd';
import md5 from "md5";

export class Lottery extends Component {

    state={
        discount: 5,

    }

    handleClick = () => {

        var random_boolean = Math.random() >= 0.5;

        if (random_boolean) {
            this.setState(prev => ({discount: prev.discount + 5}));
        } else {
            fetch(`${API_ROOT}/coupon`, {
                method: 'POST',
                body: JSON.stringify({
                    user_id: localStorage.getItem(USER_ID),
                    coupon: this.state.discount
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
                if(json["lottery:"] === "success") {
                    console.log(json);
                    message.success(`${this.state.discount}% of discount will add to your next order!`);
                    this.props.handleCouponDraw();
                }})

        }
        // console.log("coupon", JSON.stringify({
        //     user_id: localStorage.getItem(USER_ID),
        //     coupon: random,
        // }))

    }


    render() {

        return (
            <div className="lottery-main">
                <h1> Congratulation! You win a chance to draw a coupon for your next order! </h1>
                <div className="lottery-button">
                    <div className="lottery-instruction">
                        <h2> Keep clicking to draw: {this.state.discount}%</h2>
                    </div>

                    <Button type="primary" onClick={this.handleClick}>Click me!</Button>

                </div>
            </div>
        );
    }
}
