
import React from 'react';
import {withScriptjs, withGoogleMap, GoogleMap, Polyline} from "react-google-maps";
// import { AroundMarker } from './AroundMarker';
import {SF_COORD} from "../constants";

class NormalAroundMap extends React.Component {

    // reloadMarkers = () => {
    //     const center = this.getCenter();
    //     const radius = this.getRadius();
    //
    //     this.props.loadNearbyPosts(center, radius);
    // }

    // getCenter = () => {
    //     const center = this.map.getCenter();
    //     return {
    //         lat: center.lat(),
    //         lon: center.lng()
    //     };
    // }

    // getRadius = () => {
    //     const center = this.map.getCenter();
    //     const bounds = this.map.getBounds();
    //     if (center && bounds) {
    //         const ne = bounds.getNorthEast();
    //         const right = new window.google.maps.LatLng(center.lat(), ne.lng());
    //         return 0.001 * window.google.maps.geometry.spherical.computeDistanceBetween(center, right);
    //     }
    // }
    addLatLngToPoly = (latLng, poly) =>{
        const path = poly.getPath();
        path.push(latLng);

        const decodedPath = new window.google.maps.geometry.encoding.decodePath(
            '}~kvHmzrr@ba\\hnc@jiu@r{Zqx~@hjp@pwEhnc@zhu@zflAbxn@fhjBvqHroaAgcnAp}gAeahAtqGkngAinc@_h|@r{Zad\\y|_D}_y@swg' +
            '@ysg@}llBpoZqa{@xrw@~eBaaX}{uAero@uqGadY}nr@`dYs_NquNgbjAf{l@|yh@bfc@}nr@z}q@i|i@zgz@r{ZhjFr}gApob@ff}@laIsen@dgYhdPvbIren@'
        );


    }

    getMapRef = (instance) => {
        this.map = instance;
    }

    render() {
        // const { lat, lon: lng } = JSON.parse(localStorage.getItem(POS_KEY));
        const decodedPath = new window.google.maps.geometry.encoding.decodePath(
            '}~kvHmzrr@ba\\hnc@jiu@r{Zqx~@hjp@pwEhnc@zhu@zflAbxn@fhjBvqHroaAgcnAp}gAeahAtqGkngAinc@_h|@r{Zad\\y|_D}_y@swg' +
                '@ysg@}llBpoZqa{@xrw@~eBaaX}{uAero@uqGadY}nr@`dYs_NquNgbjAf{l@|yh@bfc@}nr@z}q@i|i@zgz@r{ZhjFr}gApob@ff}@laIsen@dgYhdPvbIren@'
            );
        return (
            <GoogleMap
                ref={this.getMapRef}
                defaultZoom={13}
                defaultCenter={SF_COORD}
            >
            <Polyline
                path={decodedPath}
                geodesic={true}
                options={{
                    strokeColor: "#42b9f4",
                    strokeOpacity: 0.75,
                    strokeWeight: 4,

                }}/>

            </GoogleMap>
        );
    }
}

export const Map = withScriptjs(withGoogleMap(NormalAroundMap));