package viewModel

import org.uqbar.commons.model.annotations.Observable
import org.uqbar.commons.model.exceptions.UserException
import wallet.Account
import wallet.DigitalWallet
import wallet.User

@Observable
class AddUserViewModel(var digitalWallet : DigitalWallet) {
    var nombre : String = ""
    var apellido : String = ""
    var dni : String = ""
    var email : String = ""
    var password : String = ""
    var isAdmin : Boolean = false

    /*fun validarRegistro(){
        try {
            verificarCampos()
            verificarEmail()
        }catch (e: DatosIncompletos){
            throw  UserException("Los datos estan incompletos")
        }catch (e: EmailOcupado){
            throw UserException("El email ya esta ocupado")
        }
    }*/
    fun validarRegistro() {
        try{
            verificarEmail()
            //verificarDni()
        }catch (e:EmailOcupado){
            throw UserException(e.message)
        }
        val error =
            when {
                nombre == "" -> "El campo nombre esta vacio"
                apellido == "" -> "El campo apellido esta vacio"
                dni == "" -> "El campo dni esta vacio"
                email == "" -> "El campo mail esta vacio"
                password == "" -> "El campo password esta vacio"
                else -> ""
            }
        return validarRegistroAux(error)
    }

    fun validarRegistroAux(error : String){
        if(error!=""){
            throw UserException(error)
        }
    }

     fun verificarEmail() {
        if(!(digitalWallet.users.all{it.email!=email})){
                throw EmailOcupado("El email <$email> ya esta ocupado")
            }
    }

    fun verificarDni(){
        if(!(digitalWallet.users.all{it.idCard!=dni})){
            throw UserException("No puede haber mas de un usuario con el mismo dni")
        }
    }

    fun crearUsuario() : User {
        return User(this.dni, this.nombre, this.apellido, this.email, this.password, this.isAdmin)
    }

    fun registrarUsuario() {
        var userARegistrar = crearUsuario()
        var account = Account(userARegistrar,DigitalWallet.generateNewCVU())
        digitalWallet.register(userARegistrar)
        digitalWallet.assignAccount(userARegistrar,account)
    }


}

class DatosIncompletos(message: String?) : Throwable(message)
class EmailOcupado(message: String?) : Throwable(message)
class EmailIncompleto(message: String?) : Throwable(message)