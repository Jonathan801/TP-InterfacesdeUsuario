package unq.interfaces.apiREST.wrapper

import wallet.Transactional
import java.time.LocalDate

data class TransacctionWrapper(val amount: Double, val dateTime: DateWrapper, val description: String, val cashOut: Boolean) {
    constructor(trans: Transactional) :
            this(trans.amount, DateWrapper(trans.dateTime),trans.description(), trans.isCashOut())
}