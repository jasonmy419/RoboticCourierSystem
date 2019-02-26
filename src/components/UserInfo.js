// import {
//     Menu, Dropdown, Icon, message, Avatar
// } from 'antd';
// import React, {Component} from "react";
// import {Link, Redirect} from "react-router-dom";
//
//
// const onClick = ({ key }) => {
//     //message.info(`Click on item ${key}`);
//     if(key===1){
//         console.log("1")
//         return(<Redirect to="/orders"/>)
//     }
// };
// const handleOrders = () =>{
//     return(<Redirect to="/home"/>)
// }
// const menu = (
//     <Menu onClick={onClick}>
//         <Menu.Item key="1" href="./home">Orders</Menu.Item>
//         <Menu.Item key="2">2nd memu item</Menu.Item>
//         <Menu.Item key="3">3rd menu item</Menu.Item>
//     </Menu>
// );
//
// export class UserInfo extends React.Component{
//     render() {
//         return(
//             <Dropdown overlay={menu}>
//                 <a className="ant-dropdown-link">
//                     <Avatar icon="user"/> <Icon type="down" />
//                 </a>
//             </Dropdown>)
//     }
//
// }
