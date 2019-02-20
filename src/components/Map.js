
import React from "react"
import { compose, withProps, lifecycle } from "recompose"
import { withScriptjs, withGoogleMap, GoogleMap, Marker, DirectionsRenderer } from "react-google-maps"


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
                <p>{this.props.response}</p>
            </div>
        )
    }
}
// class NormalAroundMap extends React.Component {
//
//     reloadMarkers = () => {
//         const center = this.getCenter();
//         const radius = this.getRadius();
//
//         this.props.loadNearbyPosts(center, radius);
//     }
//
//     getCenter = () => {
//         const center = this.map.getCenter();
//         return {
//             lat: center.lat(),
//             lon: center.lng()
//         };
//     }
//
//     getRadius = () => {
//         const center = this.map.getCenter();
//         const bounds = this.map.getBounds();
//         if (center && bounds) {
//             const ne = bounds.getNorthEast();
//             const right = new window.google.maps.LatLng(center.lat(), ne.lng());
//             return 0.001 * window.google.maps.geometry.spherical.computeDistanceBetween(center, right);
//         }
//     }
//
//     getMapRef = (instance) => {
//         this.map = instance;
//     }
//
//     render() {
//         const { lat, lon: lng } = JSON.parse(localStorage.getItem(POS_KEY));
//         return (
//             <GoogleMap
//                 ref={this.getMapRef}
//                 defaultZoom={11}
//                 defaultCenter={{ lat, lng }}
//                 onDragEnd={this.reloadMarkers}
//                 onZoomChanged={this.reloadMarkers}
//             >
//                 {
//                     this.props.posts.map((post) => <AroundMarker post={post} />)
//                 }
//
//             </GoogleMap>
//         );
//     }
// }
//
// export const AroundMap = withScriptjs(withGoogleMap(NormalAroundMap));