import React,{Component} from 'react';
import Login from "./component/Login";

import 'bootstrap/dist/css/bootstrap.min.css';
import Home from './component/Home'
import RegisterPage from './component/RegisterPage'
import CashIn from "./component/CashIn";
import {BrowserRouter,Link,Switch,Router,Route,NavLink} from "react-router-dom";
import NotFoundPage from "./component/NotFoundPage";
import TransferPage from "./component/TransferPage";
import UserProfile from "./component/UserProfile"
import styles from './App.module.css'
import 'react-bootstrap'
import wall from "./view/walletIcon.png"

class App extends Component {
  constructor(props){
    super(props)
    this.state = {
      user:undefined
    }
  }

  getUserFromChild = (user) => {
      this.setState({user:user})
  }

  returnUser = () =>{
    return this.state.user
  }

  resetUser = () => {
    this.setState({user:undefined})
  } 

  render(){
    return (
        <BrowserRouter>
            <NavBar user={this.state.user} resetUser={this.resetUser}/>
            <Switch>
                    <Route exact path="/" render={props => <Login asignUserInParent={this.getUserFromChild} />}/>
                    <Route exact path="/register" render={props=> <RegisterPage asignUserInParent={this.getUserFromChild}/>} />
                    <Route exact path="/home/:cvu" render={props => <Home getUserFromParent={this.returnUser} />} />
                    <Route exact path="/home/:cvu/transfer" render={props=> <TransferPage getUserFromParent={this.returnUser} />} />
                    <Route exact path="/home/:cvu/cashIn" render={props=> <CashIn getUserFromParent={this.returnUser}/>} />
                    <Route exact path="/home/:cvu/profile" render={props=> <UserProfile getUserFromParent={this.returnUser} updateUser={this.getUserFromChild}/>} />
                    <Route path="*" component={NotFoundPage} />
            </Switch>
        </BrowserRouter>);
  }
}
//
// const NavBar = props =>{
//   return props.user ?
//           (<nav className={styles.nav} >
//               <NavLink className="nav-link" onClick={props.resetUser} to='/'><h6>Log Out</h6></NavLink>
//           </nav>) : null
// }

const NavBar = props =>{
    return props.user ?
        (<nav className="navbar navbar-dark bg-dark" id={styles.nav} >
            <img src={wall} width="50" height="50"/>
            <NavLink className="nav-link" onClick={props.resetUser} to='/'>Log Out</NavLink>
        </nav>) : null
}


export default App;
