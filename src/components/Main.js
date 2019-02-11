import React from 'react';
import { Switch, Route, Redirect } from "react-router-dom";
import {Home} from "./Home";
export class Main extends React.Component {
    render() {
        return (
            <div className="main">
                <Switch>
                    <Route exact path="/" render={this.getLogin}/>
                    <Route path="/login" render={this.getLogin}/>
                    {/*<Route path="/register" component={Register}/>*/}
                    <Route path="/home" component={Home}/>
                    <Route render={this.getLogin}/>
                </Switch>
            </div>
        );
    }
}