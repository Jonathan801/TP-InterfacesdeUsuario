import React from 'react';
import { Link } from 'react-router-dom';
// import PageNotFound from '../view/404.png';
import travolta from '../view/tenor.gif';
import styles from '../App.module.css'


class NotFoundPage extends React.Component{
    render(){
        return (
          <div className={styles.notFound}>
            <div>
                <img src={travolta} alt="travolta gif" />
            </div>
            <div id={styles.error}>
                <p>404 Page Not Found</p>
                <Link id={styles.irInicio} to="/"> Ir al inicio </Link>
            </div>
          </div>
        );
    }
}
export default NotFoundPage;