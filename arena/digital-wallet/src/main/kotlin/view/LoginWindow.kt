package view

import data.DigitalWalletData
import org.uqbar.arena.Application
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.Window
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException
import viewModel.LoginViewModel
import viewModel.ManagementViewModel
import wallet.LoginException
import wallet.User
import java.awt.Color


class LoginWindow : SimpleWindow<LoginViewModel>{
    constructor(owner:WindowOwner,model: LoginViewModel):
            super(owner,model)

    override fun addActions(p0: Panel?) {
    }

    override fun createFormPanel(mainPanel: Panel?) {
        title = "Digital Wallet - Admin Login"
        Label(mainPanel) with{
            text = "Bienvenido a la ventana de acceso a la administración de Digital Wallet"
            color = Color.BLUE
        }
        Label(mainPanel) with{}
        Label(mainPanel) with{
            text = "Correo electrónico"
            setWidth(500)
        }
        TextBox(mainPanel) with{
            bindTo("mail")
        }
        Label(mainPanel) with{
            text = "Contraseña"
            setWidth(500)
        }
        PasswordField(mainPanel) with{
            bindTo("password")
        }
        Button(mainPanel) with{
            caption = "Ingresar"
            onClick{
                /*if(!modelObject.digital.login(modelObject.mail,modelObject.password).isAdmin){
                    throw  UserException("El usuario no es admin")
                }else{
                    LoginWindow2(owner,modelObject).open()
                }*/
                var user : User? = null
                try{
                    user = modelObject.digital.login(modelObject.mail,modelObject.password)
                } catch (error : LoginException){
                    throw  UserException("Usuario y/o password Incorrectos")
                }
                if(!user.isAdmin){
                    throw  UserException("El usuario ${user.email} no es admin")
                }else{
                    close()
                    ManagementWindow(owner, ManagementViewModel(modelObject.digital)).open()
                }
            }
        }
    }

}

class MyApp : Application(){
    override fun createMainWindow(): Window<*> {
        var wallet = DigitalWalletData.build()
        var view = LoginViewModel(wallet)
        return LoginWindow(this,view)
    }

}

fun main() = MyApp().start()