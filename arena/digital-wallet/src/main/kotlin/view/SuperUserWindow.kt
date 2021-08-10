package view

import org.uqbar.arena.kotlin.extensions.asHorizontal
import org.uqbar.arena.kotlin.extensions.color
import org.uqbar.arena.kotlin.extensions.text
import org.uqbar.arena.kotlin.extensions.with
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import viewModel.UserViewModel
import java.awt.Color

open class SuperUserWindow : SimpleWindow<UserViewModel>{
    constructor(owner: WindowOwner, model: UserViewModel?) :
            super(owner,model)

    override fun addActions(p0: Panel?) {
    }

    override fun createFormPanel(mainPanel: Panel?) {
        Panel(mainPanel) with {
            asHorizontal()
            Label(it) with {
                text = "Nombre:"
                color = Color.BLACK
                setWidth(160)
                alignLeft()
            }
            Label(it) with {
                text = this@SuperUserWindow.modelObject?.nombre().toString()
                alignRight()
            }
        }
        Panel(mainPanel) with {
            asHorizontal()
            Label(it) with {
                text = "Apellido" + ":"
                color = Color.BLACK
                setWidth(160)
                alignLeft()
            }
            Label(it) with {
                text = this@SuperUserWindow.modelObject?.apellido().toString()
                alignRight()
            }
        }
        Panel(mainPanel) with {
            asHorizontal()
            Label(it) with {
                text = "Numero de Documento" + ":"
                color = Color.BLACK
                setWidth(160)
                alignLeft()
            }
            Label(it) with {
                text = this@SuperUserWindow.modelObject?.dni().toString()
                alignRight()
            }
        }
        Panel(mainPanel) with {
            asHorizontal()
            Label(it) with {
                text = "Es admin?"
                color = Color.BLACK
                setWidth(160)
                alignLeft()
            }
            Label(it) with {
                text = this@SuperUserWindow.modelObject?.getEsAdmin().toString()
                alignRight()
            }
        }
        Panel(mainPanel) with {
            asHorizontal()
            Label(it) with {
                text = "Cuenta CVU" + ""
                color = Color.BLACK
                setWidth(160)
                alignLeft()
            }
            Label(it) with {
                text = this@SuperUserWindow.modelObject?.user?.account?.cvu.toString()
                alignRight()
            }
        }

    }
}