import React from 'react';
import { View, StyleSheet } from 'react-native';
import Title from './title';
import { sliderWidth, itemWidth, colors } from './styles/utils';
import { FlatList } from 'react-native-gesture-handler';
import Comment from './Comment'

const styles = StyleSheet.create({
    content:{
        display: "flex",
        justifyContent: "space-around",
        flexDirection: "column",
        alignItems: "center"
    },
    border:{
        borderStyle: "dashed",
        borderColor: colors.white,
        borderWidth: 1,
        
    },
    commentContainer:{
      borderStyle: "solid",
      borderColor: colors.yellow,
      borderWidth: 1
    }
})

renderComment = title => ({ item, index }) => {
    return (
      <React.Fragment>
        <View style={styles.commentContainer} >  
          <Comment id={`${title}_${index}`} data={item} />
        </View>
      </React.Fragment>  
    );
};
 
const CommentsMovie = ({ item: {title,comments} }) => {
  if(comments.length == 0){
    return (
     <View style={styles.content}>
        <Title text={"No comments"} ></Title>
     </View> 
    )
  }

  return (
    <View style={styles.content}>
      <FlatList
        data={comments}
        renderItem={this.renderComment(title)}
        keyExtractor={item => item.author + item.content.length + comments.indexOf(item)}
        sliderWidth={sliderWidth}
        itemWidth={itemWidth}
        useScrollView={true}
      />
    </View>
  )
}

export default CommentsMovie;