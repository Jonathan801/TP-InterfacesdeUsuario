import React,{Component} from 'react'
import { Button,Form,FormGroup,FormLabel,FormControl } from 'react-bootstrap';
import {withRouter} from 'react-router'
import styles from '../App.module.css'
import api from '../api/api';
import {TransferError, updateTransferError} from './TransferComponents'


class TransferPage extends Component{
    constructor(props){
        super(props)
        this.state = {
            cvu: '',
            amount : '',
            userActual: props.getUserFromParent(),
            transferError: null
        }
        this.handleAmount = this.handleAmount.bind(this)
        this.handleCvu = this.handleCvu.bind(this)
    }

    transferError = (errorMessage) => <TransferError error={errorMessage}/>

    actualizar = () =>{
        // axios.post(`http://localhost:7000/transfer`,{
        //     fromCVU:this.state.userActual.account.cvu,
        //     toCVU:this.state.cvu,
        //     amount: this.state.amount
        // }).then(response=>{
        //         this.props.history.push(`/logIn/${this.state.userActual.firstName}`)
        //     })
        let body = { fromCVU:this.state.userActual.account.cvu, toCVU:this.state.cvu,
                     amount: this.state.amount }

        api.transfer(body)
            .then(data => {
                 console.log('transfer',data)
                 this.props.history.push(`/home/${this.state.userActual.account.cvu}`)
            })
            .catch(error => {
                this.setState({transferError:this.transferError(error)})
                updateTransferError(error)
            })
    }

    handleBack = () => {
        this.props.history.push(`/home/${this.state.userActual.account.cvu}`)
    }

    handleAmount = (event) =>{
        let number = Number(event.target.value.trim())
        if(!isNaN(number)){
         this.setState({amount:event.target.value.trim()})
        }
    }

    handleCvu = (event) =>{
        let number = Number(event.target.value.trim())
        if(!isNaN(number)){
         this.setState({cvu: event.target.value.trim()})
        }
    }

    render(){
        return (
           <div className={styles.framePages}> 
            <div className={styles.transferForm}>
                <h1 align="center"> Transfer </h1>
                {this.state.transferError}
                <Form>
                    <Form.Group>
                        <Form.Label >CVU</Form.Label>
                        <Form.Control type="text" placeholder="CVU" value={this.state.cvu} onChange={this.handleCvu} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label >Amount</Form.Label>
                        <Form.Control type="number" placeholder="Amount" value={this.state.number} onChange={this.handleAmount} />
                    </Form.Group>
                </Form>
                <div className={styles.burr}>
                    <Button variant="primary"  onClick={this.actualizar}>
                        Confirm
                    </Button>
                    <Button variant="secondary" onClick={this.handleBack}>
                        Cancel
                    </Button>
                </div>
            </div>
           </div> 
        )
    }
}

export default withRouter(TransferPage)