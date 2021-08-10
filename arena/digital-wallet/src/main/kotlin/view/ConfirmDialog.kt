package view

import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.ErrorsPanel
import org.uqbar.arena.windows.WindowOwner
import viewModel.ManagementViewModel

class ConfirmDialog(owner:WindowOwner,model:ManagementViewModel) : Dialog<ManagementViewModel>(owner,model) {
    override fun createFormPanel(mainPanel: Panel) {
        ErrorsPanel(mainPanel,"El usuario ${modelObject.selectUser!!.user.email} sera eliminado")
        Label(mainPanel) with{
            text = "Esta seguro que desea eliminar al usuario ${modelObject.selectUser!!.user.email} ?"
        }
        Button(mainPanel) with {
            caption = "Aceptar"
            onClick{
                accept()
            }
        }
        Button(mainPanel) with{
            caption = "Cancelar"
            onClick{
                cancel()
            }
        }
    }

}