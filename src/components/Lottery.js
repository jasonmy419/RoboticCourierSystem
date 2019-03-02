import React, { Component } from 'react';
import {API_ROOT, USER_ID} from "../constants";
import {Button, message} from 'antd';
import md5 from "md5";

export class Lottery extends Component {

    state={
        discount: 0,

    }

    draw_lottery = () => {

        let random = Math.floor(Math.random() * (50 - 5)) + 5;
        this.setState({discount: random});
        message.success(`${random}% of discount will add to your next order!`);
        this.props.handleCouponDraw();
        console.log("coupon", JSON.stringify({
            user_id: localStorage.getItem(USER_ID),
            coupon: random,
        }))
        fetch(`${API_ROOT}/coupon`, {
            method: 'POST',
            body: JSON.stringify({
                user_id: localStorage.getItem(USER_ID),
                coupon: random,
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
            if(json.status === "OK"){
                console.log(json);
                this.props.handleSuccessfulLogin(json.user_id);
            }
        }).catch((err) => {
            console.log(err);
        });

    }


    render() {

        return (
            <div className="lottery-main">
                <h1> Congratulation! You win a chance to draw a coupon for your next order! </h1>
                <div className="lottery-button">
                    <div className="lottery-instruction">
                        <h2> Click the button to draw: </h2>
                    </div>
                    <Button type="primary" onClick={this.draw_lottery}>Click me!</Button>
                </div>
            </div>
        );
    }
}
