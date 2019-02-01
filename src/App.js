import React, { Component } from 'react';
import './App.css';
import { DatePicker } from 'antd';
import GoogleMapReact from 'google-map-react';
const AnyReactComponent = ({ text }) => <div>{text}</div>;
class App extends Component {
  static defaultProps = {
    center: {
      lat: 59.95,
      lng: 30.33
    },
    zoom: 11
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
            <AnyReactComponent
                lat={59.955413}
                lng={30.337844}
                text={'Kreyser Avrora'}
            />
          </GoogleMapReact>
        </div>

    );
  }
}

export default App;
