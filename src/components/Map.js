import GoogleMapReact from 'google-map-react';
import React from 'react';

const AnyReactComponent = ({ text }) => <div>{text}</div>;
export class Map extends React.Component{
    static defaultProps = {
      center: {
        lat: 37.773972,
        lng: -122.431297
      },
      zoom: 13
    };
    render() {
        return (
            <div>
                <GoogleMapReact className = "map"
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