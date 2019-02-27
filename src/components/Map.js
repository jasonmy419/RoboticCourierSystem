
import React from 'react';
import {withScriptjs, withGoogleMap, GoogleMap, Polyline} from "react-google-maps";
import {SF_COORD} from "../constants";
import {CustomizedMarker} from "./CustomizedMarker"
import {StationMarker} from "./StationMarker";


class NormalAroundMap extends React.Component {
    getMapRef = (instance) => {
        this.map = instance;
    }


    getCenter = (dest, waypoint ,station) => {
        if(this.props.response.length > 0){
            const center = {lat: (dest.lat + waypoint.lat+station.lat)/3, lng: (dest.lon + waypoint.lon+station.lon)/3};
            let positions = [
                {lat: dest.lat, lng: dest.lon},
                {lat: waypoint.lat, lng: waypoint.lon},
                {lat: station.lat, lng: station.lon}
            ];

            let bounds = new window.google.maps.LatLngBounds();
            positions.forEach(({lat, lng}) => bounds.extend(new window.google.maps.LatLng(lat, lng)));
            // this.getMapRef();
            if(this.map) {
                this.map.fitBounds(bounds);
            }

            return(center);
        }

        return(SF_COORD);
    }

    render() {
         console.log("response",this.props.response);
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

                ref={this.getMapRef}
                center = {this.getCenter(dest, waypoint, station)}
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