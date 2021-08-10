package view

import org.uqbar.arena.Application
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.Window
import org.uqbar.arena.windows.WindowOwner
import viewModel.ManagementViewModel
import viewModel.UserModificationModel
import viewModel.UserViewModel
import wallet.Account
import wallet.DigitalWallet
import wallet.User
import java.awt.Color


/***class UserModificationWindow : SimpleWindow<UserModificationModel> {
    constructor(owner: WindowOwner, model:UserModificationModel):
            super(owner,model)
    override fun addActions(p0: Panel?) {
    }

    override fun createFormPanel(mainPanel: Panel?) {
        title = "Digital Wallet - Modificar Usuario"
        Panel(mainPanel) with{
            asHorizontal()
            Label(it) with{
                text = "Nombre" + ":"
                color = Color.BLACK
                setWidth(160)
                alignLeft()
            }
            Label(it) with{
                text = this@UserModificationWindow.modelObject.user.firstName
            }
        }
        Panel(mainPanel) with{
            asHorizontal()
            Label(it) with{
                text = "Apellido" + ":"
                color = Color.BLACK
                setWidth(160)
                alignLeft()
            }
            Label(it) with{
                text = this@UserModificationWindow.modelObject.user.lastName
            }
        }
        Panel(mainPanel) with{
            asHorizontal()
            Label(it) with{
                text = "Numero de Documento" + ":"
                color = Color.BLACK
                setWidth(160)
                alignLeft()
            }
            Label(it) with{
                text = this@UserModificationWindow.modelObject.user.idCard
            }
        }
        Panel(mainPanel) with{
            asHorizontal()
            Label(it) with{
                text = "Correo Electronico" + ":"
                color = Color.GREEN
                setWidth(160)
                alignLeft()
            }
            TextBox(it) with{
                bindTo("email")
            }
        }
        Panel(mainPanel) with{
            asHorizontal()
            Label(it) with{
                text = "Estado"
                color = Color.GREEN
                setWidth(160)
                alignLeft()
            }
            Selector<String>(it) with{
                bindItemsTo("items")
                bindSelectedTo("itemSelected")
            }
        }
        Panel(mainPanel) with{
            asHorizontal()
            Label(it) with{
                text = "Cuenta CVU" + ":"
                color = Color.BLACK
                setWidth(160)
                alignLeft()
            }
            Label(it) with{
                text = this@UserModificationWindow.modelObject.user.account?.cvu.toString()
            }
        }
        Panel(mainPanel) with{
            asHorizontal()
            Label(it) with{
                text = "Saldo"
                color = Color.BLACK
                setWidth(160)
                alignLeft()
            }
            Label(it) with{
                text = "$ " + this@UserModificationWindow.modelObject.user.account?.balance.toString()
            }
        }
        Panel(mainPanel) with {
            asHorizontal()
            Button(it) with {
                caption = "Modificar"
                onClick {
                    this@UserModificationWindow.modelObject.validarCorreo()
                    this@UserModificationWindow.modelObject.verificarEstado()
                    this@UserModificationWindow.modelObject.user.email = this@UserModificationWindow.modelObject.email
                    var view = ManagementViewModel(this@UserModificationWindow.modelObject.digitalWallet)
                    close()
                    ManagementWindow(this@UserModificationWindow, view).open()
                }
            }
            Button(it) with{
                caption = "Cancelar"
                onClick {
                    var view = ManagementViewModel(this@UserModificationWindow.modelObject.digitalWallet)
                    close()
                    ManagementWindow(this@UserModificationWindow,view).open()
                }
            }
        }
    }
}

class MyAppMod : Application(){
    override fun createMainWindow(): Window<*> {
        var user1 = User("a","Pepe","Bala","cr@unq.ar","1234",false)
        var user2 = User("a","Juan","Lasd","ce@unq.ar","1234",false)
        var digital = DigitalWallet()
        var account1 = Account(user1, "asd")
        var account2 = Account(user2, "asd2")

        digital.register(user1)
        digital.register(user2)
        digital.assignAccount(user1,account1)
        digital.assignAccount(user2,account2)
        account1.balance = 50.0
        var view = UserModificationModel(user1,digital)
        return UserModificationWindow(this,view)
    }

}

fun main() = MyAppMod().start()**/

class UserModificationWindow : SuperUserWindow{
    constructor(owner: WindowOwner,model:UserViewModel):
            super(owner,model)

    override fun createFormPanel(mainPanel: Panel?) {
        super.createFormPanel(mainPanel)
        Panel(mainPanel) with{
            asHorizontal()
            Label(it) with{
                text = "Correo Electronico" + ":"
                color = Color.GREEN
                setWidth(160)
                alignLeft()
            }
            TextBox(it) with{
                bindTo("emailModification")
            }
        }
        Panel(mainPanel) with{
            asHorizontal()
            Label(it) with{
                text = "Estado"
                color = Color.GREEN
                setWidth(160)
                alignLeft()
            }
            Selector<String>(it) with{
                bindItemsTo("items")
                bindSelectedTo("itemSelected")
            }
        }
        Panel(mainPanel) with{
            asHorizontal()
            Label(it) with{
                text = "Saldo"
                color = Color.BLACK
                setWidth(160)
                alignLeft()
            }
            Label(it) with{
                text = "$ " + this@UserModificationWindow.modelObject.user.account?.balance.toString()
            }
        }
        Panel(mainPanel) with {
            asHorizontal()
            Button(it) with {
                caption = "Modificar"
                onClick {
                    this@UserModificationWindow.modelObject.validarCorreo()
                    this@UserModificationWindow.modelObject.verificarEstado()
                    this@UserModificationWindow.modelObject.user.email = this@UserModificationWindow.modelObject.emailModification
                    var view = ManagementViewModel(this@UserModificationWindow.modelObject.digitalWallet)
                    close()
                    ManagementWindow(this@UserModificationWindow, view).open()
                }
            }
            Button(it) with{
                caption = "Cancelar"
                onClick {
                    var view = ManagementViewModel(this@UserModificationWindow.modelObject.digitalWallet)
                    close()
                    ManagementWindow(this@UserModificationWindow,view).open()
                }
            }
        }
    }
}