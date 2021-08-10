package unq.interfaces.apiREST.wrapper

import unq.interfaces.apiREST.api.CashInError
import unq.interfaces.apiREST.api.NegativeAmountError
import wallet.Card
import wallet.CreditCard
import wallet.DebitCard
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.lang.Double.parseDouble

data class CashInWrapper(val fromCVU: String, val amount: Double, var cardNumber: String, var fullName: String, var endDate: String, var securityCode: String, var type: String) {
    fun toType(): Card {
        val card =
                when {
                    type == "Credit" -> CreditCard(this.cardNumber, this.fullName, this.convertidorADate("01/" + endDate), this.securityCode)
                    else -> DebitCard(this.cardNumber, this.fullName, this.convertidorADate("01/" + endDate), this.securityCode)
                }
        return card
    }

    fun convertidorADate(tar: String): LocalDate {
        val pattern = "dd/MM/yyyy"
        val valueFromView = tar
        val formatter = DateTimeFormatter.ofPattern(pattern)
        try {
            return LocalDate.parse(valueFromView, formatter)
        } catch (e: DateTimeParseException) {
            throw IllegalArgumentException("Fecha incorrecta, usar $pattern", e)
        }
    }

  fun validarCampo() {
      require(amount > 0) { throw NegativeAmountError("No se puede enviar dinero negativo") }
      require(cardNumber != ""){throw CashInError("El campo cardNumber no puede estar vacio")}
      require(fullName != ""){throw CashInError("El campo fullName no puede estar vacio")}
      require(endDate != ""){throw CashInError("El campo endDate no puede estar vacio")}
      require(securityCode.length == 3){throw CashInError("El codigo de seguridad debe ser de 3 numeros")}
      validateAmount()

  }

  fun validateAmount(){
     try {
         var num = parseDouble(amount.toString())
     } catch (e:NumberFormatException){
         throw IllegalArgumentException("Amount tiene que ser un numero")
     }
  }
}