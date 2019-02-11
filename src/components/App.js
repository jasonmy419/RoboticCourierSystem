import React, { Component } from 'react';
import '../styles/App.css';
// import GoogleMapReact from 'google-map-react';
import { TopBar } from './TopBar';
import { Main } from "./Main";
// import { ConfirmationPage } from './ConfirmationPage';

import 'rc-steps/assets/index.css';
import 'rc-steps/assets/iconfont.css';
import { TOKEN_KEY } from '../constants';
// import Steps, { Step } from 'rc-steps';

// const AnyReactComponent = ({ text }) => <div>{text}</div>;

class App extends Component {
  state = {
    isLoggedIn: Boolean(localStorage.getItem(TOKEN_KEY)),
  }

  handleSuccessfulLogin = (token) => {
    localStorage.setItem(TOKEN_KEY, token);
    this.setState({ isLoggedIn: true });
  }

  handleLogout = () => {
    localStorage.removeItem(TOKEN_KEY);
    this.setState({ isLoggedIn: false });
  }

  render() {
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
