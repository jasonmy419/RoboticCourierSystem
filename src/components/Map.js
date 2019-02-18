//import GoogleMapReact from 'google-map-react';
// import React from 'react';

// const AnyReactComponent = ({ text }) => <div>{text}</div>;
// export class Map extends React.Component{
//     constructor(props){
//         super(props);
//     }
//     static defaultProps = {
//       center: {
//         lat: 37.773972,
//         lng: -122.431297
//       },
//       zoom: 13,
//         firstStation:{
//           lat:37.793321,
//           lng:-122.422794
//         },
//         secondStation:{
//           lat:37.754538,
//             lng:-122.407157
//         },
//         thirdStation:{
//           lat: 37.757644,
//             lng:-122.436165
//         }
//     };
//     // _onClick = ({x, y, lat, lng, event, map, maps}) => {
//     //     let marker = new maps.Marker({position: { lat: lat, lng:lng},map,title:'Hello World'})}
//     renderMarkers(map, maps) {
//         let first = new maps.Marker({
//             position: this.props.firstStation,
//             map,
//             title: 'Hello World!'
//         });
//         let second = new maps.Marker({
//             position: this.props.secondStation,
//             map,
//             title: 'Hello World!'
//         });
//         let third = new maps.Marker({
//             position: this.props.thirdStation,
//             map,
//             title: 'Hello World!'
//         });
//     }
//     // _onClick = ({x, y, lat, lng, event}) => new maps.Marker({
//     //     position: this.props.firstStation,
//     //     this,
//     //     title: 'Hello World!'
//     // });
//     // componentDidMount(map, maps) {
//     //     var myLatlng = new maps.LatLng(51.65905179951626, 7.3835928124999555);
//     //     var myOptions = {
//     //         zoom: 8,
//     //         center: myLatlng,
//     //         mapTypeId: maps.MapTypeId.ROADMAP
//     //     }
//     //     var map = new maps.Map(document.getElementById("map"), myOptions);
//     //
//     //     var decodedPath = maps.geometry.encoding.decodePath('}~kvHmzrr@ba\\hnc@jiu@r{Zqx~@hjp@pwEhnc@zhu@zflAbxn@fhjBvqHroaAgcnAp}gAeahAtqGkngAinc@_h|@r{Zad\\y|_D}_y@swg@ysg@}llBpoZqa{@xrw@~eBaaX}{uAero@uqGadY}nr@`dYs_NquNgbjAf{l@|yh@bfc@}nr@z}q@i|i@zgz@r{ZhjFr}gApob@ff}@laIsen@dgYhdPvbIren@');
//     //     var decodedLevels = decodeLevels("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
//     //
//     //     var setRegion = new maps.Polyline({
//     //         path: decodedPath,
//     //         levels: decodedLevels,
//     //         strokeColor: "#FF0000",
//     //         strokeOpacity: 1.0,
//     //         strokeWeight: 2,
//     //         map: map
//     //     });
//     //
//     // }
//
//     render() {
//         return (
//             <div className="container" >
//                 <GoogleMapReact
//                 bootstrapURLKeys={{ key: "" }}
//                 defaultCenter={this.props.center}
//                 defaultZoom={this.props.zoom}
//                 onGoogleApiLoaded={({map, maps}) => this.renderMarkers(map, maps)}
//                 onClick={this._onClick}
//                 >
//                 </GoogleMapReact>
//             </div>
//         );
//     }
// }
import React from "react"
import { compose, withProps, lifecycle } from "recompose"
import { withScriptjs, withGoogleMap, GoogleMap, Marker, DirectionsRenderer } from "react-google-maps"
// const { compose, withProps, } = require("recompose");
// const {
//     withScriptjs,
//     withGoogleMap,
//     GoogleMap,
//     DirectionsRenderer,
// } = require("react-google-maps");

const MapWithADirectionsRenderer = compose(
    withProps({
        googleMapURL: "https://maps.googleapis.com/maps/api/js?key=AIzaSyB43agit7BDYyF6z6CdRuupfdeUMshOmbg&v=3.exp&libraries=geometry,drawing,places",
        loadingElement: <div style={{ height: `100%` }} />,
        containerElement: <div style={{ height: `600px` }} />,
        mapElement: <div style={{ height: `100%` }} />,
    }),
    withScriptjs,
    withGoogleMap,
    lifecycle({
        componentDidMount() {
            const DirectionsService = new window.google.maps.DirectionsService();

            DirectionsService.route({
                origin: new window.google.maps.LatLng(37.773972, -122.431297),
                destination: new window.google.maps.LatLng(37.793321, -122.422794),
                travelMode: window.google.maps.TravelMode.WALKING,
            }, (result, status) => {
                if (status === window.google.maps.DirectionsStatus.OK) {
                    this.setState({
                        directions: result,
                    });
                } else {
                    console.error(`error fetching directions ${result}`);
                }
            });

        }
    })
)(props =>
    <GoogleMap
        defaultZoom={13}
        defaultCenter={new window.google.maps.LatLng(37.773972, -122.431297 )}
    >
        {props.directions && <DirectionsRenderer directions={props.directions} />}
    </GoogleMap>
);


// const MyMapComponent = compose(
//     withProps({
//         googleMapURL: "https://maps.googleapis.com/maps/api/js?key=AIzaSyB43agit7BDYyF6z6CdRuupfdeUMshOmbg&v=3.exp&libraries=geometry,drawing,places",
//         loadingElement: <div style={{ height: `100%` }} />,
//         containerElement: <div style={{ height: `600px` }} />,
//         mapElement: <div style={{ height: `100%` }} />,
//     }),
//     withScriptjs,
//     withGoogleMap
// )((props) =>
//     <GoogleMap
//         defaultZoom={13}
//         defaultCenter={{ lat: 37.773972, lng: -122.431297 }}
//         onClick={props.onMapClick}
//     >
//         {props.isMarkerShown && <Marker position={{ lat: 37.793321, lng: -122.422794 }} onClick={props.onMarkerClick} />}
//     </GoogleMap>
// )

export class Map extends React.PureComponent {
    state = {
        isMarkerShown: false,
    }

    onMapClick(){

    }
    componentDidMount() {
        this.delayedShowMarker()
    }

    delayedShowMarker = () => {
        setTimeout(() => {
            this.setState({ isMarkerShown: true })
        }, 1000)
    }

    handleMarkerClick = () => {
        this.setState({ isMarkerShown: false })
        this.delayedShowMarker()
    }

    render() {
        return (
            <div id="container">
                {/*<MyMapComponent*/}
                    {/*isMarkerShown={this.state.isMarkerShown}*/}
                    {/*onMarkerClick={this.handleMarkerClick}*/}
                {/*/>*/}
                <MapWithADirectionsRenderer  />
            </div>
        )
    }
}