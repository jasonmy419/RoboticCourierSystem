import React from 'react';
import {
    Form, Input, Select, Button, DatePicker, message, Card
} from 'antd';
import {API_ROOT, ORDER_NUM, USER_ID} from '../constants';
import { Link } from 'react-router-dom';
import { List, Avatar, Icon } from 'antd';
export class UserProfile extends React.Component{



    componentDidMount() {
        fetch(`${API_ROOT}/profile`, {
            method: 'GET',
            // body: JSON.stringify({
            //     username: values.pickingUpAddress,
            //     password: values.deliveryAddress,
            // }),
            body: JSON.stringify({
                user_id: localStorage.getItem(USER_ID)
            }
            ),
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
            <Card className="order">
                <Avatar size={128} icon="user"/>
                <p>{`${userInfo.user_id}`}</p>
                <p>{`${userInfo.first_name} ${userInfo.last_name}`}</p>
            </Card>
        );
    }
}