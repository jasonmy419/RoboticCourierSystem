import React from 'react';
import { Map } from './Map';
import { OrderInfo } from "./OrderInfo"
import { Col } from 'antd';
import {MAP_API_KEY} from '../constants';
export class Home extends React.Component{
    state = { response : [ {
            "duration": 2771,
            "mode": "WALKING",
            "route": [
                {
                    "duration": 45,
                    "distance": 60,
                    "polyline": {
                        "points": "cntgEb{fjU@N@L@JBLDLVn@"
                    }
                },
                {
                    "duration": 69,
                    "distance": 93,
                    "polyline": {
                        "points": "{ltgEx~fjURKLGJELCFAFAPCXAf@A"
                    }
                },
                {
                    "duration": 1009,
                    "distance": 1383,
                    "polyline": {
                        "points": "{gtgEn}fjUG`G?^@\\@f@B`@JfBH`At@pJBd@RlCTtCRxBd@tELtABTLpATxBd@zFLdBDZXvD^rE\\nEBT"
                    }
                },
                {
                    "duration": 603,
                    "distance": 767,
                    "polyline": {
                        "points": "{{sgEvxijU@PN`Bd@vGd@lGVrDB^?B@B@P?@@HB\\TxCBNVhDBZ\\vG?HBN"
                    }
                },
                {
                    "duration": 32,
                    "distance": 35,
                    "polyline": {
                        "points": "_usgEjkkjUS@C?AAECGCAAA?C?E?E@"
                    }
                },
                {
                    "duration": 36,
                    "distance": 46,
                    "polyline": {
                        "points": "{vsgEbkkjUCDQRe@f@"
                    }
                },
                {
                    "duration": 28,
                    "distance": 36,
                    "polyline": {
                        "points": "wxsgEdmkjU?N?|@"
                    }
                },
                {
                    "duration": 94,
                    "distance": 131,
                    "polyline": {
                        "points": "wxsgErokjUo@CW@QBSDOFSJKHw@r@"
                    }
                },
                {
                    "duration": 557,
                    "distance": 772,
                    "polyline": {
                        "points": "m_tgElrkjUT^Td@NZJZFXFX@L@LBV@Z?`@?L?\\?D?^?nB?jBAP?rCAf@?|D?HAzC?X?zA?hFApB"
                    }
                },
                {
                    "duration": 121,
                    "distance": 163,
                    "polyline": {
                        "points": "s|sgEbemjUBV?HALAN?HEXALCLCLCNGP[vACPCDEZCP"
                    }
                },
                {
                    "duration": 20,
                    "distance": 24,
                    "polyline": {
                        "points": "}~sgEpomjUj@J"
                    }
                },
                {
                    "duration": 157,
                    "distance": 221,
                    "polyline": {
                        "points": "q}sgE|omjUEVKh@SdBGj@CVA`@GhAAb@EtA"
                    }
                }
            ],
            "station_point": {
                "city": "San Diego",
                "street_number": "5716",
                "station_lat": 32.8777831,
                "station_lon": -117.188114,
                "street_name": "Miramar Rd"
            },
            "distance": 3731,
            "size": "SMALL",
            "courier": "211",
            "price": 8.945838503141653,
            "destination_point": {
                "destination_point_lon": -117.154692,
                "city": "San Diego",
                "destination_point_lat": 32.8238827,
                "street_number": "4609",
                "street_name": "Convoy St"
            },
            "overview_polyline": {
                "points": "cntgEb{fjUB\\DX\\|@`@Sh@Mj@Ef@AG`G@|@DhAnA`Qh@bHx@nIt@vHvC`_@zBtZZbEZxD`@rHBXW@GEOEK@UXe@f@?N?|@o@Ci@Dc@L_@Tw@r@T^d@`ARt@Hf@Dd@@hBClSAzGAzIB`@I`AMx@k@`CIl@j@JEV_@nCKbAQdFPeFJcA^oCbAaGB]e@EB[@WAiC@{N?gE@g@zB?jBAd@?dMDvH@~D?|BKtB[bDy@rBi@tA]xHgBTIrBq@dB_AhBwAjAqAh@q@\\e@hAqBxDgHjEyHf@u@|@uAh@o@f@g@p@c@zAs@rGwA|JqBtB]tI_BjAKbACzG`@rBHv@?n@EjAS~@]`Ag@nAcA`D{DtBiCxBgCnDeEtDkEtAwA`CgBfB_ApBs@bAWhBg@fE_A~KuC`D{@~@]zFiCtAIlA?T??kP?kMBqJH}^H}e@BkGBuANyBPiB|BoT`A_Jx@sHXqCTeCHkDAsGGkS?iCr@Cd@G`Gy@|UaDdHaAdAMAgAAqB?gE^@nA?FELeBlA@?mC^?D?p@E"
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