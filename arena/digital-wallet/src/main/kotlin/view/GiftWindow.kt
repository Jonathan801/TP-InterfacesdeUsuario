package view

import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException
import viewModel.DateTransformer
import viewModel.GiftViewModel
import viewModel.ManagementViewModel
import wallet.DiscountGiftStrategy
import wallet.FixedGiftStrategy
import wallet.LoyaltyGift

open class GiftWindow : SimpleWindow<GiftViewModel> {

    constructor(owner: WindowOwner,model:GiftViewModel):
            super(owner,model)

    override fun addActions(mainPanel: Panel?) {
        Button(mainPanel) with{
            caption = "Cancelar"
            width = 250
            onClick {
                close()
                var modelManagement = ManagementViewModel(modelObject.digitalWallet)
                ManagementWindow(this@GiftWindow,modelManagement).open()
            }
        }
    }

    override fun createFormPanel(mainPanel: Panel?) {
        Panel(mainPanel) with{
            asHorizontal()
            Label(it) with{
                text = "Nombre"
            }
            TextBox(it) with {
                bindTo("name")
            }
        }
        Panel(mainPanel) with{
            asHorizontal()
            Label(it) with{
                text = "Fecha Desde:"
            }
            TextBox(it) with{
                bindTo("validFrom").setTransformer(DateTransformer())
            }
        }
        Panel(mainPanel) with{
            asHorizontal()
            Label(it) with{
                text = "Fecha Hasta:"
            }
            TextBox(it) with{
                bindTo("validTo").setTransformer(DateTransformer())
            }
        }
        Panel(mainPanel) with{
            asHorizontal()
            Label(it) with{
                text = "Cantidad de operaciones"
            }
            TextBox(it) with{
                bindTo("minNumberOfTransactions")
                width = 50
            }
        }
        Panel(mainPanel) with{
            asHorizontal()
            Label(it) with{
                text = "Importe de cada operacion"
            }
            TextBox(it) with{
                bindTo("minAmountPerTransaction")
                width = 50
            }
        }

    }
}

class DiscountWindow : GiftWindow{
    constructor(owner: WindowOwner,model: GiftViewModel):
            super(owner, model)

    override fun createFormPanel(mainPanel: Panel?) {
        super.createFormPanel(mainPanel)
        Panel(mainPanel) with{
            asHorizontal()
            Label(it) with{
                text = "Porcentaje de descuento"
            }
            TextBox(it) bindTo "porcentaje"
            width = 50
        }
        Button(mainPanel) with{
            caption = "Agregar"
            onClick {
                try {
                    validarCampos()
                    close()
                    var discount = DiscountGiftStrategy(modelObject.porcentaje)
                    var loyaltyGift = LoyaltyGift(
                        modelObject.name,
                        discount,
                        modelObject.minNumberOfTransactions,
                        modelObject.minAmountPerTransaction,
                        modelObject.validFrom,
                        modelObject.validTo
                    )
                    modelObject.digitalWallet.addLoyalty(loyaltyGift)
                    var modelManagement = ManagementViewModel(modelObject.digitalWallet)
                    ManagementWindow(this@DiscountWindow, modelManagement).open()
                }catch(e:IllegalArgumentException){
                    throw UserException(e.message!!)
                }
            }
        }
    }

    private fun validarCampos() {
        require(modelObject.name != ""){throw IllegalArgumentException("El nombre no puede estar vacio")}
        require(modelObject.porcentaje != 0.0){throw IllegalArgumentException("El campo porcentaje tiene que ser un numero distinto de 0")}
        require(modelObject.minAmountPerTransaction != 0.0){throw IllegalArgumentException("El campo importe tiene que ser un numero distinto de 0")}
        require(modelObject.minNumberOfTransactions != 0){throw IllegalArgumentException("El campo operaciones debe ser un numero superior a 0")}

    }
}

class CashGiftWindow : GiftWindow{

    constructor(owner: WindowOwner,model: GiftViewModel):
            super(owner, model)

    override fun createFormPanel(mainPanel: Panel?) {
        super.createFormPanel(mainPanel)
        Panel(mainPanel) with{
            Label(it) with{
                text = "Importe de regalo"
            }
            TextBox(it) bindTo "importe"
            width = 50
        }
        Button(mainPanel) with{
            caption = "Aplicar"
            onClick {
                close()
                var gift = FixedGiftStrategy(modelObject.importe)
                var loyaltyGift = LoyaltyGift(
                    modelObject.name,
                    gift,
                    modelObject.minNumberOfTransactions,
                    modelObject.minAmountPerTransaction,
                    modelObject.validFrom,
                    modelObject.validFrom
                )
                modelObject.digitalWallet.addLoyalty(loyaltyGift)
                var modelManagement = ManagementViewModel(modelObject.digitalWallet)
                ManagementWindow(this@CashGiftWindow,modelManagement).open()
            }
        }
    }
}