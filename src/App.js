import React, { Component } from 'react';
import './App.css';
import { DatePicker } from 'antd';
import GoogleMapReact from 'google-map-react';

import 'rc-steps/assets/index.css';
import 'rc-steps/assets/iconfont.css';
import Steps, { Step } from 'rc-steps';

const AnyReactComponent = ({ text }) => <div>{text}</div>;

class App extends Component {
  static defaultProps = {
    center: {
      lat: 37.773972,
      lng: -122.431297
    },
    zoom: 13
  };
  render() {
    return (

        <div style={{ height: '100vh', width: '100%' }}>
          <DatePicker/>
          <GoogleMapReact
              bootstrapURLKeys={{ key: "AIzaSyD9rV0geR-5eRazBffY9G7fBJg5yC-G1qc" }}
              defaultCenter={this.props.center}
              defaultZoom={this.props.zoom}
          >
          </GoogleMapReact>
        </div>

    );
  }
}

export class ConfirmationPage extends Component {

    description = "这里可以添加描述";

    render() {
        return (
            <div>
                <h1>
                    Success! Your order have been placed
                </h1>
                <h2>
                    Tracking number: 123456
                </h2>
                <Steps progressDot size="big" current={2}>
                    <Step title="Order Received" description={this.description} />
                    <Step title="Picked up" description={this.description} />
                    <Step title="Delivering" description={this.description} />
                    <Step title="Arrived" description={this.description} />
                </Steps>
            </div>
        );
    }
}

//export default App;
export default ConfirmationPage;
