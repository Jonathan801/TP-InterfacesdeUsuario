package viewModel

import org.uqbar.commons.model.annotations.Dependencies
import org.uqbar.commons.model.annotations.Observable
import org.uqbar.commons.model.exceptions.UserException
import wallet.DigitalWallet
import wallet.User

@Observable
class UserViewModel(var user: User, var digitalWallet:DigitalWallet) {

    var emailModification = user.email
    var items  = mutableListOf<String>()
    init {
        items.add("Bloqueado")
        items.add("Habilitado")
    }
    var isBlock : Boolean? = user.account?.isBlocked
    var itemSelected : String
    init{
        if(user.account?.isBlocked!!){
            itemSelected = "Bloqueado"
        } else{
            itemSelected = "Habilitado"
        }
    }
    fun validarCorreo() {
        try {
            verificarEmail()
        } catch (e:EmailOcupado){
            throw UserException(e.message)
        } catch (e:EmailIncompleto){
            throw UserException(e.message)
        }
    }

    fun verificarEmail(){
        if(!(digitalWallet.users.filter { it.email != user.email }.all{it.email!=emailModification})){
            throw EmailOcupado("El email ya esta siendo usado")
        } else{
            if(this.getEmail() == ""){
                throw EmailIncompleto("Se debe completar el campo de Correo Electrónico")
            }
        }
    }

    fun verificarEstado(){
        if(itemSelected!! == "Bloqueado"){
            digitalWallet.blockAccount(user.account!!)
        }else{
            digitalWallet.unblockAccount(user.account!!)
        }
    }


    /*@Dependencies("isBlock")
    fun getIsBlocked() : Boolean?{
        return isBlock
    }*/

    fun nombre() : String? = user.firstName
    fun apellido() = user.lastName
    fun dni() = user.idCard
    fun getEmail() = user.email
    fun password() = user.password
    fun isAdmin() = user.isAdmin

    fun getEsAdmin() : String{
        if(this.isAdmin()){
            return "Si"
        }
        else{
            return "No"
        }
    }

    @Dependencies("user")
    fun getIsBlocked() : String {
        if(user.account?.isBlocked!!){
            return "Bloqueado"
        }else{
            return "Habilitado"
        }
    }


}