import React from 'react';
import {
    Form, Input, Cascader, Select, Row, Col, Button,
} from 'antd';

const { Option } = Select;


const residences = [{
    value: 'United State',
    label: 'United State',
    children: [{
        value: 'San Francisco',
        label: 'San Francisco',
    }],
}];

class UserInformationForm extends React.Component {
    state = {
        confirmDirty: false,
        autoCompleteResult: [],
    };

    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFieldsAndScroll((err, values) => {
            if (!err) {
                console.log('Received values of form: ', values);
            }
        });
    }

    handleConfirmBlur = (e) => {
        const value = e.target.value;
        this.setState({ confirmDirty: this.state.confirmDirty || !!value });
    }



    render() {
        const { getFieldDecorator } = this.props.form;

        const formItemLayout = {
            labelCol: {
                xs: { span: 24 },
                sm: { span: 8 },
            },
            wrapperCol: {
                xs: { span: 24 },
                sm: { span: 16 },
            },
        };

        const prefixSelector = getFieldDecorator('prefix', {
            initialValue: '1',
        })(
            <Select style={{ width: 70 }}>
                <Option value="1">+1</Option>
                <Option value="86">+86</Option>
            </Select>
        );



        return (
            <Form onSubmit={this.handleSubmit} className="userinformation">

                <Form.Item
                    {...formItemLayout}
                    label="Last Name"
                >
                    {getFieldDecorator('lastname', {
                        rules: [{
                            required: true, message: 'Please input your lastname!',
                        }],
                    })(
                        <Input type="password" />
                    )}
                </Form.Item>
                <Form.Item
                    {...formItemLayout}
                    label="First Name"
                >
                    {getFieldDecorator('confirm', {
                        rules: [{
                            required: true, message: 'Please input your firstname!',
                        }],
                    })(
                        <Input type="firstname" onBlur={this.handleConfirmBlur} />
                    )}
                </Form.Item>
                <Form.Item
                    {...formItemLayout}
                    label="E-mail"
                >
                    {getFieldDecorator('email', {
                        rules: [{
                            type: 'email', message: 'The input is not valid E-mail!',
                        }, {
                            required: true, message: 'Please input your E-mail!',
                        }],
                    })(
                        <Input />
                    )}
                </Form.Item>

                <Form.Item
                    {...formItemLayout}
                    label="Address"
                >
                    {getFieldDecorator('residence', {
                        initialValue: ['United State', 'San Francisco'],
                        rules: [{ type: 'array', required: true, message: 'Please select your Address!' }],
                    })(
                        <Cascader options={residences} />
                    )}
                </Form.Item>
                <Form.Item
                    {...formItemLayout}
                    label="Phone Number"
                >
                    {getFieldDecorator('phone', {
                        rules: [{ required: true, message: 'Please input your phone number!' }],
                    })(
                        <Input addonBefore={prefixSelector} style={{ width: '100%' }} />
                    )}
                </Form.Item>
            </Form>
        );
    }
}

export const UserInformation = Form.create({ name: 'userinformation' })(UserInformationForm);