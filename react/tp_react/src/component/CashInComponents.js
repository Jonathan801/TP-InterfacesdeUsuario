import React from 'react'
import 'bootstrap/dist/css/bootstrap.min.css';

export class CashInError extends React.Component{
    constructor(props){
        super(props)
        this.state={
            error: props.error
        }
        updateException = updateException.bind(this)
    }

    render(){
        return(
            <div>
                <ErrorMessage error={this.state.error} />
            </div>
        )
    }
}

const ErrorMessage = props =>{
    return (
        props.error
           ? <div className="alert alert-danger" role="alert">{props.error}</div>
           : null
    )
}

export function updateException(error){
    this.setState({error:error})
}

