package unq.interfaces.apiREST.api

import data.DigitalWalletData
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse
import unq.interfaces.apiREST.wrapper.*
import wallet.BlockedAccountException
import wallet.DigitalWallet
import wallet.NoMoneyException

class TransferController(var digitalWallet: DigitalWallet) {

    fun transaccions(ctx: Context){
        var user = digitalWallet.accounts.firstOrNull{it.cvu==ctx.pathParam("cvu")}
                ?: throw NotFoundResponse("Invalid cvu")

        ctx.json((user.transactions.map { TransacctionWrapper(it) }))
    }

    fun cashIn(ctx: Context) {
        var trans = ctx.body<CashInWrapper>()
        try {
            ////digitalWallet.accountByCVU(trans.fromCVU) esta linea se comprueba en el metodo tranferMoneyFromCard
            trans.validarCampo()
            digitalWallet.transferMoneyFromCard(trans.fromCVU, trans.toType(), trans.amount)
            ctx.json("Se ha realizado la operacion con exito")
        } catch (e: NoSuchElementException) {
            ctx.json(e.message!!)
            //throw NotFoundResponse("Invalid cvu")
            ctx.status(404)
        } catch (e: IllegalArgumentException) {
            ctx.json(e.message!!)
            ctx.status(404)
        } catch (e: BlockedAccountException) {
            //ctx.json("No se puede operar con un usuario bloqueado")
            ctx.json(e.message!!)
            ctx.status(403)
        } catch (e:NegativeAmountError){
            ctx.json(e.message!!)
            ctx.status(400)
        } catch (e:CashInError){
            ctx.json(e.message!!)
            ctx.status(400)
        }
    }
    fun transfer(ctx: Context){
        val transferWrapper = ctx.bodyAsClass(TransferWrapper::class.java)

        try {
            transferWrapper.validarAmount()
            this.digitalWallet.transfer(transferWrapper.fromCVU, transferWrapper.toCVU, transferWrapper.amount)
            ctx.json("Ok")
        }catch (e:BlockedAccountException){
            ctx.status(403)
            ctx.json("No se puede ingresar ni sustraer dinero de una cuenta bloqueada")
        }catch (e: NoMoneyException){
            ctx.status(403)
            ctx.json("No cuenta con suficiente saldo para la accion")
        }catch(e:NoSuchElementException){
            ctx.status(403)
            ctx.json(e.message!!)
        }catch (e:NegativeAmountError){
            ctx.json(e.message!!)
            ctx.status(400)
        }
    }

}