import React from 'react';
import { Switch, Route, Redirect } from "react-router-dom";
import {Home} from "./Home";
import { UserInformation} from "./UserInformation";
import { PaymentMain } from "./PaymentMain";

export class Main extends React.Component {
    render() {
        return (
            <div className="main">
                <Switch>
                    <Route exact path="/" render={this.getLogin}/>
                    {/*<Route path="/login" render={this.getLogin}/>*/}
                    {/*<Route path="/register" component={Register}/>*/}
                    {/*<Route path="/" component={Home}/>*/}
                    <Route path="/home" component={Home}/>
                    {/*<Route render={this.getLogin}/>*/}
                    <Route path="/user" component={UserInformation}/>
                    <Route path="/paymentMain" component={PaymentMain}/>
                    <Route component={Home}/>
                </Switch>
            </div>
        );
    }
}