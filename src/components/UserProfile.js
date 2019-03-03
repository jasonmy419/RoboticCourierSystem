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
    componentWillMount() {
        console.log(localStorage.getItem(USER_ID));
        fetch(`${API_ROOT}/profile?user_id=${ localStorage.getItem(USER_ID)}`, {
            method: 'GET',
            // body: JSON.stringify({
            //     username: values.pickingUpAddress,
            //     password: values.deliveryAddress,
            // }),
        }).then((response) => {
            if (response.ok) {
                console.log("success", response)
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
        // const userInfo = {
        //     user_id:"T1nypeanut",
        //     first_name: "Yu",
        //     last_name:"MA",
        // }
        return(
            <div className="user-info">
                {
                    this.state.userInfo == null ? <h2>{`Welcome back, ${this.state.userInfo.first_name} `}</h2> : null
                }
            </div>
        );
    }
}