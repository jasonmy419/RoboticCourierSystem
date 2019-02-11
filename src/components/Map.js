import GoogleMapReact from 'google-map-react';
import React from 'react';

const AnyReactComponent = ({ text }) => <div>{text}</div>;
export class Map extends React.Component{
    constructor(props){
        super(props);
       // this._onClick = this._onClick.bind(this);
    }
    static defaultProps = {
      center: {
        lat: 37.773972,
        lng: -122.431297
      },
      zoom: 13
    };
    // _onClick = ({x, y, lat, lng, event, map, maps}) => {
    //     let marker = new maps.Marker({position: { lat: lat, lng:lng},map,title:'Hello World'})}
    renderMarkers(map, maps) {
        let marker = new maps.Marker({
            position: this.props.center,
            map,
            title: 'Hello World!'
        });
    }
    render() {
        const greatPlaceStyle = {
            position: 'absolute',
        }
        return (
            <div className="container" >
                <GoogleMapReact
                bootstrapURLKeys={{ key: "AIzaSyBwKSMNZMNhNAhiIcZUlHBsUv0vkbT_EkQ" }}
                defaultCenter={this.props.center}
                defaultZoom={this.props.zoom}
                                 onGoogleApiLoaded={({map, maps}) => this.renderMarkers(map, maps)}
                >
                    <AnyReactComponent
                        lat={59.955413}
                        lng={30.337844}
                        text={'Kreyser Avrora'}
                    />

                        {/*{this.props.text}*/}

                </GoogleMapReact>

            </div>

        );
    }
}