package viewModel

import org.uqbar.commons.model.annotations.Dependencies
import org.uqbar.commons.model.annotations.Observable
import org.uqbar.commons.model.exceptions.UserException
import wallet.DigitalWallet

@Observable
class ManagementViewModel(var wallet : DigitalWallet) {
    var digitalWallet = wallet
    var filters = digitalWallet.users.map{it -> UserViewModel(it,wallet) }.toMutableList()
    var user = ""
    var selectUser : UserViewModel? = null

    fun filtrarPor(name : String){
        var userSearch= mutableListOf<UserViewModel>()
        for (user in digitalWallet.users){
              if (user.firstName.toLowerCase().contains(name.toLowerCase())){
                  userSearch.add(UserViewModel(user,wallet))
              }
        }
        if(name.length==0){
            filters=digitalWallet.users.map{it -> UserViewModel(it,wallet) }.toMutableList()
        }
        else{
            filters=userSearch
        }

    }
    @Dependencies("selectUser")
    fun getIsSelected() :Boolean{
        return selectUser!=null
    }

    fun esEliminable(){
       /** try{
            digitalWallet.deleteUser(selectUser?.user!!)
        }catch (e : AssertionError) {
            throw UserException("No se puede eliminar un usuario con saldo")
        }***/
        if(selectUser?.user?.account?.balance != 0.0){
            throw UserException("No se puede eliminar un usuario con saldo disponible")
        }else{
            digitalWallet.deleteUser(selectUser?.user!!)
        }
    }
}