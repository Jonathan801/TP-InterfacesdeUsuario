package unq.interfaces.apiREST.wrapper

import unq.interfaces.apiREST.api.RegisterError
import wallet.User

data class RegisterWrapper(var email: String = "", var firstName: String = "", var idCard: String = "", var lastName: String = "", var password: String = "") {

    var pattern = "^[A-Za-z](.*)([@]{1})(.{1,})([gmail]|[hotmail]|[facebook]|[yahoo])(\\.)(.{1,})".toRegex()
    var emailPattern = "xxx@example.com"

    fun validarCampos(){
        require(email!=""){throw RegisterError("El usuario debe tener un email")}
        require(lastName!=""){throw RegisterError("El usuario debe tener un apellido")}
        require(password!=""){throw RegisterError("El usuario debe tener un password")}
        require(idCard!=""){throw RegisterError("El usuario debe tener un idCard")}
        require(pattern.matches(email)){throw RegisterError("Invalid email, use ${emailPattern}")}
    }
}
