import React from 'react';
import { Map } from './Map';
import { UserInformation} from "./UserInformation";
import { OrderInfo } from "./OrderInfo";

export class Home extends React.Component{
    render(){
        return(
            <div className="maps">
               <Map/>
               <OrderInfo/>
               <UserInformation/>
            </div>
        )
    }
}