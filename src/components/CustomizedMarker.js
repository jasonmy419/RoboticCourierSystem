import React, { Component } from 'react';
import { Marker, InfoWindow } from "react-google-maps";
import blueMarkerUrl from '../assets/images/blue-marker.svg';

export class CustomizedMarker extends React.Component {
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
        const icon = this.props.point_type == "destination" ? null : {
            url: blueMarkerUrl,
            scaledSize: new window.google.maps.Size(26, 41)
        };
        console.log({city});
        console.log({lat});
        console.log({lon});
        console.log({stName});
        console.log({stNum});
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
                            <p>{`${stNum} ${stName},  ${city}`}</p>
                        </div>
                    </InfoWindow> : null
                }
            </Marker>
        );
    }
}