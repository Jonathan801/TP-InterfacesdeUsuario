package viewModel

import org.uqbar.arena.bindings.ValueTransformer
import org.uqbar.commons.model.annotations.Observable
import org.uqbar.commons.model.exceptions.UserException
import wallet.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Observable
class GiftViewModel(var digitalWallet: DigitalWallet) {
    var gifts = digitalWallet.loyaltyGifts.map{it -> LoyaltyGiftViewModel(it) }.toMutableList()
    var name: String = ""
    var strategy: LoyaltyGiftStrategy = DiscountGiftStrategy(0.0)
    var minNumberOfTransactions: Int = 0
    var minAmountPerTransaction: Double = 0.0
    var validFrom: LocalDate = LocalDate.now()
    var validTo: LocalDate = LocalDate.now()
    var options = mutableListOf<String>()
       init {
           options.add("Regalo")
           options.add("Descuento")
       }
    var itemSelected : String? = null
    var porcentaje : Double = 0.0
    var importe : Double = 0.0
}

class DateTransformer : ValueTransformer<LocalDate, String> {
    private val pattern = "dd/MM/yyyy"
    private val formatter = DateTimeFormatter.ofPattern(pattern)
    override fun getModelType() = LocalDate::class.java
    override fun getViewType() = String::class.java
    override fun modelToView(valueFromModel: LocalDate): String =
        valueFromModel.format(formatter)

    override fun viewToModel(valueFromView: String): LocalDate {
        try {
            return LocalDate.parse(valueFromView, formatter)
        } catch (e: DateTimeParseException) {
            throw IllegalArgumentException("Fecha incorrecta, usar $pattern", e)
        }
    }
}
@Observable
class LoyaltyGiftViewModel(var loyal: LoyaltyGift){
   var nombre = loyal.name
    var desde = loyal.validFrom
    var hasta = loyal.validTo
    var cantidad = loyal.minNumberOfTransactions
    var cantidadmoney = loyal.minAmountPerTransaction
    var str = if(loyal.strategy is DiscountGiftStrategy){
        "Descuento"
    }else{
        "Regalo"
    }
}