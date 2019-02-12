import React from 'react';
import logo from '../assets/images/logo.svg';
import { Icon } from 'antd';

export class TopBar extends React.Component {
    render() {
        return (
            <header className="App-header">
<<<<<<< HEAD
               <img src={logo} className="App-logo" alt="logo" />
               <div className="App-title">Naming is Hard</div>
               {this.props.isLoggedIn ?
                  <a className="logout" onClick={this.props.handleLogout}>
                    <Icon type="logout" />{' '}Logout
                  </a> : null}
=======
                <img src={logo} className="App-logo" alt="logo" />
                <div className="App-title">RobotEx</div>
>>>>>>> 68d1590ad155bdeb33419177db3380297962082e
            </header>
        );
    }
}