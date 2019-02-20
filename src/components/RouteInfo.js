import React from 'react';
import { Radio } from 'antd';

export class RouteInfo extends React.Component {

    render() {
        const { duration, mode, price } = this.props.route;
        return (
            <Radio.Button value={price}>{mode} {duration}</Radio.Button>
        );
    }
}
