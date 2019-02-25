import React, { Component } from 'react';
import { Marker, InfoWindow } from "react-google-maps";
import greenMarkerUrl from '../assets/images/green-marker.svg';

export class StationMarker extends React.Component {
    state = {
        isOpen: false
    }
    toggleOpen = () => {
        this.setState((prevState) => ({
            isOpen: !prevState.isOpen
        }));
    }
    render() {
        console.log(this.props);
        const{ city, stName, stNum, lat, lon} = this.props.point;
        //
        // if(this.props.point_type == "dest"){
        //     lat = this.props.point.destination_point_lat;
        //     lon = this.props.point.destination_point_lon;
        // }else{
        //     lat = this.props.point.way_point_lat;
        //     lon = this.props.point.way_point_lon;
        // }
        // const city = this.props.point.city;
        // const stNum = this.props.point.street_number;
        // const stName = this.props.point.street_name;
        const icon = {
            url: greenMarkerUrl,
            scaledSize: new window.google.maps.Size(57, 57)
        };
        return (
            <Marker
                icon={icon}
                position={{lat: lat, lng: lon }}
                onMouseOver={this.toggleOpen}
                onMouseOut={this.toggleOpen}
            >
                {   this.state.isOpen ?
                    <InfoWindow>
                        <div>
                            <p>{`Station: ${stNum} ${stName},  ${city}`}</p>
                        </div>
                    </InfoWindow> : null
                }
            </Marker>
        );
    }
}