import React from 'react';
import { Form, Input, Button, Select, Card, message, Radio } from 'antd';
import { API_ROOT, proxyurl, NUMBER, WORD, PRICE } from "../constants";
import { Link } from 'react-router-dom';
import { RouteInfo } from "./RouteInfo";

class OrderInfoForm extends React.Component {
    state = {
        pickUpAddr: [],
        deliveryAddr: [],
        routes: [],
        price: 0.00,
    }
    // handleSubmit = (e) => {
    //     e.preventDefault();
    //     this.props.form.validateFieldsAndScroll((err, values) => {
    //         if (!err) {
    //             console.log('Received values of form: ', values);
    //             this.props.history.push('/');
    //         }
    //     });
    // }
    // onChange = (e) => {
    //     console.log('radio checked', e.target.value);
    //     this.setState({
    //         value: e.target.value,
    //     });
    // }

    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFieldsAndScroll((err, values) => {
            if (!err) {
                console.log('Received values of form: ', values);
                // check is valid addresses
                let tmpPickingUp = this.isValidAddress(values.pickingUpAddress);
                let tmpDelivery = this.isValidAddress(values.deliveryAddress);
                if (!tmpPickingUp) {
                    message.error('Picking up address is invalid!');
                    return;
                } else if (!tmpDelivery) {
                    message.error('Delivery address is invalid!');
                    return;
                } else {
                    this.setState({
                        pickUpAddr : tmpPickingUp,
                        deliveryAddr : tmpDelivery
                    })
                }
                // send request
                fetch(`${API_ROOT}/routeRecommend`, {
                    method: 'POST',
                    // body: JSON.stringify({
                    //     username: values.pickingUpAddress,
                    //     password: values.deliveryAddress,
                    // }),
                    body: JSON.stringify({
                            'waypoint': {
                                street_number: this.state.pickUpAddr[0],
                                street_name: this.state.pickUpAddr[1],
                                city: this.state.pickUpAddr[2],
                            },
                            'destination': {
                                street_number: this.state.deliveryAddr[0],
                                street_name: this.state.deliveryAddr[1],
                                city: this.state.deliveryAddr[2],
                            },
                            'size': values.itemSize,
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
                        message.success('Sending Succeed!');
                        this.setState({ routes : data ? data : [] });
                        console.log(this.state.routes);
                        // this.props.handleResponse(data);
                        //this.props.history.push('/payment');
                    })
                    .catch((e) => {

                        console.log(e);
                        message.error('Sending Failed.');
                    });
            }
        });
    }

    isValidAddress = (address) => {
        let building = this.getBuildingNumber(address);
        let streetCity = this.getStreetCity(address);
        if (building && streetCity) {
            return [building, ...streetCity];
        } else {
            return false;
        }
    }

    getBuildingNumber = (address) => {
        let building = address.match(NUMBER);
        if (!building) { return false; }
        else { return building[0]; }
    }

    getStreetCity = (address) => {
        let street = address.match(WORD);
        if (!street || !street[1]) { return false; }
        else { return [street[0], street[1]]; }
    }

    onClick = () => {
        this.props.history.push('/payment');
    }

    // getPrice = (price) => {
    //     return price.match(PRICE);
    // }

    onChange = (e) => {
        console.log(`radio checked:${e.target.value}`);
        this.setState({price : e.target.value});
        this.props.handleResponse(this.state.routes.filter((route) => route.price === e.target.value));
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

        return (
            <Card className="order">
                <Form onSubmit={this.handleSubmit}>
                    <Form.Item
                        {...formItemLayout}
                        label="Picking Up Address"
                    >
                        {getFieldDecorator('pickingUpAddress', {
                            rules: [{ required: true, message: 'Please input your picking up address!', whitespace: true }],
                        })(
                            <Input placeholder="#Building Street, City"/>
                        )}
                    </Form.Item>
                    <Form.Item
                        {...formItemLayout}
                        label="Delivery Address"
                    >
                        {getFieldDecorator('deliveryAddress', {
                            rules: [{ required: true, message: 'Please input your delivery address!', whitespace: true }],
                        })(
                            <Input placeholder="#Building Street, City"/>
                        )}
                    </Form.Item>
                    <Form.Item
                        {...formItemLayout}
                        label="Item Size"
                    >
                        {getFieldDecorator('itemSize', {
                            rules: [{ required: true, message: 'Please select your item size!'}],
                        })(
                            <Radio.Group>
                                <Radio value="SMALL">Small</Radio>
                                <Radio value="MEDIUM">Medium</Radio>
                                <Radio value="LARGE">Large</Radio>
                            </Radio.Group>
                        )}
                    </Form.Item>
                    <Form.Item
                        {...formItemLayout}
                        label="Radio.Button"
                    >
                        {getFieldDecorator('radio-button')(
                            <Radio.Group onChange={this.onChange}>
                                {this.state.routes.map((route) => <RouteInfo route={route} key={route.price}/>)}
                            </Radio.Group>
                        )}
                    </Form.Item>
                    <Form.Item
                        {...formItemLayout}
                        label="Price: "
                    >
                        <p>$ {this.state.price} </p>
                    </Form.Item>
                    <Form.Item {...tailFormItemLayout}>
                        <Button type="primary" htmlType="submit">
                            Show Route
                        </Button>
                        <Button type="primary" className="button" onClick={this.onClick}>
                            Place Order
                        </Button>
                    </Form.Item>
                </Form>
            </Card>
        );
    }
}

export const OrderInfo = Form.create({ name: 'orderInfo' })(OrderInfoForm);