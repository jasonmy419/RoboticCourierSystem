import React from 'react';
import {
    Form, Input, Select, Button, DatePicker, message, Card
} from 'antd';
import {API_ROOT, ORDER_NUM, USER_ID} from '../constants';
import { List, Avatar, Icon } from 'antd';
export class UserProfile extends React.Component{
    state = {
        userInfo: [

        ] }
    componentDidMount() {
        console.log(localStorage.getItem(USER_ID));
        fetch(`${API_ROOT}/profile`, {
            method: 'GET',
            // body: JSON.stringify({
            //     username: values.pickingUpAddress,
            //     password: values.deliveryAddress,
            // }),
            user_id: localStorage.getItem(USER_ID)
        }).then((response) => {
            if (response.ok) {
                return response.json();
            }
            throw new Error(response.statusText);
        })
            .then((data) => {
                console.log(data);
                // message.success('Sending Succeed!');
                this.setState({userInfo: data});
                //console.log(this.state.routes);
                // this.props.handleResponse(data);
                //this.props.history.push('/payment');
            })
            .catch((e) => {
                console.log(e);
                message.error('Getting User Information Failed.');
            });
    }

    render() {
        const userInfo = {
            user_id:"T1nypeanut",
            first_name: "Yu",
            last_name:"MA",
        }
        return(
            <div className="user-info">
                <h2>{`Welcome back, ${userInfo.first_name} `}</h2>
            </div>
        );
    }
}