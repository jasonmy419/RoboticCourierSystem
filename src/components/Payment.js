import React from 'react';
import {
    Form, Input, Select, Button, DatePicker, message
} from 'antd';
import { API_ROOT, ORDER_NUM } from '../constants';
import { Link } from 'react-router-dom';


const { Option } = Select;
const { MonthPicker } = DatePicker;


class PaymentFrom extends React.Component {
    state = {
        confirmDirty: false,
        autoCompleteResult: [],
    };

    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, fieldsValue) => {if (!err) {

            const rangeValue = fieldsValue['range-picker'];
            const rangeTimeValue = fieldsValue['range-time-picker'];
            const values = {
                ...fieldsValue,
                'date-picker': fieldsValue['date-picker'].format('YYYY-MM-DD'),
                'date-time-picker': fieldsValue['date-time-picker'].format('YYYY-MM-DD HH:mm:ss'),
                'month-picker': fieldsValue['month-picker'].format('YYYY-MM'),
                'range-picker': [rangeValue[0].format('YYYY-MM-DD'), rangeValue[1].format('YYYY-MM-DD')],
                'range-time-picker': [
                    rangeTimeValue[0].format('YYYY-MM-DD HH:mm:ss'),
                    rangeTimeValue[1].format('YYYY-MM-DD HH:mm:ss'),
                ],
                'time-picker': fieldsValue['time-picker'].format('HH:mm:ss'),
            };
            console.log('Received values of form: ', values);
            // send request
            fetch(`${API_ROOT}/checkout`, {
                method: 'POST',
                body: JSON.stringify({
                    lastname: values.lastname,
                    firstname: values.firstname,
                    email: values.email,
                    phone: values.phone,
                    address: values.address,
                    state: values.state,
                    zip: values.zip,
                    cardnumber: values.cardnumber,
                    cardaddress: values.cardaddress,
                    month: values.month,
                    date: values.date,
                    cvv: values.cvv,
                }),
            }).then((response) => {
                if (response.ok) {
                    return response.text();
                }
                throw new Error(response.statusText);
            }).then((data) => {
                message.success('Check Success!');
                console.log(data);
                this.props.handleSuccessfulLogin(data);
                localStorage.setItem(ORDER_NUM, data);
            }).catch((e) => {
                console.log(e);
                message.error('Check Failed.');
            });

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
        const config = {
            rules: [{ type: 'object', required: true, message: 'Please select time!' }],
        };

        return (
            <Form onSubmit={this.handleSubmit} className="payment">

                <Form.Item
                    {...formItemLayout}
                    label="Last Name"
                >
                    {getFieldDecorator('lastname', {
                        rules: [{
                            required: true, message: 'Please input your lastname!',
                        }],
                    })(
                        <Input type="lastname" onBlur={this.handleConfirmBlur}/>
                    )}
                </Form.Item>
                <Form.Item
                    {...formItemLayout}
                    label="First Name"
                >
                    {getFieldDecorator('firstname', {
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
                    label="Phone Number"
                >
                    {getFieldDecorator('phone', {
                        rules: [{ required: true, message: 'Please input your phone number!' }],
                    })(
                        <Input addonBefore={prefixSelector} style={{ width: '100%' }} />
                    )}
                </Form.Item>

                <Form.Item
                    {...formItemLayout}
                    label="Address"
                >
                    {getFieldDecorator('address', {
                        rules: [{
                            required: true, message: 'Please input your address!',
                        }],
                    })(
                        <Input type="address" />
                    )}
                </Form.Item>

                <Form.Item
                    {...formItemLayout}
                    label="State"
                >
                    {getFieldDecorator('state', {
                        rules: [{
                            required: true, message: 'Please input your state!',
                        }],
                    })(
                        <Input type="state" />
                    )}
                </Form.Item>

                <Form.Item
                    {...formItemLayout}
                    label="Zip"
                >
                    {getFieldDecorator('zipcode', {
                        rules: [{
                            required: true, message: 'Please input your zipcode!',
                        }],
                    })(
                        <Input type="zipcode" />
                    )}
                </Form.Item>
                <hr></hr>

                <Form.Item
                    {...formItemLayout}
                    label="Card Number"
                >
                    {getFieldDecorator('cardnumber', {
                        rules: [{
                            required: true, message: 'Please input your cardnumber!',
                        }],
                    })(
                        <Input type="cardnumber" />
                    )}
                </Form.Item>

                <Form.Item
                    {...formItemLayout}
                    label="Card Address"
                >
                    {getFieldDecorator('cardaddress', {
                        rules: [{
                            required: true, message: 'Please input your cardaddress!',
                        }],
                    })(
                        <Input type="cardaddress" />
                    )}
                </Form.Item>

                <Form.Item
                    {...formItemLayout}
                    label="Month"
                >
                    {getFieldDecorator('month', config)(
                        <MonthPicker />
                    )}
                </Form.Item>

                <Form.Item
                    {...formItemLayout}
                    label="Date"
                >
                    {getFieldDecorator('date', config)(
                        <DatePicker />
                    )}
                </Form.Item>

                <Form.Item
                    {...formItemLayout}
                    label="CVV"
                >
                    {getFieldDecorator('cvv', {
                        rules: [{
                            required: true, message: 'Please input your cvv!',
                        }],
                    })(
                        <Input type="cvv" />
                    )}
                </Form.Item>

                <hr></hr>

                <Form.Item
                    wrapperCol={{
                        xs: { span: 24, offset: 0 },
                        sm: { span: 16, offset: 8 },
                    }}
                >
                    <Button type="primary" htmlType="submit"><Link to ='/confirmation'>Place your order</Link></Button>
                    <p> Order total :</p>
                </Form.Item>
            </Form>
        );
    }
}

export const Payment = Form.create({ name: 'userinformation' })(PaymentFrom);