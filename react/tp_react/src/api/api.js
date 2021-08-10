import axios from 'axios'

axios.defaults.baseURL = "http://localhost:7000";
axios.defaults.timeout = 10000;

const request = (type, path, body) =>
  axios
    .request({ url: path, method: type, data: body })
    .then(response => response.data)
    .catch(error => Promise.reject(error.response.data)); // equivalente: try {...} catch (e: Exception) { throw Exception(error) }

const api ={
    getUserTransaccions: (cvu) => request('get',`/transaccions/${cvu}`),
    getUser: (cvu) => request('get',`/users/${cvu}`),
    registerUser:(body) => request('post',`/users/register`,body),
    changeUser:(cvu,body) => request('put',`/users/change/${cvu}`,body),
    login:(body) => request('post',`/users/login`,body),
    cashIn:(body) => request('post',`/cashIn`,body),
    transfer:(body) => request('post',`/transfer`,body)

}

export default api