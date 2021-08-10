package unq.interfaces.apiREST.api

import data.DigitalWalletData
import io.javalin.http.*
import unq.interfaces.apiREST.wrapper.*
import wallet.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*
import kotlin.IllegalArgumentException
import kotlin.NoSuchElementException

class AccountController {

    var digitalWallet = DigitalWalletData.build()
    var  validFrom = LocalDate.of(2017,11,2)
    var  validTo= LocalDate.of(2020,11,2)
    var loyalty = LoyaltyGift("regalo",FixedGiftStrategy(100.0),2,5.0,validFrom,validTo)

    fun addLoyal(){
        this.digitalWallet.addLoyalty(loyalty)
    }

    fun getAccounts(ctx: Context) {
        val accounts = digitalWallet.accounts.map { AccountWrapper(it.balance, it.cvu) }
        ctx.json(accounts)
        ctx.status(200)
    }

    fun getAccountByCVU(ctx: Context) {
        try {
            val account = AccountCVUWrapper(digitalWallet.accountByCVU(ctx.pathParam("cvu")))
            ctx.json(account)
            ctx.status(200)
        } catch (e: NoSuchElementException) {
            ctx.json("No existe una cuenta con cvu ${ctx.pathParam("cvu")}")
            ctx.status(401)
        }
    }

}

