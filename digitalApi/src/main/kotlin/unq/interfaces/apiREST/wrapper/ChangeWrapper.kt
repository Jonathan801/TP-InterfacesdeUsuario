package unq.interfaces.apiREST.wrapper

import wallet.User


data class ChangeWrapper(var email : String ="",var firstName : String = "") {

    var pattern = "^[A-Za-z](.*)([@]{1})(.{1,})([gmail]|[hotmail]|[facebook]|[yahoo])(\\.)(.{1,})".toRegex()
    var emailPattern = "xxx@example.com"

    fun validarCampos(users:MutableList<User>){
        require(email!="" ){throw IllegalArgumentException("El email no puede estar vacío")}
        require(firstName != ""){ throw IllegalArgumentException("El nombre no puede estar vacio")}
        require(pattern.matches(email)){throw IllegalArgumentException("Invalid email, use ${emailPattern}")}
        require(users.all { it.email != email }){throw IllegalArgumentException("El email ya esta en uso")}
    }
}