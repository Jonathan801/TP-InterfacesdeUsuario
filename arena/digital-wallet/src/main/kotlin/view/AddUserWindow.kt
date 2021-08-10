package view

import data.DigitalWalletData
import org.uqbar.arena.Application
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.Window
import org.uqbar.arena.windows.WindowOwner
import viewModel.AddUserViewModel
import viewModel.ManagementViewModel
import wallet.DigitalWallet
import wallet.User
import java.awt.Color

class AddUserWindow : SimpleWindow<AddUserViewModel> {
    override fun addActions(actionsPanel: Panel?) {
    }

    constructor(owner: WindowOwner, model: AddUserViewModel):
            super(owner,model)


    override fun createFormPanel(mainPanel: Panel?) {
        title = "DigitalWallet - Agregar Usuario"
        Panel(mainPanel) with{
            asHorizontal()
            Label(it) with{
                text = "Nombre"
                width = 170
                color = Color.BLACK
            }
            TextBox(it) with{
                bindTo("nombre")
                width = 200
            }
        }
        Panel(mainPanel) with{
            asHorizontal()
            Label(it) with{
                text = "Apellido"
                width = 170
                color = Color.BLACK
            }
            TextBox(it) with {
                bindTo("apellido")
                width = 200
            }
        }
        Panel(mainPanel) with{
            asHorizontal()
            Label(it) with{
                text = "Numero de documento"
                width = 170
                color = Color.BLACK
            }
            TextBox(it) with{
                bindTo("dni")
                width = 200
            }
        }
        Panel(mainPanel) with{
            asHorizontal()
            Label(it) with{
                text = "Correo Electrónico"
                width = 170
                color = Color.BLACK
            }
            TextBox(it) with {
                bindTo("email")
                width = 200
            }
        }
        Panel(mainPanel) with{
            asHorizontal()
            Label(it) with{
                text = "Contraseña"
                width = 170
                color = Color.BLACK
            }
            PasswordField(it) with{
                bindTo("password")
                width = 200
            }
        }
        Panel(mainPanel) with {
            Label(it) with {
                text = "Es administrador?"
                color = Color.BLACK
            }
        }
        Panel(mainPanel) with{
            asHorizontal()
            Button(it) with {
                caption="Si"
                width = 200
                //setWidth(200)
                onClick {
                    this@AddUserWindow.modelObject.isAdmin = true
                }
            }
            Button(it) with {
                caption="No"
                width = 200
                //setWidth(200)
                onClick {
                    this@AddUserWindow.modelObject.isAdmin = false
                }
            }

        }
        Button(mainPanel) with {
            caption="Agregar"
            onClick {
              modelObject.validarRegistro()
              modelObject.registrarUsuario()
              close()
              ManagementWindow(owner, ManagementViewModel(modelObject.digitalWallet)).open()
            }
        }
        Button(mainPanel) with {
            caption="Cancelar"
            onClick {
                close()
                ManagementWindow(owner, ManagementViewModel(modelObject.digitalWallet)).open()
            }
        }
    }
}
