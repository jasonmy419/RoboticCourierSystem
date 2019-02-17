import GoogleMapReact from 'google-map-react';
import React from 'react';

const AnyReactComponent = ({ text }) => <div>{text}</div>;
export class Map extends React.Component{
    constructor(props){
        super(props);
    }
    static defaultProps = {
      center: {
        lat: 37.773972,
        lng: -122.431297
      },
      zoom: 13,
        firstStation:{
          lat:37.793321,
          lng:-122.422794
        },
        secondStation:{
          lat:37.754538,
            lng:-122.407157
        },
        thirdStation:{
          lat: 37.757644,
            lng:-122.436165
        }
    };
    // _onClick = ({x, y, lat, lng, event, map, maps}) => {
    //     let marker = new maps.Marker({position: { lat: lat, lng:lng},map,title:'Hello World'})}
    renderMarkers(map, maps) {
        let first = new maps.Marker({
            position: this.props.firstStation,
            map,
            title: 'Hello World!'
        });
        let second = new maps.Marker({
            position: this.props.secondStation,
            map,
            title: 'Hello World!'
        });
        let third = new maps.Marker({
            position: this.props.thirdStation,
            map,
            title: 'Hello World!'
        });
    }
    // _onClick = ({x, y, lat, lng, event}) => new maps.Marker({
    //     position: this.props.firstStation,
    //     this,
    //     title: 'Hello World!'
    // });
    // componentDidMount(map, maps) {
    //     var myLatlng = new maps.LatLng(51.65905179951626, 7.3835928124999555);
    //     var myOptions = {
    //         zoom: 8,
    //         center: myLatlng,
    //         mapTypeId: maps.MapTypeId.ROADMAP
    //     }
    //     var map = new maps.Map(document.getElementById("map"), myOptions);
    //
    //     var decodedPath = maps.geometry.encoding.decodePath('}~kvHmzrr@ba\\hnc@jiu@r{Zqx~@hjp@pwEhnc@zhu@zflAbxn@fhjBvqHroaAgcnAp}gAeahAtqGkngAinc@_h|@r{Zad\\y|_D}_y@swg@ysg@}llBpoZqa{@xrw@~eBaaX}{uAero@uqGadY}nr@`dYs_NquNgbjAf{l@|yh@bfc@}nr@z}q@i|i@zgz@r{ZhjFr}gApob@ff}@laIsen@dgYhdPvbIren@');
    //     var decodedLevels = decodeLevels("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
    //
    //     var setRegion = new maps.Polyline({
    //         path: decodedPath,
    //         levels: decodedLevels,
    //         strokeColor: "#FF0000",
    //         strokeOpacity: 1.0,
    //         strokeWeight: 2,
    //         map: map
    //     });
    //
    // }

    render() {
        return (
            <div className="container" >
                <GoogleMapReact
                bootstrapURLKeys={{ key: "" }}
                defaultCenter={this.props.center}
                defaultZoom={this.props.zoom}
                onGoogleApiLoaded={({map, maps}) => this.renderMarkers(map, maps)}
                onClick={this._onClick}
                >
                </GoogleMapReact>
            </div>
        );
    }
}