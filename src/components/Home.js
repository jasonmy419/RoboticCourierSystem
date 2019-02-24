import React from 'react';
import { Map } from './Map';
import { OrderInfo } from "./OrderInfo"
import { Col } from 'antd';
import {MAP_API_KEY} from '../constants';
export class Home extends React.Component{
    state = { response : [
            {
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
                        "duration": 30,
                        "distance": 36,
                        "polyline": {
                            "points": "s|sgEbemjUBV?HALANAJ"
                        }
                    },
                    {
                        "duration": 19,
                        "distance": 21,
                        "polyline": {
                            "points": "u|sgEngmjUd@D"
                        }
                    },
                    {
                        "duration": 249,
                        "distance": 351,
                        "polyline": {
                            "points": "o{sgEtgmjUC\\[`BO|@QhAEVKh@SdBGj@CVA`@GhAAb@EtA"
                        }
                    }
                ],
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
                    "points": "cntgEb{fjUB\\DX\\|@`@Sh@Mj@Ef@AG`G@|@DhAnA`Qh@bHx@nIt@vHvC`_@zBtZZbEZxD`@rHBXW@GEOEK@UXe@f@?N?|@o@Ci@Dc@L_@Tw@r@T^d@`ARt@Hf@Dd@@hBClSAzGAzIB`@C\\AJd@D_@~Ba@fCQ`A[pCOfDEtADuAHmBDy@ZqCr@iE^_Ce@E@KB]Ca@@qMBkKhGArQFhI@`BE~@GtB[|DcA`Dw@fImBb@OjBo@|@c@vAaAfBaBn@u@b@o@|@{ApBwDbIoN`BaC|@aAp@c@bAg@~@YrBe@fO{CbBWxGoAxAUjBIhEVtERt@AvAOt@QtAo@b@Wt@m@j@o@dBwBhFoGbE{En@w@|DmEzAuAlAy@l@]nB{@xAe@nCs@fE_AzFyA~F}AdCw@zFiCtAIbB??kb@Ru_ABoRDeCLmCPgBLeBlAeLrAsLf@eEn@wG`@aEHgABcDAiBEcUAuEhAGhEm@lVeD~G_AbC[A}AAcHjB@HAFq@Fw@f@@d@??mC`@?b@CPA"
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