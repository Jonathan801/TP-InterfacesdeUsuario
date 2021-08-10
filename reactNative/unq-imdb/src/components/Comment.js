import React, { Component } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { colors } from './styles/utils';

const styles = StyleSheet.create({
  author:{
      color: colors.green
  },
  comment:{
      color: colors.white,
      fontSize: 11
  },
  spaceComment:{
      paddingTop: 10,
      paddingBottom: 10,
      paddingLeft: 10,
      paddingRight: 10
  }
});

class Comment extends Component{
    constructor(props){
        super(props)
        this.state ={
          comment: props.data.content   
        }
    }

    renderComment = () =>{
    return(
        <React.Fragment>
           <View style={styles.spaceComment}>  
            <Text style={styles.author}>{this.props.data.author}</Text>
            <Text style={styles.comment}>{this.state.comment}</Text>
           </View> 
        </React.Fragment>)        
    }

    render(){
       return( 
        <View>
            {this.renderComment()}
        </View>
       )
    }
}

export default Comment;