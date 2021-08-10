package unq.interfaces.apiREST.wrapper

import unq.interfaces.apiREST.api.NegativeAmountError

data class TransferWrapper(var fromCVU :String,var toCVU: String,var amount:Double){

    fun validarAmount(){
        require(amount > 0){throw NegativeAmountError("No se puede enviar dinero negativo") }
    }
}

