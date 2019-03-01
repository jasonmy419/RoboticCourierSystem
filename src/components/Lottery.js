import React, { Component } from 'react';
import {API_ROOT} from "../constants";
import {Button, message} from 'antd';

export class Lottery extends Component {

    state={
        discount: 0,

    }

    draw_lottery = () => {

        let random =Math.floor(Math.random() * (50 - 5)) + 5;
        this.setState({discount: random});
        message.success(`${random}% of discount will add to your next order!`);
        this.props.handleCouponDraw();
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
