package unq.interfaces.apiREST.wrapper

import wallet.User

class UserWithAccountWrapper (var email :String,var firstName : String,var idCard :String,var lastName : String,var isAdmin : Boolean,var account: AccountCVUWrapper){
    constructor(user : User):
            this(user.email,user.firstName,user.idCard,user.lastName,user.isAdmin,AccountCVUWrapper(user.account!!))

}