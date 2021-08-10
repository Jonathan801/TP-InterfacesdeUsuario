package view

import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.WindowOwner
import viewModel.UserViewModel
import java.awt.Color

class DataUserWindow : SuperUserWindow{
    constructor(owner:WindowOwner,model:UserViewModel):
            super(owner,model)

    override fun createFormPanel(mainPanel: Panel?) {
        super.createFormPanel(mainPanel)
        Panel(mainPanel) with{
            asHorizontal()
            Label(it) with{
                text = "Correo electronico" + ":"
                color = Color.BLACK
                setWidth(160)
                alignLeft()
            }
            Label(it) with{
                text = this@DataUserWindow.modelObject?.getEmail().toString()
                alignRight()
            }
        }
        Panel(mainPanel) with{
            asHorizontal()
            Label(it) with{
                text = "Estado" + ":"
                color = Color.BLACK
                setWidth(160)
                alignLeft()
            }
            Label(it) with{
                text = this@DataUserWindow.modelObject?.getIsBlocked()!!
                alignRight()
            }
        }

    }
}