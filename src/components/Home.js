import React from 'react';
import { Map } from './Map';
import { OrderInfo } from "./OrderInfo"
import { Col } from 'antd';
import {MAP_API_KEY} from '../constants';
export class Home extends React.Component{
    state = { response : [

        ] }

handleResponse = (responseValue) => {
        this.setState({response: responseValue});
        console.log(this.state.response);
        this.props.handlerResponse(responseValue);
    }

    render(){
        return(
            <div className="home">
                <Col span={14}>
                   {/*<Map response={this.state.response}/>*/}
                    <Map
                        googleMapURL={`https://maps.googleapis.com/maps/api/js?key=${MAP_API_KEY}&libraries=geometry,drawing,places`}
                        loadingElement={<div style={{ height: `100%` }} />}
                        containerElement={<div style={{ height: `900px` }} />}
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