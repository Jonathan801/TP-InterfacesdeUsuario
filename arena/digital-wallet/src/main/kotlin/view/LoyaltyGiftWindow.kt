package view

import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.widgets.tables.Column
import org.uqbar.arena.widgets.tables.Table
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import viewModel.GiftViewModel
import viewModel.LoyaltyGiftViewModel

class LoyaltyGiftWindow : SimpleWindow<GiftViewModel> {
    override fun addActions(actionsPanel: Panel?) {
    }

    override fun createFormPanel(mainPanel: Panel?) {
        title = "Digital Wallet - Beneficios Registrados"
        Table(mainPanel, LoyaltyGiftViewModel::class.java) with {
            it bindItemsTo "gifts"
            //it bindSelectionTo "selectUser"
            setNumberVisibleRows(10)
            Column(it) with {
                title = "Nombre de Beneficio"
                width = 2000
                fixedSize = 250
                bindContentsToProperty("nombre")
            }
            Column(it) with {
                title = "Desde"
                width = 2000
                fixedSize = 250
                bindContentsToProperty("desde")
            }
            Column(it) with {
                title = "Hasta"
                width = 2000
                fixedSize = 250
                bindContentsToProperty("hasta")
            }
            Column(it) with {
                title = "Cantidad Minima de Transacciones"
                width = 2000
                fixedSize = 250
                bindContentsToProperty("cantidad")
            }
            Column(it) with {
                title = "Monto minimo por Transaccion"
                width = 2000
                fixedSize = 250
                bindContentsToProperty("cantidadmoney")
            }
            Column(it) with {
                title = "Tipo de regalo"
                width = 2000
                fixedSize = 250
                bindContentsToProperty("str")
            }
        }        }

    constructor(owner: WindowOwner, model: GiftViewModel):
            super(owner,model)
}