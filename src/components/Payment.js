import React from 'react';
import {
    Form, Input, Select, Button, DatePicker, message, Row, Col
} from 'antd';
import {API_ROOT, ORDER_NUM, USER_ID} from '../constants';
import moment from 'moment';


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

            const values = {
                ...fieldsValue,
                year: fieldsValue ['expiration date'].format('YYYY'),
                month: fieldsValue ['expiration date'].format('MM')
            };
            console.log('payment json', JSON.stringify({
                user_id:localStorage.getItem(USER_ID),
                // user_id: localStorage.getItem(USER_ID),
                last_name: values.last_name,
                first_name: values.first_name,
                card_number: values.cardnumber,
                address_line1: values.address1,
                address_line2: values.address2,
                city: values.city,
                zipcode: values.zipcode,
                state: values.state,
                month: values.month,
                year: values.year,
                cvv: values.cvv,
            }));
            const isValid = () =>{
                if(values.zipcode.length != 5){
                    message.error("Invalid zipcode");
                    return false;
                }else if(values.cardnumber.length != 16){
                    message.error("Invalid card number");
                    return false;
                }else if(values.cvv.length != 3){
                    message.error("Invalid cvv");
                    return false;
                }
                return true;
            }
            if(isValid()){
                fetch(`${API_ROOT}/checkout`, {
                    method: 'POST',
                    body: JSON.stringify({
                        user_id:localStorage.getItem(USER_ID),
                        // user_id: localStorage.getItem(USER_ID),
                        last_name: values.last_name,
                        first_name: values.first_name,
                        card_number: values.cardnumber,
                        address_line1: values.address1,
                        address_line2: values.address2,
                        city: values.city,
                        zipcode: values.zipcode,
                        state: values.state,
                        month: values.month,
                        year: values.year,
                        cvv: values.cvv,
                    }),
                }).then((response) => {
                    if (response.ok) {
                        this.props.handlerIsPaymentSucceed(true);
                        return response.json();
                    }
                    throw new Error(response.statusText);
                }).then((data) => {
                    console.log(data);
                    console.log(data.confirmation_number);
                    const {confirmation_number} = data;
                    console.log(confirmation_number);
                    if(confirmation_number){
                        message.success('Congratulations, you have successfully checked out!');
                        // this.props.handleSuccessfulLogin(data);
                        this.props.history.push("/confirmation");
                        this.props.handlerOrderID(confirmation_number);
                    }else{
                        message.error('Check out failed, try again');
                    }

                    //localStorage.setItem(ORDER_NUM, data)
                }).catch((e) => {;
                    console.log(e);
                    message.error('Check out failed, try again');
                });
            }

        }
        });

    }

    handleConfirmBlur = (e) => {
        const value = e.target.value;
        this.setState({ confirmDirty: this.state.confirmDirty || !!value });
    }


    disabledDate = (date) =>{
        const {currDate} = new Date().getDate();
        console.log(currDate);
        return (date.diff(currDate, 'days') < -2);
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

        const formItemLayouttwo = {
            labelCol: {
                xs: { span: 24, offset: 8 },
                sm: { span: 8 },
            },
            wrapperCol: {
                xs: { span: 24},
                sm: { span: 8 },
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

            <div>
                <h2 style={{ width: '108%' }}>Billing Information</h2>
            <Form onSubmit={this.handleSubmit} className="payment"  >

                <Row gutter={12}>
                    <Col span={12}>
                <Form.Item

                    {...formItemLayouttwo}
                    label="Last Name"

                >
                    {getFieldDecorator('last_name', {
                        rules: [{
                            required: true, message: 'Please input your last_name!',
                        }],
                    })(
                        <Input autoFocus={true} type="last_name" onBlur={this.handleConfirmBlur}/>
                    )}
                </Form.Item>
                     </Col>
                    <Col span={12}>
                <Form.Item
                    {...formItemLayout}
                    label="First Name"
                >
                    {getFieldDecorator('first_name', {
                        rules: [{
                            required: true, message: 'Please input your first_name!',
                        }],
                    })(
                        <Input type="first_name" onBlur={this.handleConfirmBlur} />
                    )}
                </Form.Item>
                    </Col>
                </Row>

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
                    {getFieldDecorator('address1', {
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
                    {getFieldDecorator('address2', {

                    })(
                        <Input type="address" />
                    )}
                </Form.Item>

                <Row gutter={8}>
                    <Col span={12}>
                <Form.Item
                    {...formItemLayouttwo}
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
                        </Col>
                    <Col span={12}>
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
                        </Col>
                    </Row>


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
                <Row gutter={8}>
                    <Col span={12}>
                <Form.Item
                    {...formItemLayouttwo}
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
                        </Col>
                    <Col span={12}>
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
                        </Col>
                    </Row>

                <Form.Item className="date-picker"
                    {...formItemLayout}
                    label="Expiration Date"
                >
                    {getFieldDecorator('expiration date', config)(
                        <DatePicker size="large" style={{ width: `100%` }} disabledDate={current => current < moment().startOf('day')}/>
                    )}
                </Form.Item>

                <Form.Item
                    wrapperCol={{
                        xs: { span: 24, offset: 0 },
                        sm: { span: 16, offset: 8 },
                    }}
                >
                    <Button  type="primary" htmlType="submit" className="payment-button">pay now</Button>
                </Form.Item>
            </Form>
            </div>
        );
    }
}

export const Payment = Form.create({ name: 'userinformation' })(PaymentFrom);