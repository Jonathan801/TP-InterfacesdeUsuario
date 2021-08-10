import axios from 'axios';

const API = 'https://us-central1-unq-imdb.cloudfunctions.net/api/v1';
const GROUP_ID = '10';

const headers = { 'content-type': 'application/json' };

const execute = (method, url, data) => axios({ method, url: `${API}/${GROUP_ID}/${url}`, headers, data, })
  .then(response => response.data)
  .catch(response => Promise.reject(response.response.data));

export default {
  login: (user) => execute('POST', 'login', user),
  register: (user) => execute('POST', 'register', user),
  getTop: () => execute('GET', 'top'),
  search: (text) => execute('GET', `search?q=${text}`),
  getMovies: (genre) => execute('GET',`/genre/${genre}`),
  getMovie: (id) => execute('GET',`${id}`),
  putComment: (id,author,content) => execute('PUT',`${id}/comment`,{author:author,content:content})
};
