import React from 'react';
import { Map } from './Map';
import { OrderInfo } from "./OrderInfo"
import { Col } from 'antd';
import {MAP_API_KEY} from '../constants';
export class Home extends React.Component{
    state = { response : [{
            "duration": 634,
            "mode": "FLYING",
            "route": [
                {
                    "duration": 294,
                    "distance": 7368,
                    "polyline": {
                        "points": "w~kgE|yajUs}FxcK"
                    }
                },
                {
                    "duration": 340,
                    "distance": 8519,
                    "polyline": {
                        "points": "k}sgEv~mjUb`I}eL"
                    }
                }
            ],
            "station_point": {
                "city": "San Diego",
                "street_number": "7373",
                "station_lat": 32.834517,
                "station_lon": -117.1623357,
                "street_name": "Convoy Ct"
            },
            "distance": 15887,
            "size": "SMALL",
            "courier": "222",
            "price": 53.47195398870596,
            "destination_point": {
                "destination_point_lon": -117.154692,
                "city": "San Diego",
                "destination_point_lat": 32.8238827,
                "street_number": "4609",
                "street_name": "Convoy St"
            },
            "overview_polyline": {
                "points": "w~kgE|yajUs}FxcKb`I}eL"
            },
            "way_point": {
                "city": "La Jolla",
                "way_point_lat": 32.8752583,
                "street_number": "3869",
                "way_point_lon": -117.2223574,
                "street_name": "Miramar St"
            }
        }
        ] }

handleResponse = (responseValue) => {
        this.setState({response: responseValue});
        console.log(this.state.response);
    }

    render(){
        return(
            <div className="home">
                <Col span={14}>
                   {/*<Map response={this.state.response}/>*/}
                    <Map
                        googleMapURL={`https://maps.googleapis.com/maps/api/js?key=${MAP_API_KEY}&libraries=geometry,drawing,places`}
                        loadingElement={<div style={{ height: `100%` }} />}
                        containerElement={<div style={{ height: `600px` }} />}
                        mapElement={<div style={{ height: `100%` }} />}
                        response={this.state.response}
                        // posts={this.state.posts}
                        // loadNearbyPosts={this.state.topic === "around" ? this.loadNearbyPosts: this.loadFacesAroundTheWorld}
                    />
                </Col>
                <Col span={10}>
                    <OrderInfo handleResponse={this.handleResponse} history={this.props.history}/>
                </Col>
            </div>
        )
    }
}