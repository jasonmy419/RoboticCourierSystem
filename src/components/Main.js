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


export class Main extends React.Component {

    getExactpath = () => {
        return this.props.isLoggedIn ? <Redirect to="/home"/> : <Redirect to="/login"/>;
    }

    getLogin = () => {
        return this.props.isLoggedIn ? <Redirect to="/home"/> : <Login handleSuccessfulLogin={this.props.handleSuccessfulLogin}/>;
    }

    getHome = (props) => {
        return this.props.isLoggedIn ? <Home {...props} /> : <Redirect to="/login"/>;
    }
    getOrders = () => {
        return this.props.isLoggedIn ? <Orders/> : <Redirect to="/login"/>;
    }
    getPayment = () => {
        return this.props.isLoggedIn ? <Payment/> : <Redirect to="/login"/>;
    }
    getConfirmation = () => {
        return <ConfirmationPage orderID = '123abc'/>
    }

    render() {
        return (
            <div className="main">
                <BrowserRouter>
                    <Switch>
                        <Route exact path="/" render={this.getExactpath}/>
                        <Route path="/login" render={this.getLogin}/>
                        <Route path="/register" component={Register}/>
                        <Route path="/home" render={this.getHome}/>
                        <Route path="/payment" render={this.getPayment}/>
                        <Route path="/confirmation" render={this.getConfirmation}/>
                        <Route path="/orders" render={this.getOrders}/>
                        <Route render={this.getLogin}/>
                    </Switch>
                </BrowserRouter>
            </div>
        );
    }
}