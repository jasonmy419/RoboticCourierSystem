import React, { Component } from 'react';
import './App.css';
import { DatePicker } from 'antd';
import GoogleMapReact from 'google-map-react';
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

export default App;
