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
            console.log(fieldsValue.month);
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
                    last_name: values.last_name,
                    first_name: values.first_name,
                    card_number: values.card_number,
                    address1: values.address1,
                    address2: values.address2,
                    city: values.city,
                    zipcode: values.zipcode,
                    state: values.state,
                    month: values.month,
                    year: values.year,
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
                    label="Last_Name"
                >
                    {getFieldDecorator('last_name', {
                        rules: [{
                            required: true, message: 'Please input your last_name!',
                        }],
                    })(
                        <Input type="last_name" onBlur={this.handleConfirmBlur}/>
                    )}
                </Form.Item>
                <Form.Item
                    {...formItemLayout}
                    label="First_Name"
                >
                    {getFieldDecorator('first_name', {
                        rules: [{
                            required: true, message: 'Please input your first_name!',
                        }],
                    })(
                        <Input type="first_name" onBlur={this.handleConfirmBlur} />
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
                    label="AddressLine1"
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
                    label="AddressLine2"
                >
                    {getFieldDecorator('address', {

                    })(
                        <Input type="address" />
                    )}
                </Form.Item>

                <Form.Item
                    {...formItemLayout}
                    label="City"
                >
                    {getFieldDecorator('city', {
                        rules: [{
                            required: true, message: 'Please input your city!',
                        }],
                    })(
                        <Input type="state" />
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
                    label="Zipcode"
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
                    label="expiration date"
                >
                    {getFieldDecorator('expiration date', config)(
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