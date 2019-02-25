
import React from 'react';
import {withScriptjs, withGoogleMap, GoogleMap, Polyline} from "react-google-maps";
// import { AroundMarker } from './AroundMarker';
import {SF_COORD} from "../constants";
import {CustomizedMarker} from "./CustomizedMarker"
import {StationMarker} from "./StationMarker";

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
    // addLatLngToPoly = (latLng, poly) =>{
    //     const path = poly.getPath();
    //     path.push(latLng);
    //
    //     const decodedPath = new window.google.maps.geometry.encoding.decodePath(
    //     //         '}~kvHmzrr@ba\\hnc@jiu@r{Zqx~@hjp@pwEhnc@zhu@zflAbxn@fhjBvqHroaAgcnAp}gAeahAtqGkngAinc@_h|@r{Zad\\y|_D}_y@swg' +
    //     //         '@ysg@}llBpoZqa{@xrw@~eBaaX}{uAero@uqGadY}nr@`dYs_NquNgbjAf{l@|yh@bfc@}nr@z}q@i|i@zgz@r{ZhjFr}gApob@ff}@laIsen@dgYhdPvbIren@'
    //     //     );
    //
    //
    // }
    // toggleRoute = () => {
    //     this.setState((prevState) => ({
    //         isRouteGiven: !prevState.isRouteGiven
    //     }));
    // }
    getMapRef = (instance) => {
        this.map = instance;
    }
    // state ={
    //     isRouteGiven: true
    // }
    getCenter = (dest, waypoint) => {
        if(this.props.response.length > 0){
            const destLat = this.props.response[0].destination_point.destination_point_lat;
            const destLon = this.props.response[0].destination_point.destination_point_lon;
            const wayptLat = this.props.response[0].way_point.way_point_lat;
            const wayptLon = this.props.response[0].way_point.way_point_lon;
            const center = {lat: (dest.lat + waypoint.lat)/2, lng: (dest.lon + waypoint.lon)/2};
            return(center);
        }
        return(SF_COORD);
    }

    render() {
        // console.log(this.props.response.length > 0 ? this.props.response[0].mode : null);
        // const { lat, lon: lng } = JSON.parse(localStorage.getItem(POS_KEY));
        const dest = this.props.response.length > 0 ? {
                city: this.props.response[0].destination_point.city,
                lat: this.props.response[0].destination_point.destination_point_lat,
                lon: this.props.response[0].destination_point.destination_point_lon,
                stName: this.props.response[0].destination_point.street_name,
                stNum: this.props.response[0].destination_point.street_number
            } : null;
        const waypoint = this.props.response.length > 0 ? {
                city: this.props.response[0].way_point.city,
                lat: this.props.response[0].way_point.way_point_lat,
                lon: this.props.response[0].way_point.way_point_lon,
                stName: this.props.response[0].way_point.street_name,
                stNum: this.props.response[0].way_point.street_number
            } : null;
        const station = this.props.response.length > 0 ? {
            city: this.props.response[0].station_point.city,
            lat: this.props.response[0].station_point.station_lat,
            lon: this.props.response[0].station_point.station_lon,
            stName: this.props.response[0].station_point.street_name,
            stNum: this.props.response[0].station_point.street_number
        } : null;
        const decodedPath = this.props.response.length > 0 ? new window.google.maps.geometry.encoding.decodePath(
             this.props.response[0].overview_polyline.points
            ):null;
        return (


            <GoogleMap
                center = {this.getCenter(dest, waypoint)}
                ref={this.getMapRef}
                defaultZoom={13}
                defaultCenter={SF_COORD}
                //onClick={this.toggleRoute}
            >
                {
                    <Polyline
                    path={decodedPath}
                    geodesic={true}
                    options={{
                        strokeColor: "#42b9f4",
                        strokeOpacity: 0.75,
                        strokeWeight: 4,

                    }}/>

                }
                {this.props.response.length > 0 ? <CustomizedMarker point = {dest} point_type="destination"></CustomizedMarker> : null}
                {this.props.response.length > 0 ?<CustomizedMarker point = {waypoint} point_type="waypoint"></CustomizedMarker> : null}
                {this.props.response.length > 0 ? <StationMarker point = {station} point_type="station"></StationMarker> : null}

            </GoogleMap>
        );
    }
}

export const Map = withScriptjs(withGoogleMap(NormalAroundMap));