import { createAppContainer, createSwitchNavigator } from 'react-navigation';
import { createStackNavigator } from 'react-navigation-stack';

import AuthenticatorScreen from './src/screens/AuthenticatorScreen';
import LoginScreen from './src/screens/auth/LoginScreen';
import RegisterScreen from './src/screens/auth/RegisterScreen';

import HomeScreen from './src/screens/home/HomeScreen';
import SearchScreen from './src/screens/home/SearchScreen';
import MovieDetails from './src/screens/details/MovieDetails.js'
import MoviesScreen from './src/screens/home/MoviesScreen'


const AppStack = createStackNavigator({ Home: HomeScreen, Search: SearchScreen , Movie: MovieDetails,Movies:MoviesScreen});
const AuthStack = createStackNavigator({ Login: LoginScreen, Register: RegisterScreen });

export default createAppContainer(
  createSwitchNavigator(
    {
      AuthLoading: AuthenticatorScreen,
      App: AppStack,
      Auth: AuthStack,
    },
    {
      initialRouteName: 'AuthLoading',
    }
  )
);
