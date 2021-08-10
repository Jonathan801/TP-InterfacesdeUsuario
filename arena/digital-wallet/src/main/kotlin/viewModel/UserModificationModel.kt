package viewModel

import org.uqbar.commons.model.annotations.Dependencies
import org.uqbar.commons.model.annotations.Observable
import org.uqbar.commons.model.exceptions.UserException
import wallet.DigitalWallet
import wallet.User

@Observable
class UserModificationModel(var user: User, var digitalWallet : DigitalWallet) {

    var email : String = user.email
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
        if(!(digitalWallet.users.filter { it.email != user.email }.all{it.email!=email})){
            throw EmailOcupado("El email ya esta siendo usado")
        } else{
            if(this.email == ""){
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


    @Dependencies("isBlock")
    fun getIsBlocked() : Boolean?{
        return isBlock
    }
}