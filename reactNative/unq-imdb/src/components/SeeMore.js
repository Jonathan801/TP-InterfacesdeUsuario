import React from 'react';
import { View, Text, Image, TouchableOpacity, StyleSheet } from 'react-native';
import { IS_IOS, itemWidth, slideHeight } from './styles/utils';
import Logo from './styles/seeMoreImage';
import { withNavigation } from 'react-navigation'

const styles = StyleSheet.create({
  slideInnerContainer: {
    width: itemWidth,
    height: slideHeight,
  },
  imageContainer: {
    flex: 1,
    marginBottom: IS_IOS ? 0 : -1, // Prevent a random Android rendering issue
    backgroundColor: 'black',
    justifyContent: 'center',
    alignItems: 'center',
  },
  image: {
    resizeMode: 'center',
    width: 70,
    height: 70,
    tintColor: 'white'
  },
  textContainer: {
    position: 'absolute',
    bottom: IS_IOS ? 20 : 12,
    width: itemWidth,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 10,
    backgroundColor: 'black',
  },
  title: {
    color: 'white',
    fontSize: 13,
    fontWeight: 'bold',
    letterSpacing: 0.5
  },
});

// const SeeMore = ({ genre }) => {
  // const uppercaseTitle = <Text style={styles.title}>{`See more`}</Text>;
  // return (
  //   <TouchableOpacity activeOpacity={1} style={styles.slideInnerContainer} onPress={} >
  //     <View style={styles.imageContainer}>
  //       <Image source={{ uri: Logo }} style={styles.image} />
  //     </View>
  //     <View style={styles.textContainer}>{uppercaseTitle}</View>
  //   </TouchableOpacity>
  // );
// }

class SeeMore extends React.Component{
  constructor(props){
    super(props)
    this.state={
      genre: props.genre
    }
  }

  render(){
    const {genre} = this.state
    const uppercaseTitle = <Text style={styles.title}>{`See more`}</Text>;
    return(      
        <TouchableOpacity activeOpacity={1} style={styles.slideInnerContainer} onPress={() => this.props.navigation.push('Movies',{genre})} >
          <View style={styles.imageContainer}>
            <Image source={{ uri: Logo }} style={styles.image} />
          </View>
          <View style={styles.textContainer}>{uppercaseTitle}</View>
        </TouchableOpacity>
      
    )
  }
}

export default withNavigation(SeeMore);
