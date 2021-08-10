package view

import data.DigitalWalletData
import org.uqbar.arena.Application
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.widgets.tables.Column
import org.uqbar.arena.widgets.tables.Table
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.Window
import org.uqbar.arena.windows.WindowOwner
import viewModel.*
import wallet.Account
import wallet.User

class ManagementWindow : SimpleWindow<ManagementViewModel>{
    constructor(owner:WindowOwner,model: ManagementViewModel):
            super(owner,model)

    override fun addActions(p0: Panel?) {

    }

    override fun createFormPanel(mainPanel: Panel?) {
        //mainPanel.asHorizontal()
        title = "Digital Wallet - Administración"
        Label(mainPanel) with{
            text = "Listado de usuarios"
        }
        TextBox(mainPanel) with{
           bindTo("user")
        }
        Button(mainPanel) with{
            caption = "Buscar"
            onClick { modelObject.filtrarPor(modelObject.user) }
        }
        Table(mainPanel, UserViewModel::class.java) with {
            it bindItemsTo "filters"
            it bindSelectionTo "selectUser"

            setNumberVisibleRows(10)
            Column(it) with {
                title = "Nombre"
                width = 2000
                fixedSize = 250
                bindContentsToProperty("nombre")
            }
            Column(it) with {
                title = "Apellido"
                width = 2000
                fixedSize = 250
                bindContentsToProperty("apellido")
            }
            Column(it) with {
                title = "Usuario"
                width = 2000
                fixedSize = 250
                bindContentsToProperty("email")
            }
            Column(it) with {
                title = "Estado"
                width = 2000
                fixedSize = 250
                bindContentsToProperty("isBlocked")
            }
        }
        Panel(mainPanel) with{
            asHorizontal()
            Button(it) with{
                bindEnabledTo("isSelected")
                caption = "Ver"
                width = 140
                onClick {
                   // if (this@ManagementWindow.modelObject.selectUser != null) {
                        DataUserWindow(owner,this@ManagementWindow.modelObject.selectUser!!).open()
                   // }else{
                    //    throw UserException("Error nuestro jeje")
                }

            }
            Button(it) with{
                caption = "Agregar usuario"
                width = 155
                onClick {
                    close()
                    var userAdd = AddUserViewModel(this@ManagementWindow.modelObject.digitalWallet)
                    AddUserWindow(owner,userAdd).open()
                }
            }
            Button(it) with{
                bindEnabledTo("isSelected")
                caption = "Modificar"
                width = 155
                onClick {
                    var usuario = this@ManagementWindow.modelObject.selectUser?.user!!
                    var userMod = UserViewModel(usuario,this@ManagementWindow.modelObject.digitalWallet)
                    close()
                    UserModificationWindow(owner,userMod).open()
                }
            }
            Button(it) with{
                bindEnabledTo("isSelected")
                caption = "Eliminar"
                width = 155
                onClick {
                    ConfirmDialog(this@ManagementWindow, this@ManagementWindow.modelObject).with {
                        onAccept {
                            this@ManagementWindow.modelObject.esEliminable()
                            close()
                            var model = ManagementViewModel(this@ManagementWindow.modelObject.digitalWallet)
                            ManagementWindow(this@ManagementWindow, model).open()
                        }
                        open()
                    }

//                    this@ManagementWindow.modelObject.esEliminable()
//                    close()
//                    var model = ManagementViewModel(this@ManagementWindow.modelObject.digitalWallet)
//                    ManagementWindow(this@ManagementWindow, model).open()
                }
            }
            Button(it) with{
                caption = "Agregar beneficio"
                width = 155
                onClick {
                    close()
                    var giftModel = GiftViewModel(this@ManagementWindow.modelObject.digitalWallet)
                    ConfirmLoyaltyWindow(this@ManagementWindow,giftModel).open()
                }
            }
            Button(it) with{
                caption = "Log out"
                width = 155
                onClick {
                    var log = LoginViewModel(this@ManagementWindow.modelObject.digitalWallet)
                    close()
                    LoginWindow(this@ManagementWindow,log).open()
                }
            }
            Button(it) with{
                caption = "Beneficios Registrados"
                width = 160
                onClick {
                    var loy = GiftViewModel(this@ManagementWindow.modelObject.digitalWallet)
                    LoyaltyGiftWindow(this@ManagementWindow,loy).open()
                }
            }
        }
    }

}

class ConfirmLoyaltyWindow : SimpleWindow<GiftViewModel> {
    constructor(owner: WindowOwner,model: GiftViewModel):
            super(owner,model)

    override fun addActions(p0: Panel?) {

    }

    override fun createFormPanel(p0: Panel?) {
        Label(p0) with{
            text = "Que tipo de beneficio quiere agregar?"
        }
        Panel(p0) with{
            asHorizontal()
            Button(it) with{
                caption = "Descuento"
                width = 100
                onClick{
                    close()
                    DiscountWindow(this@ConfirmLoyaltyWindow,this@ConfirmLoyaltyWindow.modelObject).open()

                }
            }
            Button(it) with{
                caption = "Regalo"
                width = 100
                onClick{
                    close()
                    CashGiftWindow(this@ConfirmLoyaltyWindow,this@ConfirmLoyaltyWindow.modelObject).open()

                }
            }
        }
    }
}

class MyApp2 : Application(){
    override fun createMainWindow(): Window<*> {
        var wallet = DigitalWalletData.build()
        var view = ManagementViewModel(wallet)
        var user1 = User("a","Pepe","Bala","cr@unq.ar","1234",false)
        var user2 = User("a","Juan","Lasd","ce@unq.ar","1234",false)
        var account1 = Account(user1, "asd")
        var account2 = Account(user2, "asd2")

        wallet.register(user1)
        wallet.register(user2)
        wallet.assignAccount(user1,account1)
        wallet.assignAccount(user2,account2)
        account1.balance = 50.0
        return ManagementWindow(this,view)
    }

}
fun main() = MyApp2().start()