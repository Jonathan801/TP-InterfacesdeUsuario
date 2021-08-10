package unq.interfaces.apiREST.wrapper

import wallet.Account
import wallet.Transactional

data class AccountWrapper(val amount : Double,val cvu:String) {
    constructor(account: Account):
            this(account.balance,account.cvu)
}

data class AccountCVUWrapper(val amount: Double,val cvu: String,val transacciones: MutableList<TransacctionWrapper>){
    constructor(account: Account):
            this(account.balance,account.cvu,
                    account.transactions.map { TransacctionWrapper(it) }.toMutableList())
}