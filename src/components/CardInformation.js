import React from 'react';
import {
    Form, DatePicker, Button, Input, Row, Col,
} from 'antd';

const { MonthPicker } = DatePicker;

class CardInformationForm extends React.Component {
    handleSubmit = (e) => {
        e.preventDefault();

        this.props.form.validateFields((err, fieldsValue) => {
            if (err) {
                return;
            }

            // Should format date value before submit.
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
        });
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
        const config = {
            rules: [{ type: 'object', required: true, message: 'Please select time!' }],
        };
        return (
            <Form onSubmit={this.handleSubmit} className="cardinformation">
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

                <Form.Item
                    {...formItemLayout}
                    label="Captcha"
                    extra="We must make sure that your are a human."
                >
                    <Row gutter={8}>
                        <Col span={12}>
                            {getFieldDecorator('captcha', {
                                rules: [{ required: true, message: 'Please input the captcha you got!' }],
                            })(
                                <Input />
                            )}
                        </Col>
                        <Col span={12}>
                            <Button>Get captcha</Button>
                        </Col>
                    </Row>
                </Form.Item>

                <Form.Item
                    wrapperCol={{
                        xs: { span: 24, offset: 0 },
                        sm: { span: 16, offset: 8 },
                    }}
                >
                    <Button type="primary" htmlType="submit">Submit</Button>
                </Form.Item>
            </Form>
        );
    }
}

export const CardInformation = Form.create({ name: 'userinformation' })(CardInformationForm);

