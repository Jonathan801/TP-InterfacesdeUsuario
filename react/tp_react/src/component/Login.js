import {Form,Button} from "react-bootstrap";
import React,{Component} from "react";
import styles from '../App.module.css'
import icon from  '../view/walletIcon.png'
import axios from 'axios'
import api from "../api/api";
import {withRouter} from 'react-router'
import {ErrorLogin} from './LoginComponents'


class Login extends  Component {
    constructor(props) {
        super(props);
        this.state = {
            correo: "",
            password: "",
            user: {},
            checkLogin: null
        }
    }

    checkLogin = (messageError) => <ErrorLogin error={messageError}/>

    routeChange = (path) =>{
        // let path = `/register`;
            this.props.history.push(path)
    }

    logear= () => {
        // axios.post('http://localhost:7000/users/login', {email: this.state.correo, password: this.state.password})
        //     .then(response => {
        //             this.setState({user: response.data});
        //             this.props.asignUserInParent(response.data);
        //             this.props.history.push(`/logIn/${this.state.user.firstName || this.state.user.lastName}`)
        //         }
        //     )
        //     .catch(error => this.setState({checkLogin: this.checkLogin(error.response.data)}))
        let body = {email: this.state.correo, password: this.state.password}
        api.login(body)
             .then(data => {
                 this.setState({user:data})
                 this.props.asignUserInParent(data)
                 this.props.history.push(`/home/${data.account.cvu}`)
            })
            .catch(error => this.setState({checkLogin: this.checkLogin(error)}))

    }

    handleEmail = (event) =>{
        this.setState({correo:event.target.value.trim()})
    }

    handlePassword = (event) =>{
        this.setState({password:event.target.value.trim()})
    }

    render() {
        return (
           <div className={styles.framePages}> 
             <div className={styles.titleLogin}>
                <img className={styles.icon} src={icon} alt='Icon'/>
                <span className={styles.texto}>DigitalWallet</span>
             </div>    
              <div className={styles.container}>
                <Form className={styles.formLogin}>
                    {this.state.checkLogin}                
                    <Form.Group className={styles.formularioLogin}>
                        <Form.Label>Email</Form.Label>
                        <Form.Control type="email" placeholder="Ingresar Correo" value={this.state.correo}
                                      onChange={this.handleEmail}/>
                    </Form.Group>
                    <Form.Group className={styles.formularioLogin}>
                        <Form.Label>Password</Form.Label>
                        <Form.Control  type="password" placeholder="Password" value={this.state.password} onChange={this.handlePassword}/>
                    </Form.Group>

                    <Button className={styles.loginPageButtons} variant="primary"  onClick={this.logear}>
                        Login
                    </Button>
                    <br/>
                    <br/>
                    <Button className={styles.loginPageButtons} variant="success" type="button" onClick={() => {
                        this.routeChange('/register')
                    }}>
                        Register
                    </Button>
                </Form>
            </div>
         </div>   
        );
    }
}

export default withRouter(Login)