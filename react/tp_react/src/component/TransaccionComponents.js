import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {faDollarSign} from '@fortawesome/free-solid-svg-icons'
import styles from '../App.module.css'

export const Transaccion = ({dateTime,amount,description}) =>{
    return (
    	<div className={styles.homeTrans}>
    	  <span > {dateTime.year}/{dateTime.month}/{dateTime.day} </span> 
          <span className={styles.row2} > {description} </span> 
          {/* <span className={styles.mount} > <FontAwesomeIcon icon={faDollarSign}/> {amount} </span> */}
          <Transacc amount={amount}></Transacc>
      </div>)
}

const Transacc = (props) =>{
 return props.amount > 0 ? <span className={styles.mount} > <FontAwesomeIcon icon={faDollarSign}/> {props.amount} </span> 
                  : <span className={styles.mountDisc} > <FontAwesomeIcon icon={faDollarSign}/> {props.amount} </span>
}
