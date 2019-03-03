import React from 'react';
import {
    Form, Input, Select, Button, DatePicker, message
} from 'antd';
import {API_ROOT, ORDER_NUM, USER_ID, PARCEL_ICON} from '../constants';
import { Link } from 'react-router-dom';
import { List, Avatar, Icon } from 'antd';
import {UserProfile} from "./UserProfile";

// const listData = [];
// for (let i = 0; i < 23; i++) {
//     listData.push({
//         type: "R",
//         href: 'http://ant.design',
//         title: `Order Number: ${i+1}`,
//         description: 'Your order was delivered on 2019, Feb 14th, 12:00pm',
//         fromAddr: 'Union Square,  San Francisco, CA',
//         toAddr: 'Palace Of Fine Arts,  San Francisco, CA',
//     });
// }


export class Orders extends React.Component{

    state={
        listData:[]
    }

    componentWillMount(){
        //console.log(listData);

        fetch(`${API_ROOT}/history?user_id=${localStorage.getItem(USER_ID)}`, {
            method: 'GET',
        }).then((response) => {
            if(response){
                return response.text();
            }
            throw new Error(response.statusText);
        }).then((data) => {
            console.log(data);
            return JSON.parse(data);
        }).then((json) => {
            console.log(json);
            this.setState({listData: json})
        }).catch((err) => {
            console.log(err);
            message.error('Error getting history');
        });
    }

    render() {
        return(
            <div>
            <UserProfile/>
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
                    dataSource={this.state.listData}
                    //footer={<div><b>ant design</b> footer part</div>}
                    renderItem={item => (
                        <List.Item
                            key={item.title}
                            //actions={[<IconText type="star-o" text="156" />, <IconText type="like-o" text="156" />, <IconText type="message" text="2" />]}
                            extra={<img width={170} alt="ship method"
                                        src={item.type === "D" ? "https://cdn1.iconfinder.com/data/icons/business-e-commerce-logistics-solid-set-1/91/Business_E-commerce__Logistics_15-512.png"
                                        : "https://cdn1.iconfinder.com/data/icons/robots-solid-2/48/59-512.png"} />}
                        >
                            <List.Item.Meta
                                avatar={<Avatar shape="square" src={PARCEL_ICON} />}
                                title={<a href={item.href}>{`Order Number: ${item.order_id}`}</a>}
                                description={item.end_time === "IN TRANSIT" ? "We are delivering your order" :
                                    `Your order was delivered on ${item.end_time}`}
                            />
                            <div className="addr-entry">
                                <div className="addr-entry-left">From:</div>
                                <div className="addr-entry-right">{item.start_address}</div>
                            </div>
                            <div className="addr-entry">
                                <div className="addr-entry-left">To:</div>
                                <div className="addr-entry-right">{item.end_address}</div>
                            </div>
                        </List.Item>
                    )}
                />
            </div>
            </div>
        );
    }
}