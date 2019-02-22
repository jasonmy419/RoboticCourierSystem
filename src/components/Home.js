import React from 'react';
import { Map } from './Map';
import { OrderInfo } from "./OrderInfo"
import { Col } from 'antd';

export class Home extends React.Component{
    state = { response : [] }

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