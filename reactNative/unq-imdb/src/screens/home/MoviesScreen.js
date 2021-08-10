import React from 'react';
import { View, StyleSheet,Text } from 'react-native';
import { FlatList } from 'react-native-gesture-handler';
import Loading from '../../components/loading'
import Movie from '../../components/movie'
import { SafeAreaView } from 'react-navigation';
import Api from '../../resources/Api'
import { colors } from '../../components/styles/utils'
import Loguot from '../../components/logout'
import ErrorText from '../../components/errorText';

class MoviesScreen extends React.Component{
    constructor(props){
        super(props)
        this.state ={
            data: [],
            loading: true,
            genre: props.navigation.getParam('genre'),
            error: ''
        }
    }

    static navigationOptions = {
        title: "Unq-Imdb",
        headerRight: () => <Loguot />,
        headerStyle: {
          backgroundColor: colors.pink2,
        },
        headerTintColor: colors.white,
        headerTitleStyle: {
          color: colors.white,
        },
      };

    componentDidMount(){
        Api.getMovies(this.state.genre)
            .then(data => this.setState({data,loading:false,error:''}))
            .catch(error => {
                console.log('error:',error)
                this.setState({error: 'No se pudieron cargar las peliculas.'})
            })
    }

    componentWillUnmount(){
        this.setState({data:[]})
    }

    renderMovie = id => ({ item, index }) =>{
        return (
            <React.Fragment>
                <View>
                    <Movie id={id} data={item}/>
                </View>
            </React.Fragment>
        )
    }

    renderMovies(){
        const {loading} = this.state
        if(loading){
            return <Loading />
        }
        return( 
            <React.Fragment>
               <Text style={styles.title}>{this.state.genre} list:</Text>
               <View style={styles.container2}> 
                <FlatList
                    data={this.state.data}
                    renderItem={this.renderMovie()}
                    keyExtractor={item => item.title + item.id}
                    showsVerticalScrollIndicator={false}
                />
               </View> 
            </React.Fragment>
        )
    }

    render(){
        return (
            <SafeAreaView style={styles.safeArea}>
              <View style={styles.container}>
                {this.renderMovies()}
              </View>
            </SafeAreaView>
        );
    }
}

const styles = StyleSheet.create({
    safeArea: {
      backgroundColor: colors.grey3,
    },
    container: {
      width: '100%',
      height: '100%',
      backgroundColor: colors.grey3,
    },
    container2: {
      display: "flex",
      flexDirection:"column",
      justifyContent: "center",
      alignItems: "center"
    },
    title:{
        color: colors.white
    }
  });

export default MoviesScreen