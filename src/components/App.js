import React, { Component } from 'react';
import {API_ROOT} from "../constants";
import '../styles/App.css';
// import GoogleMapReact from 'google-map-react';
import { TopBar } from './TopBar';
import { Main } from "./Main";
// import { ConfirmationPage } from './ConfirmationPage';

import 'rc-steps/assets/index.css';
import 'rc-steps/assets/iconfont.css';
import { USER_ID } from '../constants';
import {BrowserRouter, Redirect, Route, Switch} from "react-router-dom";
import {Register} from "./Register";
import {Payment} from "./Payment";
import {message} from "antd"
import md5 from "md5"
// import Steps, { Step } from 'rc-steps';

// const AnyReactComponent = ({ text }) => <div>{text}</div>;

class App extends Component {
  constructor(props){
    console.log("app constructor ran");
    super(props);
    this.state = {
      isLoggedIn: Boolean(localStorage.getItem(USER_ID))? true : false
    }
  }

  componentWillMount() {
    console.log("componentWillMount ran");
    this.validateSession();
  }

  validateSession = () => {
    console.log("validate session function ran")

    // //for test purpose, comment for final use
    // const response = '{"status": "OK", "user_id": "Jizhou"}';
    // const promise = new Promise((resolve, reject) => {
    //   resolve(response);
    // });

    //send request, uncomment for final use
    fetch(`${API_ROOT}/login`, {
      method: "GET",
      credentials: "include",
    }).then((response) => {
      if(response){
        return response.text();
      }
      throw new Error(response.statusText);
    }).then((data) => {
      console.log(data);
      return JSON.parse(data);
    }).then((json) => {
      if(json.status === "OK"){
        console.log("Session is valid");
        localStorage.setItem(USER_ID,json.user_id);
        this.setState({isLoggedIn: true,});
      } else {
        console.log("Session is INVALID");
        localStorage.removeItem(USER_ID);
        this.setState({isLoggedIn: false,});
      }
    }).catch((err) => {
      console.log("catch err")
      console.log(err);
    });
  }

  handleSuccessfulLogin = (user_id) => {
    localStorage.setItem(USER_ID, user_id);
    this.setState({ isLoggedIn: true });
  }

  handleLogout = () => {
    // localStorage.removeItem(USER_ID);
    // this.setState({isLoggedIn: false});
    fetch(`${API_ROOT}/logout`, {
      method: 'POST',
      credentials: 'include',
    }).then((response) => {
      if(response){
        return response.text();
      }
      throw new Error(response.statusText);
    }).then((data) => {
      console.log(data);
      return JSON.parse(data);
    }).then((json) => {
      console.log("logout", json);
      if(json.status === "OK"){
        console.log("Logout successfully")
        localStorage.removeItem(USER_ID);
        this.setState({isLoggedIn: false});
      } else {
        console.log("invalid operation");
      }
    }).catch((err) => {
      console.log(err);
    });
  }

  render() {
    console.log("rendering");
    console.log(`this.state.isLoggedIn:${this.state.isLoggedIn}, localstorage.USER_ID: ${localStorage.getItem("USER_ID")}`);
    return (
        <div className="App">
          <TopBar handleLogout={this.handleLogout} isLoggedIn={this.state.isLoggedIn} />
          <Main handleSuccessfulLogin={this.handleSuccessfulLogin} isLoggedIn={this.state.isLoggedIn} />
        </div>
    );
  }
}

// export class ConfirmationPage extends Component {
//
//     description = "这里可以添加描述";
//
//     render() {
//         return (
//             <div>
//                 <h1>
//                     Success! Your order have been placed
//                 </h1>
//                 <h2>
//                     Tracking number: 123456
//                 </h2>
//                 <Steps progressDot size="big" current={2}>
//                     <Step title="Order Received" description={this.description} />
//                     <Step title="Picked up" description={this.description} />
//                     <Step title="Delivering" description={this.description} />
//                     <Step title="Arrived" description={this.description} />
//                 </Steps>
//             </div>
//         );
//     }
// }

export default App;
// export default ConfirmationPage;
