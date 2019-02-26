import React from 'react';
import {API_ROOT} from "../constants";
import { Form, Icon, Input, Button, message,} from 'antd';
import { Link } from 'react-router-dom';
import md5 from "md5"

class NormalLoginForm extends React.Component {
  handleSubmit = (e) => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
    if (!err) {
      console.log('Received values of form: ', values);

      // //for test purpose, comment for final use
      // const response = '{"status": "OK", "user_id": "Jizhou"}';
      // const promise = new Promise((resolve, reject) => {
      //   resolve(response);
      // });

      // send request, uncomment for final use
      fetch(`${API_ROOT}/login`, {
        method: 'POST',
        credentials: "include",
        body: JSON.stringify({
          user_id: values.username,
          password: md5(values.username + md5(values.password))
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
          console.log("Login successfully")
          this.props.handleSuccessfulLogin(json.user_id);
        } else {
          message.error('User not found or wrong password');
        }
      }).catch((err) => {
        console.log(err);
        message.error('Failed to access to the website');
      });
    }
    });
  }

  render() {
    const { getFieldDecorator } = this.props.form;
    return (
        <Form onSubmit={this.handleSubmit} className="login-form">
          <Form.Item className=".login-form">
            {getFieldDecorator('username', {
              rules: [{ required: true, message: 'Please input your username!' }],
            })(
                <Input prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="Username" />
            )}
          </Form.Item>
          <Form.Item className=".login-form">
            {getFieldDecorator('password', {
              rules: [{ required: true, message: 'Please input your Password!' }],
            })(
                <Input prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />} type="password" placeholder="Password" />
            )}
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit" className="login-form-button">
              Log in
            </Button>
            Or <Link to="/register">register now!</Link>
          </Form.Item>
        </Form>
    );
  }
}

export const Login = Form.create({ name: 'normal_login' })(NormalLoginForm);

