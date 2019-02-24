import React from 'react';
import logo from '../assets/images/logo.svg';
import { Icon } from 'antd';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faRobot } from '@fortawesome/free-solid-svg-icons'
import {BrowserRouter, Link, Redirect, Route, Switch} from 'react-router-dom';
import {Payment} from "./Payment";
import {Orders} from "./Orders";
import {Home} from "./Home";

export class TopBar extends React.Component {

  render() {
    return (
            <header className="App-header">
                <FontAwesomeIcon icon={faRobot} className="App-logo" alt="logo"/>
                <div><a href='./home' className="App-title">RobotEx</a></div>
                {this.props.isLoggedIn ?
                    <a className="logout" onClick={this.props.handleLogout}>
                        <Icon type="logout" />{' '}logout
                    </a> : null}
                {this.props.isLoggedIn? <a href='./orders' className="orders">orders</a>:null}
            </header>
    );
  }
}