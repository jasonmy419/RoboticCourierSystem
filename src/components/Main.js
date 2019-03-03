import React from 'react';
import { OrderInfo } from './OrderInfo';
import { Map} from './Map';
import { Register } from "./Register"
import { Login } from "./Login"
import { Home } from './Home';
import { Payment } from './Payment';
import { Orders } from './Orders';
import { BrowserRouter, Switch, Route, Redirect } from 'react-router-dom';
import { ConfirmationPage } from "./ConfirmationPage";
import {UserProfile} from "./UserProfile";


export class Main extends React.Component {
    state={
        orderID: "",
        response: [],
        hasCoupon: false,
        isRouteChosen: false,
        isPaymentSucceed: false,
    }
    handleCouponDraw = () =>{
        this.setState({hasCoupon: false});
    }
    handlerIsRouteChosen = (isRouteChosen) =>{
        this.setState({isRouteChosen: isRouteChosen});
    }
    handlerIsPaymentSucceed = (isPaymentSucceed) =>{
        this.setState({isPaymentSucceed: isPaymentSucceed});
    }
    handlerOrderID = (orderID) =>{
        this.setState({orderID: orderID});
    }
    handlerResponse = (response) =>{
        this.setState({response: response});
    }
    handlerCoupon = (hasCoupon) =>{
        this.setState({hasCoupon: hasCoupon});
    }
    getExactpath = () => {
        return this.props.isLoggedIn ? <Redirect to="/home"/> : <Redirect to="/login"/>;
    }
    getLogin = () => {
        return this.props.isLoggedIn ? <Redirect to="/home"/> : <Login handleSuccessfulLogin={this.props.handleSuccessfulLogin}/>;
    }
    getHome = (props) => {
        return this.props.isLoggedIn ? <Home {...props} handlerResponse = {this.handlerResponse} handlerIsRouteChosen = {this.handlerIsRouteChosen} handlerCoupon = {this.handlerCoupon}/> : <Redirect to="/login"/>;
    }
    getOrders = () => {
        return this.props.isLoggedIn ? <Orders/> : <Redirect to="/login"/>;
    }
    getUserProfile = () => {
        return this.props.isLoggedIn ? <UserProfile/> : <Redirect to="/login"/>;
    }
    getPayment = (props) => {
        if(this.props.isLoggedIn){
            if(this.state.isRouteChosen){
                return (<Payment handlerIsPaymentSucceed = {this.handlerIsPaymentSucceed} handlerOrderID = {this.handlerOrderID} {...props}/>);
            }else{
                return (<Redirect to="/home"/>);
            }
        }
        return (<Redirect to="/login"/>);
    }
    getConfirmation = (props) => {
        if(this.props.isLoggedIn){
            if(this.state.isPaymentSucceed){
                return (<ConfirmationPage response = {this.state.response} orderID = {this.state.orderID} coupon = {this.state.hasCoupon} handlerIsPaymentSucceed = {this.handlerIsPaymentSucceed} handlerIsRouteChosen = {this.handlerIsRouteChosen}
                                          handleCouponDraw = {this.handleCouponDraw}/>);
            }else{
                return (<Redirect to="/home"/>);
            }
        }
        return (<Redirect to="/login"/>);
    }


    render() {
        console.log("main",this.state.orderID);
        return (
            <div className="main">
                <BrowserRouter>
                    <Switch>
                        <Route exact path="/" render={this.getExactpath}/>
                        <Route path="/login" render={this.getLogin}/>
                        <Route path="/register" component={Register}/>
                        <Route path="/home" render={this.getHome}/>
                        <Route path="/payment" render={this.getPayment}/>
                        <Route path="/profile" render={this.getUserProfile}/>
                        {/*<Route path="/payment" component={Payment}/>*/}
                        <Route path="/confirmation" render={this.getConfirmation}/>
                        <Route path="/orders" render={this.getOrders}/>
                        <Route render={this.getLogin}/>
                    </Switch>
                </BrowserRouter>
            </div>
        );
    }
}