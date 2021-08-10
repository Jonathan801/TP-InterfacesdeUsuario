import React, {Component} from "react";
import styles from '../App.module.css';
import { Button,Form,FormLabel,FormControl,FormCheck } from 'react-bootstrap';
import {withRouter} from 'react-router'
import {ProfileError, updateError, updateOk} from './ProfileComponents'
import userIcon from '../view/userProfile.png'
import api from '../api/api'

class UserProfile extends Component{

    constructor(props){
        super(props)
        this.state ={
            user: props.getUserFromParent(),
            firstName:props.getUserFromParent().firstName,
            email:props.getUserFromParent().email,
            errorMessage: null
        }

    }

    handleEmail = (event) =>{
        this.setState({email:event.target.value.trim()}) 
    }

    handleFirstName = (event) =>{
        this.setState({firstName:event.target.value.trim()})
    }

    handleChanges = () =>{
        // axios.put(`http://localhost:7000/users/change/${this.state.user.account.cvu}`,{email:this.state.email,firstName:this.state.firstName})
        //     .then( response=> {
        //         this.setState({user:response.data})
        //         this.props.updateUser(response.data)
        //         this.props.history.push(`/logIn/${response.data.firstName || response.data.lastName}`)
        //     })
        //     .catch(error =>{
        //         this.setState({errorMessage:this.handleError(error.response.data)})
        //         updateError(error.response.data)
        //     })
        let body = {email:this.state.email,firstName:this.state.firstName}
        let cvu = this.state.user.account.cvu
        api.changeUser(cvu,body)
            .then(data =>{
                this.setState({user:data})
                this.props.updateUser(data)
                this.props.history.push(`/home/${data.account.cvu}`)
            })
            .catch(error => {
                console.log('updateUserProfile Error',error)
                this.setState({errorMessage:this.handleError(error)})
                updateError(error)
            })
    }

    handleError = (errorMessage) => <ProfileError error={errorMessage}/>

    handleBack = () =>{
        this.props.updateUser(this.state.user)
        this.props.history.push(`/home/${this.state.user.account.cvu}`)
    }

    render(){
        return(
            <div className={styles.framePages}>
                <div className={styles.container}>
                    <span><img className={styles.userIcon} src={userIcon} alt='CashIn Profile'/></span>
                    <span><h3 align='center'> Profile </h3></span>
                </div>
                <div className={styles.container}>
                    <Form className={styles.grid1}>
                        {this.state.errorMessage}
                        <div>
                            <Form.Label> First Name</Form.Label>
                            <Form.Control type="text" value={this.state.firstName} onChange={this.handleFirstName}></Form.Control>
                        </div>
                        <div>
                            <Form.Label> Last Name </Form.Label>
                            <Form.Control type="text" value={this.state.user.lastName} disabled />
                        </div>
                        <div>
                            <Form.Label> Email </Form.Label>
                            <Form.Control type="email" value={this.state.email} onChange={this.handleEmail} />
                        </div>
                        <div>
                            <Form.Label> CVU </Form.Label>
                            <Form.Control type="text" value={this.state.user.account.cvu} disabled></Form.Control>
                        </div>
                        <div>
                            <Form.Label> Amount </Form.Label>
                            <Form.Control type="text" value={this.state.user.account.amount} disabled></Form.Control>
                        </div>
                        <div className={styles.burr}>
                            <Button className={styles.regRegister} variant="primary" onClick={this.handleChanges}> Save </Button><br />
                            <Button className={styles.regBack} variant="secondary" onClick={this.handleBack}> Back </Button>
                        </div>
                    </Form>
                </div>
            </div>
        )
    }

}

export default withRouter(UserProfile)