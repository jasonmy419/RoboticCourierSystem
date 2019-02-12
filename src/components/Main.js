import React from 'react';
<<<<<<< HEAD
import { OrderInfo } from './OrderInfo';
import { Map} from './Map';
import { Register } from "./Register"
import { Login } from "./Login"
import { Home } from './Home';
import { BrowserRouter, Switch, Route, Redirect } from 'react-router-dom';

=======
import { Switch, Route, Redirect } from "react-router-dom";
import {Home} from "./Home";
import { ConfirmationPage} from "./ConfirmationPage";
import { Payment } from "./Payment";
>>>>>>> 68d1590ad155bdeb33419177db3380297962082e

export class Main extends React.Component {
    getLogin = () => {
        return this.props.isLoggedIn ? <Redirect to="/home"/> : <Login handleSuccessfulLogin={this.props.handleSuccessfulLogin}/>;
    }

    getHome = () => {
        return this.props.isLoggedIn ? <Home/> : <Redirect to="/login"/>;
    }

    render() {
        return (
            <div className="main">
<<<<<<< HEAD
              <BrowserRouter>
                <Switch>
                    <Route exact path="/" render={this.getLogin}/>
                    <Route path="/login" render={this.getLogin}/>
                    <Route path="/register" component={Register}/>
                    <Route path="/home" render={this.getHome}/>
                    <Route render={this.getLogin}/>
                </Switch>
              </BrowserRouter>
=======
                <Switch>
                    <Route exact path="/" component={Home}/>
                    {/*<Route path="/login" render={this.getLogin}/>*/}
                    {/*<Route path="/register" component={Register}/>*/}
                    {/*<Route path="/" component={Home}/>*/}
                    <Route path="/home" component={Home}/>
                    <Route path="/payment" component={Payment}/>
                    <Route path="/confirmation" component={ConfirmationPage}/>
                    <Route component={Home}/>
                </Switch>
>>>>>>> 68d1590ad155bdeb33419177db3380297962082e
            </div>
        );
    }
}