import React from 'react';
import { Map } from './Map';
import { OrderInfo } from "./OrderInfo"
import { Col } from 'antd';

export class Home extends React.Component{
    state = { response : [    {
            "duration": 477,
            "mode": "FLYING",
            "route": [
                {
                    "duration": 136,
                    "distance": 3413,
                    "polyline": {
                        "points": "cmtgE`{fjUvNtbF"
                    }
                },
                {
                    "duration": 341,
                    "distance": 8540,
                    "polyline": {
                        "points": "k}sgEv~mjUz`I_gL"
                    }
                }
            ],
            "distance": 11953,
            "size": "SMALL",
            "price": 18.444499721625707,
            "overview_polyline": {
                "points": "cmtgE`{fjUvNtbFz`I_gL"
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
                        googleMapURL="https://maps.googleapis.com/maps/api/js?key=AIzaSyD3CEh9DXuyjozqptVB5LA-dN7MxWWkr9s&v=3.exp&libraries=geometry,drawing,places"
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