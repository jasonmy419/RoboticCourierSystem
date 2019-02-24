import React from 'react';
import { Map } from './Map';
import { OrderInfo } from "./OrderInfo"
import { Col } from 'antd';
import {MAP_API_KEY} from '../constants';
export class Home extends React.Component{
    state = { response : [ {
            "duration": 574,
            "mode": "FLYING",
            "route": [
                {
                    "duration": 234,
                    "distance": 5854,
                    "polyline": {
                        "points": "auigE|xnjUigIeY"
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
            "distance": 14373,
            "size": "SMALL",
            "courier": "311",
            "price": 48.568174063469336,
            "destination_point": {
                "destination_point_lon": -117.154692,
                "city": "San Diego",
                "destination_point_lat": 32.8238827,
                "street_number": "4609",
                "street_name": "Convoy St"
            },
            "overview_polyline": {
                "points": "auigE|xnjUigIeYb`I}eL"
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