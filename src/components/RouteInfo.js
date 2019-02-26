import React from 'react';
import { Radio } from 'antd';

export class RouteInfo extends React.Component {

    getDuration = (time) => {
        const hour = parseInt(time / 3600);
        const minute = parseInt((time % 3600) / 60);
        const second = (time % 60);
        return hour == 0 ? minute + ":" + second : hour + ":" + minute + ":" + second;
    }

    render() {
        const { duration, mode, price } = this.props.route;
        const time = this.getDuration(duration);
        return (
            <Radio.Button className="radioButton" value={price}>{mode}, Duration: {time}</Radio.Button>
        );
    }
}
