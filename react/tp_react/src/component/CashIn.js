import React, {Component} from "react";
import styles from '../App.module.css';
import {Button, Form, FormGroup} from "react-bootstrap";
import {withRouter} from 'react-router';
import api from '../api/api';
import {updateError} from "./RegisterComponents";
import cashInicon from "../view/cashInIcon.png";
import {CashInError , updateException} from './CashInComponents'

class CashIn extends Component {

    constructor(props) {
        super(props)
        this.state = {
            amount: '',
            card: '',
            fullName: '',
            secCode: '',
            endD: '',
            userActual: props.getUserFromParent(),
            selectedOption:'',
            checkCashInError: null
        }


        this.handleAmmount = this.handleAmmount.bind(this)
        this.handleFullname = this.handleFullname.bind(this)
        this.handlecard = this.handlecard.bind(this)
        this.handlesecCode = this.handlesecCode.bind(this)
        this.handlendD = this.handlendD.bind(this)
        
    }

    handleAmmount = (event) => {
        let number = Number(event.target.value.trim())
        if(!isNaN(number)){
         this.setState({amount: event.target.value.trim()})
        } 
    }

    handleFullname = (event) => {
       let regex = new RegExp("^[a-zA-Z ]+$")
       if(regex.test(event.target.value.trim())){
           this.setState({fullName:event.target.value.trim()})
       } else {
           if(event.target.value === ""){
               this.setState({fullName:event.target.value.trim()})
           }
       }
    //    }else{
    //        if(this.state.fullName.length === 1 && isNaN(Number(event.target.value.trim().charAt(0)))){
    //            this.setState({fullName:event.target.value.trim()})
    //        }
    //    }
    }

    handlecard = (event) => {
        let number = Number(event.target.value.trim())
        if(!isNaN(number)){
         this.setState({card: event.target.value.trim()})
        } 
    }

    handlesecCode = (event) => {
        let number = Number(event.target.value.trim())

        if(!isNaN(number)){
         this.setState({secCode: event.target.value.trim()})
        } 
    }

    handlendD = (event) => {
        this.setState({endD: event.target.value.trim()})
    }

    handleBack = () => {
        this.props.history.push(`/home/${this.state.userActual.account.cvu}`)
    }

    handleOptionChange = changeEvent => {
        this.setState({
            selectedOption: changeEvent.target.value
        });

    };

    checkError = (errorMessage) => <CashInError error={errorMessage}/>

    componentDidUpdate() {
        console.log('Mi estado actual es:',this.state)
        console.log('Mi cvu es:',this.state.userActual.account.cvu)

    }

    handleCashIn = () =>{
        // axios.post('http://localhost:7000/cashIn/',
        //     {fromCVU:this.state.userActual.account.cvu,amount:this.state.amount,fullName:this.state.fullName,cardNumber:this.state.card,endDate:this.state.endD,securityCode:this.state.secCode,type:this.state.selectedOption})
        //     .then(response=>{
        //         this.props.history.push(`/logIn/${this.state.userActual.firstName}`)
        //     })
            let body = {fromCVU:this.state.userActual.account.cvu,amount:this.state.amount,fullName:this.state.fullName,cardNumber:this.state.card,endDate:this.state.endD,securityCode:this.state.secCode,type:this.state.selectedOption}
            api.cashIn(body)
               .then(data => {
                   console.log('cashIn',data)
                   this.props.history.push(`/home/${this.state.userActual.account.cvu}`)
               })
               .catch(error => {
                   this.setState({checkCashInError:this.checkError(error)})
                   updateException(error)
               })
           
    }

    render() {
        return (
          <div className={styles.framePages}>
            {this.state.checkCashInError}
            <div className={styles.container}>
                <span><img className={styles.userIcon} src={cashInicon} alt='User Profile'/></span>
                <span><h3 align='center'> CashIn </h3></span>
            </div>
            <div className={styles.container}>
                <Form>
                    <div>
                        <Form.Group>
                            <Form.Label>Monto</Form.Label>
                            <Form.Control placeholder="Monto" value={this.state.amount} onChange={this.handleAmmount}/>
                        </Form.Group>
                    </div>
                        <Form.Group className={styles.radio}>
                          <Form.Check inline
                                    type="radio"
                                    label="Credit"
                                    value="Credit"
                                    name="inlineRadios"
                                    onChange={this.handleOptionChange}
                          />
                          <Form.Check inline
                                    type="radio"
                                    label="Debit"
                                    value="Debit"
                                    name="inlineRadios"
                                    onChange={this.handleOptionChange}
                          />
                        </Form.Group>
                        <div>
                            <Form.Group>
                                <Form.Label>Card Number</Form.Label>
                                <Form.Control placeholder="Card Number" value={this.state.card}
                                              onChange={this.handlecard}/>
                            </Form.Group>
                        </div>
                        <div>
                            <Form.Group>
                                <Form.Label>Full Name</Form.Label>
                                <Form.Control placeholder="Full Name" value={this.state.fullName}
                                              onChange={this.handleFullname}/>
                            </Form.Group>
                        </div>
                        <div className={styles.card}>
                            <Form.Group>
                                <Form.Label>Security Code</Form.Label>
                                <Form.Control placeholder="Security Code" value={this.state.secCode}
                                              onChange={this.handlesecCode}/>
                            </Form.Group>
                            <Form.Group>
                                <Form.Label>End Date</Form.Label>
                                <Form.Control placeholder="End Date" value={this.state.endD} onChange={this.handlendD}/>
                            </Form.Group>
                        </div>
                        <div className={styles.card}>
                            <Button className={styles.button} variant="secondary"
                                    onClick={this.handleBack}> Cancel </Button>
                            <Button className={styles.button} variant="primary"
                                    onClick={this.handleCashIn}> Confirm </Button>
                        </div>
                    </Form>
                </div>
            </div>
        )
    }


}

export default withRouter(CashIn)