package unq.interfaces.apiREST.wrapper

import wallet.User

class UserWrapper {

    var email = ""
    var password = ""
    constructor(user: User){
        this.email = user.email
        this.password = user.password
    }
}