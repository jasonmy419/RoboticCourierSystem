import React from 'react';
import {
    Form, Input, Select, Button, DatePicker, message
} from 'antd';
import { API_ROOT, ORDER_NUM } from '../constants';
import { Link } from 'react-router-dom';
import { List, Avatar, Icon } from 'antd';

const listData = [];
for (let i = 0; i < 23; i++) {
    listData.push({
        href: 'http://ant.design',
        title: `Order Number: ${i}`,
        avatar: 'https://cdn0.iconfinder.com/data/icons/octicons/1024/package-512.png',
        description: 'Your order was delivered on 2019, Feb 14th, 12:00pm',
        fromAddr: 'Union Square,  San Francisco, CA',
        toAddr: 'Palace Of Fine Arts,  San Francisco, CA',
        content: 'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
    });
}

export class Orders extends React.Component{
    render() {
        return(
            <div className="OrderList">
                <div>
                    <h2 className="history_title">Order History</h2>
                </div>
                <List
                    itemLayout="vertical"
                    size="large"
                    pagination={{
                        onChange: (page) => {
                            console.log(page);
                        },
                        pageSize: 3,
                    }}
                    dataSource={listData}
                    //footer={<div><b>ant design</b> footer part</div>}
                    renderItem={item => (
                        <List.Item
                            key={item.title}
                            //actions={[<IconText type="star-o" text="156" />, <IconText type="like-o" text="156" />, <IconText type="message" text="2" />]}
                            extra={<img width={170} alt="ship method" src="https://cdn1.iconfinder.com/data/icons/business-e-commerce-logistics-solid-set-1/91/Business_E-commerce__Logistics_15-512.png" />}
                        >
                            <List.Item.Meta
                                avatar={<Avatar shape="square" src={item.avatar} />}
                                title={<a href={item.href}>{item.title}</a>}
                                description={item.description}
                            />
                            <div className="addr-entry">
                                <div className="addr-entry-left">From:</div>
                                <div className="addr-entry-right">{item.fromAddr}</div>
                            </div>
                            <div className="addr-entry">
                                <div className="addr-entry-left">To:</div>
                                <div className="addr-entry-right">{item.toAddr}</div>
                            </div>
                        </List.Item>
                    )}
                />
            </div>
        );
    }
}