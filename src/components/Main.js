import React from 'react';
import { Switch, Route, Redirect } from "react-router-dom";
import {Home} from "./Home";
import { ConfirmationPage} from "./ConfirmationPage";

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
                    <Route path="/confirmation" component={ConfirmationPage}/>
                    <Route component={Home}/>
                </Switch>
            </div>
        );
    }
}