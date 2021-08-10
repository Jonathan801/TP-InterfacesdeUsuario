import React,{Component} from 'react'
import styles from '../App.module.css'
import ListGroup from 'react-bootstrap/ListGroup'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {faDollarSign} from '@fortawesome/free-solid-svg-icons'
import api from '../api/api'
// import axios from 'axios'
import {Transaccion} from './TransaccionComponents'
import {withRouter} from 'react-router'
import icon from '../view/cashInIcon.png'
import transferIcon from '../view/transferIcon.jpg'
import userIcon from '../view/userProfile.png'
import {Link} from 'react-router-dom'

class Home extends Component{

    constructor(props){
        super(props)
        this.state = { 
            transaccions: [],
            userActual: props.getUserFromParent()
        }
    }


    componentDidMount(){
        // axios.get(`http://localhost:7000/transaccions/${this.state.userActual.account.cvu}`)
        //     .then(response => this.setState({transaccions:response.data}))
        //     .catch(error => console.log(error))
        api.getUserTransaccions(this.state.userActual.account.cvu)
             .then(data => this.setState({transaccions:data}))
             .catch(error =>console.log(error))
        // axios.get(`http://localhost:7000/users/${this.state.userActual.account.cvu}`)
        //     .then(response=>this.setState({userActual: response.data}))  
        //     .catch(error => console.log(error)) 
        api.getUser(this.state.userActual.account.cvu)
             .then(data => this.setState({userActual:data}))
             .catch(error => console.log(error))
         
    }

    render(){
        let transaccions = this.state.transaccions.map((transacc,key) => <Transaccion key={key} {...transacc} /> );
        let pathHome = `/home/${this.state.userActual.account.cvu}`
        
        return(
            <div className={styles.containerHome}>
                {/*<MyC {...this.props} user={this.state.user} />*/}
                <div className={styles.homeGridFirst}>
                    <div className={styles.homeAmount}> 
                        Saldo de: <FontAwesomeIcon className={styles.mount} icon={faDollarSign}/> 
                        <span className={styles.mount} > {this.state.userActual.account.amount} </span>
                    </div>
                    <div className={styles.homeIcons}> 
                      <Link to={pathHome + "/cashIn"}><img className={styles.cashinIcon} src={icon} alt='CashIn' /></Link>
                      <Link to={pathHome + "/profile"}><img className={styles.userIcon} src={userIcon} alt='User Profile'/></Link>
                      <Link to={pathHome + "/transfer"}><img className={styles.transferIcon} src={transferIcon} alt='Transfer'/></Link>
                    </div>
                </div>
                <div className={styles.homeListGrid}>
                    <ListGroup className={styles.homeListGrid}>
                        Historial 
                        {transaccions.map(trans => <ListGroup.Item className={styles.homeTrans}>
                                                    {trans}
                                                </ListGroup.Item>)} 
                                                {/* {transaccions} */}
                    </ListGroup>
                </div>   
            </div>
        )
    }
}

export default withRouter(Home)