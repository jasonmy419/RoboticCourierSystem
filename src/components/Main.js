import React from 'react';
import { Switch, Route, Redirect } from "react-router-dom";
import {Home} from "./Home";
import { ConfirmationPage} from "./ConfirmationPage";
import { Payment } from "./Payment";

export class Main extends React.Component {
    render() {
        return (
            <div className="main">
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
            </div>
        );
    }
}