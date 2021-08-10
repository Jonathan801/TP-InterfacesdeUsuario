import React from 'react';
import { View, StyleSheet, Text } from 'react-native';
import { FlatList } from 'react-native-gesture-handler';
import { SafeAreaView } from 'react-navigation';
import Movie from '../../components/movie'
import Api from '../../resources/Api';
import Loading from '../../components/loading';
import Loguot from '../../components/logout';
import Storage from '../../resources/Storage'
import { colors } from '../../components/styles/utils';
import CommentsMovie from '../../components/CommentsMovie'
import Input from '../../components/input';
import Title from '../../components/title'
import ErrorText from '../../components/errorText'
import Button from '../../components/button'

class MovieDetails extends React.Component{
    constructor(props){
        super(props)
        this.state = {
            movie: [],
            loading: true,
            seeComments: false,
            comments: [],
            comment: '',
            author:'',
            error:'',
            id: props.navigation.getParam('id')
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
          Api.getMovie(this.state.id)
                .then(data => this.setState({movie:data,loading:false}))
                .catch(error => console.log('Ocurrio un error',error))
      }

      submit = () =>{
        const comment = this.state.comment
        if(comment == ''){
          this.setState({error:'El comentario no puede ser vacio.'})
          return null
        }
        const id = this.state.movie.id
        Storage.getUser()
              .then((user)=>this.setState({author:user}))
              .then(() => Api.putComment(id,this.state.author,comment))
              .then(()=>Api.getMovie(id))
              .then(data=>this.setState({movie:data,comment:'',error:''}))
              .catch(error => {
                this.setState({error:'No se pudo enviar el comentario. Por favor, vuelva a intentarlo'})
                console.log('error al enviar comentario',error)
              })
      }

      renderComments(){
        const moviesC = [this.state.movie]
        if(!this.state.seeComments){
          return(
            <View style={styles.button}>
              <Button text='Cargar comentarios' onPress={()=>{this.setState({seeComments:true})}} backgroundColor={colors.blue} style={styles.button}></Button>
            </View>  
          )
        }
        return(
         <React.Fragment> 
          <View style={styles.comments}>
            <Text style={{color:colors.white}}>Agrega tu comentario</Text>
            <Input placeholder="Add comment..." value={this.state.comment} 
                          onChange={comment => this.setState({comment})} 
                          onSubmit={this.submit} />
            </View>
              { !!this.state.error && <ErrorText text={this.state.error} />}
            <FlatList
              data={moviesC}
              renderItem={CommentsMovie}
              keyExtractor={item => item.title}
              showsVerticalScrollIndicator={false}
            />
          </React.Fragment>  
        )
      }

      renderContent(){
          const {loading, movie, seeMore} = this.state
          if(loading){
              return <Loading />
          }
          const moviesC = [movie]
          return(
              <React.Fragment>
                <View style={styles.container}>
                    <View style={styles.imageContainer}>  
                        <Movie data={movie} />
                        <Text></Text>
                        <Title style={{paddingTop:10}} text={movie.title}/>
                        <Text style={styles.overview} onPress={() => alert(movie.overview)}>See overview</Text>
                    </View>
                    {this.renderComments()}
                </View>    
              </React.Fragment>
          )
      }

      render(){
        return (
            <SafeAreaView style={styles.safeArea}>
              <View >
                {this.renderContent()}
              </View>
            </SafeAreaView>
          );
      }

}

const styles = StyleSheet.create({
    safeArea: {
      backgroundColor: colors.black,
      width: "100%",
      height: "100%"
    },
    container:{
      display:"flex",
      justifyContent: "space-evenly",
      flexDirection:"column"
    },
    imageContainer: {
        display: "flex",
        justifyContent: "space-around",
        alignItems: "center",
        flexDirection: "column"
    },
    image: {
        height: "30%",
        width: "30%"
    },
    noComment:{
      color: colors.white
    },
    comments:{
      paddingTop: 40,
      display: "flex",
      alignItems:"center"
    },
    button:{
      paddingTop: 20,
      paddingLeft: 25
    },
    overview:{
      color: colors.white,
      paddingTop: 30,
      paddingBottom: 10,
      fontSize: 10
    }
  });

export default MovieDetails;  