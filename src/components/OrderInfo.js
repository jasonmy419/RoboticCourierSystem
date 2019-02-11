import React from 'react';
import { Form, Input, Button, Select, Card } from 'antd';

class OrderInfoForm extends React.Component {
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

    compareToFirstPassword = (rule, value, callback) => {
        const form = this.props.form;
        if (value && value !== form.getFieldValue('password')) {
            callback('Two passwords that you enter is inconsistent!');
        } else {
            callback();
        }
    }

    validateToNextPassword = (rule, value, callback) => {
        const form = this.props.form;
        if (value && this.state.confirmDirty) {
            form.validateFields(['confirm'], { force: true });
        }
        callback();
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
        const tailFormItemLayout = {
            wrapperCol: {
                xs: {
                    span: 24,
                    offset: 0,
                },
                sm: {
                    span: 16,
                    offset: 8,
                },
            },
        };

        const Option = Select.Option;

        function handleChange(value) {
            console.log(`selected ${value}`);
        }

        return (
            <Card style={{ width: 520 }} className="order">
            <Form onSubmit={this.handleSubmit}>
                <Form.Item
                    {...formItemLayout}
                    label="Picking Up Address"
                >
                    {getFieldDecorator('pickingUpAddress', {
                        rules: [{ required: true, message: 'Please input your picking up address!', whitespace: true }],
                    })(
                        <Input />
                    )}
                </Form.Item>
                <Form.Item
                    {...formItemLayout}
                    label="Delivery Address"
                >
                    {getFieldDecorator('deliveryAddress', {
                        rules: [{ required: true, message: 'Please input your delivery address!', whitespace: true }],
                    })(
                        <Input />
                    )}
                </Form.Item>
                <Form.Item
                    {...formItemLayout}
                    label="Recommanded By"
                >
                    <Select defaultValue="price" style={{ width: 315 }} onChange={handleChange}>
                        <Option value="price">the lowest price</Option>
                        <Option value="time">the shortest time</Option>
                    </Select>
                </Form.Item>
                <Form.Item
                    {...formItemLayout}
                    label="Price: "
                >
                    <p>$100.00</p>
                </Form.Item>
                <Form.Item {...tailFormItemLayout}>
                    <Button type="primary" htmlType="submit">Place Order</Button>
                </Form.Item>
            </Form>
            </Card>
        );
    }
}

export const OrderInfo = Form.create({ name: 'orderInfo' })(OrderInfoForm);