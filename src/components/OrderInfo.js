import React from 'react';
import { Form, Input, Button, Card, message, Radio, Spin, Select } from 'antd';
import {API_ROOT, proxyurl, NUMBER, WORD, USER_ID} from "../constants";
import { RouteInfo } from "./RouteInfo";
import NumberFormat from 'react-number-format';
import robotic from "../assets/images/robotic.svg"


class OrderInfoForm extends React.Component {
    state = {
        pickUpAddr: [],
        deliveryAddr: [],
        routes: [],
        price: 0.00,
        chosenRoute: '',
        beforeRoute: true,
        isLoading: true,
        isSelectedRoute: false,
        isFlying: true,
    }

    changeAddress1 = (e) => {
        let address = this.isValidAddress(e.target.value);
        if (address) {
            console.log('Validaddress1: ', address);
            this.setState({pickUpAddr : address});
        }
        this.reset();
    }

    changeAddress2 = (e) => {
        let address = this.isValidAddress(e.target.value);
        if (address) {
            console.log('Validaddress2: ', address);
            this.setState({deliveryAddr : address});
        }
        this.reset();
    }

    selectSize = (e) => {
        console.log('size: ', e.target.value);
        this.reset();
    }

    reset = () => {
        if (!this.state.beforeRoute) {
            this.setState({ beforeRoute : true });
        }
        if (!this.state.isLoading) {
            this.setState({ isLoading : true });
        }
        if (this.state.isSelectedRoute) {
            this.setState({ isSelectedRoute : false});
        }
    }

    handleSubmit = (e) => {
        this.setState({ beforeRoute : false });
        this.props.handleResponse([]);
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
                }
                    this.setState({
                        pickUpAddr : tmpPickingUp,
                        deliveryAddr : tmpDelivery
                    });
                // send request
                console.log("route",JSON.stringify({
                    waypoint: {
                        street_number: tmpPickingUp[0],
                        street_name: tmpPickingUp[1],
                        city: tmpPickingUp[2],
                    },
                    destination: {
                        street_number: tmpDelivery[0],
                        street_name: tmpDelivery[1],
                        city: tmpDelivery[2],
                    },
                    size: values.itemSize,
                    userid : localStorage.getItem(USER_ID)
                }))
                fetch(`${API_ROOT}/routeRecommend`, {
                    method: 'POST',
                    // body: JSON.stringify({
                    //     username: values.pickingUpAddress,
                    //     password: values.deliveryAddress,
                    // }),
                    body: JSON.stringify({
                        waypoint: {
                            street_number: tmpPickingUp[0],
                            street_name: tmpPickingUp[1],
                            city: tmpPickingUp[2],
                        },
                        destination: {
                            street_number: tmpDelivery[0],
                            street_name: tmpDelivery[1],
                            city: tmpDelivery[2],
                        },
                            size: values.itemSize,
                            userid : localStorage.getItem(USER_ID)
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
                        this.setState({ isLoading : false });
                        console.log(this.state.routes);
                        // this.props.handleResponse(data);
                        // this.props.history.push('/payment');
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

    onPlaceOrder = () => {
        console.log(JSON.stringify({
            'waypoint': this.state.pickUpAddr,
            'destination': this.state.deliveryAddr,
            'detail' : {...this.state.routes.filter((route) => route.price === this.state.price)[0]},
            'user_id' : localStorage.getItem(USER_ID),
            'courier_id' : "abc",
        }));
        // send request
        fetch(`${API_ROOT}/orders`, {
            method: 'POST',

            body: JSON.stringify({
                    'waypoint': this.state.pickUpAddr,
                    'destination': this.state.deliveryAddr,
                    'detail' : {...this.state.routes.filter((route) => route.price === this.state.price)[0]},
                    'user_id' : localStorage.getItem(USER_ID),
                    'courier_id' : "abc",
                }
            ),
        }).then((response) => {
            if (response.ok) {
                this.props.handlerIsRouteChosen(true);
                return response.json();
            }
            throw new Error(response.statusText);
        })
            .then((data) => {
                console.log(data);
                this.props.history.push('/payment');
            })
            .catch((e) => {

                console.log(e);
                message.error('Sending Failed.');
            });
    }

    onSelectRoute = (e) => {
        console.log(`radio checked:${e.target.value}`);
        this.setState({price : e.target.value});
        this.setState({chosenRoute : e.target.value});
        this.props.handleResponse(this.state.routes.filter((route) => route.price === e.target.value));
    }

    onSelectRoute1 = (value) => {
        console.log(`selected ${value}`);
        this.setState({price : value[1]});
        this.setState({chosenRoute : value[1]});
        this.setState({ isSelectedRoute : true });
        console.log(`selected... ${value[0]}`);
        if (value[2] === "FLYING") {
            this.setState({ isFlying : true });
        } else {
            this.setState({ isFlying : false });
        }
        this.props.handleResponse(this.state.routes.filter((route) => route.price === value[1]));
    }

    getMode = (value) => {
        if (value === "FLYING") {
            return "Send by UAV";
        } else {
            return "Send by Robotic";
        }
    }

    getDuration = (time) => {
        const hour = parseInt(time / 3600);
        const minute = parseInt((time % 3600) / 60);
        const second = (time % 60);
        return hour == 0 ? minute + ":" + second : hour + ":" + minute + ":" + second;
    }

    checkRecommend = (flag, index) => {
        if (flag && index < 2) {
            return "and Recommended";
        }
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

        const recommendLabel = ["Shortest Time", "Lowest Price", "Recommended"];

        return (
            <Card className="order">
                <h1>Let's find out the best option for you!</h1>
                <Form onSubmit={this.handleSubmit}>
                    <Form.Item
                        {...formItemLayout}
                        label="Picking Up Address"
                    >
                        {getFieldDecorator('pickingUpAddress', {
                            rules: [{ required: true, message: 'Please input your picking up address!', whitespace: true }],
                        })(
                            <Input placeholder="#Building Street, City" onChange={this.changeAddress1}/>
                        )}
                    </Form.Item>
                    <Form.Item
                        {...formItemLayout}
                        label="Delivery Address"
                    >
                        {getFieldDecorator('deliveryAddress', {
                            rules: [{ required: true, message: 'Please input your delivery address!', whitespace: true }],
                        })(
                            <Input placeholder="#Building Street, City" onChange={this.changeAddress2}/>
                        )}
                    </Form.Item>
                    <Form.Item
                        {...formItemLayout}
                        label="Item Size"
                    >
                        {getFieldDecorator('itemSize', {
                            rules: [{ required: true, message: 'Please select your item size!'}],
                        })(
                            <Radio.Group onChange={this.selectSize} checked={false}>
                                <Radio value="SMALL">Small</Radio>
                                <Radio value="MEDIUM">Medium</Radio>
                                <Radio value="LARGE">Large</Radio>
                            </Radio.Group>
                        )}
                    </Form.Item>
                    {this.state.beforeRoute ? (
                        <Form.Item {...tailFormItemLayout}>
                            {/*<Button type="primary" htmlType="submit" className="button">*/}
                            <Button type="primary" htmlType="submit">
                                    Show Route
                            </Button>
                        </Form.Item>
                    ) : this.state.isLoading ? (
                        <Form.Item>
                            <Spin size="large" tip="We're calculating the best route for you, please be patient..."/>
                        </Form.Item>) : (

                        <Form.Item
                        {...formItemLayout}
                        label="Routes"
                    >
                        {getFieldDecorator('route-select', {
                            rules: [{ required: true, message: 'Please select a route!'}],
                        })(
                            /*<Radio.Group buttonStyle="solid" onChange={this.onSelectRoute}>*/
                                /*{this.state.routes.map((route, index) => <RouteInfo route={route} key={index} index={index}/>)}*/
                            /*</Radio.Group>*/
                            <Select onChange={this.onSelectRoute1}
                                    placeholder="Select a route">
                                {this.state.routes.map((route, index) =>
                                {
                                    // debugger;
                                    return <Select.Option value={[this.getMode(route.mode), route.price, route.mode]} key={index}>
                                        {route.mode === "FLYING" ? (<img  className="image" src="https://cdn1.iconfinder.com/data/icons/business-e-commerce-logistics-solid-set-1/91/Business_E-commerce__Logistics_15-512.png" />)
                                            : (<img  className="image" src={robotic}/>)}
                                        <span>{ recommendLabel[index] } {this.checkRecommend(route.isRecommended, index)} {": delivery in "} {this.getDuration(route.duration)}</span>
                                    </Select.Option>}
                                    )}

                            </Select>
                        )}
                    </Form.Item>)}
                    {this.state.isSelectedRoute ? (
                        <div>
                            {/*<Form.Item>*/}
                                {/*{this.state.isFlying ? (<img  className="image" src="https://cdn1.iconfinder.com/data/icons/business-e-commerce-logistics-solid-set-1/91/Business_E-commerce__Logistics_15-512.png" />)*/}
                                {/*: (<img  className="image" src={robotic}/>)}*/}
                            {/*</Form.Item>*/}
                            <Form.Item
                                {...formItemLayout}
                                label="Price: "
                            >
                                <NumberFormat value={this.state.price} displayType={'text'} thousandSeparator={true} prefix={'$'} decimalScale={2} />
                            </Form.Item>
                            <Form.Item {...tailFormItemLayout}>
                                <Button type="primary" onClick={this.onPlaceOrder}>
                                    Place Order
                                </Button>
                            </Form.Item>
                        </div>
                    ) : null }
                </Form>
            </Card>
        );
    }
}

export const OrderInfo = Form.create({ name: 'orderInfo' })(OrderInfoForm);