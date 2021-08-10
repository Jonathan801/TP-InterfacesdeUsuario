import React,{Component} from 'react'
import { Button,Form,FormLabel,FormControl,FormCheck } from 'react-bootstrap';
import styles from '../App.module.css'
import walletIcon from '../view/walletIcon.png'
import api from '../api/api'
import {withRouter} from 'react-router'
import {ErrorRegister, updateError} from './RegisterComponents'

class RegisterPage extends Component{
    constructor(props){
        super(props)
        this.state={
            email:'',
            firstName:'',
            lastName:'',
            idCard:'',
            password:'',
            checkRegister: null
        }

        this.handleEmail = this.handleEmail.bind(this)
        this.handleFirstName = this.handleFirstName.bind(this)
        this.handleLastName = this.handleLastName.bind(this)
        this.handleIdCard = this.handleIdCard.bind(this)
        this.handlePassword = this.handlePassword.bind(this)
    }

    handleEmail = (event)=>{      
       this.setState({email:event.target.value.trim()})
    }
    handleFirstName = (event)=>{
       this.setState({firstName:event.target.value.trim()})
    }
    handleLastName = (event)=>{
       this.setState({lastName:event.target.value.trim()})
    }
    handleIdCard = (event)=>{
      let number = Number(event.target.value.trim())
      if(!isNaN(number)){      
        this.setState({idCard:event.target.value.trim()})
      }  
    }
    handlePassword = (event)=>{
        this.setState({password:event.target.value.trim()})
    }

    handleBack = () =>{
      this.props.history.push('/')
    }

    checkRegister = (errorMessage) => <ErrorRegister error={errorMessage}/>

    handleRegister = () =>{
        let body = {email:this.state.email,firstName:this.state.firstName,idCard:this.state.idCard,
                    lastName:this.state.lastName,password:this.state.password}
        // axios.post('http://localhost:7000/users/register',
        // {email:this.state.email,firstName:this.state.firstName,idCard:this.state.idCard,lastName:this.state.lastName,password:this.state.password})
        //   .then(response => {
        //     this.props.asignUserInParent(response.data);
        //     this.props.history.push(`/logIn/${response.data.firstName || response.data.lastName}`)
        //   })
        //   .catch(err => {
        //     console.log('MIRA ESTE ERROR', err.response) 
        //     this.setState({checkRegister:this.checkRegister(err.response.data)})
        //     updateError(err.response.data)
        //   })
        api.registerUser(body)
           .then(data =>{
              this.props.asignUserInParent(data)
              this.props.history.push(`/home/${data.account.cvu}`)
           })
           .catch(error =>{
             this.setState({checkRegister:this.checkRegister(error)})
             updateError(error)
           })
           
    }
  
    render(){
        return (
              
          <div className={styles.framePages}> 
            <div className={styles.title}>
              <img className={styles.iconTitle} src={walletIcon} alt="digital wallet Icon"/> 
              <span className={styles.texto}>DigitalWallet</span>              
            </div>
            <div>
            <img className={styles.iconWallet} src={walletIcon} alt="digitalWalletIcon"/>
             {this.state.checkRegister}
             <div className={styles.container}>
                <Form className={styles.formRegister}>
                  <div>
                  <Form.Group>
                    <Form.Label >Email</Form.Label>
                    <Form.Control className={styles.registerControls} type="email" placeholder="Email" value={this.state.email} onChange={this.handleEmail} />
                  </Form.Group>
                  </div>
                  <div>
                  <Form.Group >
                    <Form.Label>First Name</Form.Label>
                    <Form.Control className={styles.registerControls} type="text" placeholder="FirstName" value={this.state.firstName} onChange={this.handleFirstName} />
                  </Form.Group>
                  </div>
                  <div>
                  <Form.Group>
                    <Form.Label>Last Name</Form.Label>
                    <Form.Control className={styles.registerControls} type="text" placeholder="Last Name" value={this.state.lastName} onChange={this.handleLastName} />
                  </Form.Group>
                  </div>
                  <div>
                  <Form.Group>
                    <Form.Label>idCard</Form.Label>
                    <Form.Control className={styles.registerControls} type="tel" placeholder="idCard" value={this.state.idCard} onChange={this.handleIdCard} />
                  </Form.Group>
                  </div>
                  <div>
                  <Form.Group>
                    <Form.Label>Password</Form.Label>
                    <Form.Control className={styles.registerControls} type="password" placeholder="Password" value ={this.state.password} onChange={this.handlePassword}/>
                  </Form.Group>
                  </div>
                  <div>
                  <Form.Group>
                  <Button className={styles.regRegister} variant="primary" onClick={this.handleRegister}> Register </Button><br/>
                  <Button className={styles.regBack} variant="secondary"onClick={this.handleBack}> Back </Button>
                  </Form.Group>
                  </div>
                </Form>                
              </div>
            </div>
          </div>
            
        );
    }

}

export default withRouter(RegisterPage)