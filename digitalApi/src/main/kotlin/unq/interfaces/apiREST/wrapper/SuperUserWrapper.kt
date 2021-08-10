package unq.interfaces.apiREST.wrapper

import wallet.User

class SuperUserWrapper(var email :String,var firstName : String,var idCard :String,var lastName : String,var isAdmin : Boolean){
    constructor(user : User):
                this(user.email,user.firstName,user.idCard,user.lastName,user.isAdmin)

}