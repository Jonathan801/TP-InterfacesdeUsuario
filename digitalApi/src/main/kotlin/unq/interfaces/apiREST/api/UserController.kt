package unq.interfaces.apiREST.api

import io.javalin.http.Context
import unq.interfaces.apiREST.wrapper.*
import wallet.Account
import wallet.DigitalWallet
import wallet.LoginException
import wallet.User
import kotlin.IllegalArgumentException

class UserController(var digitalWallet: DigitalWallet) {

    fun loginUser(ctx: Context){
        val loginWrapper = ctx.bodyAsClass(LoginWrapper::class.java)
        try{
            val user = digitalWallet.login(loginWrapper.email,loginWrapper.password)
            val userAccountWrapper = UserWithAccountWrapper(user)
            ctx.json(userAccountWrapper)
        }catch(e: LoginException){
            ctx.status(401)
            ctx.json("email o password incorrectos")
        }
    }

    fun getUserByCVU(ctx: Context){
        try{
            var user = getUser(ctx.pathParam("cvu"))
            var userWrapper = UserWithAccountWrapper(user)
            ctx.status(200)
            ctx.json(userWrapper)
        } catch (e : NoSuchElementException){
            ctx.status(401)
            ctx.json(e.message!!)
        }
    }

    fun changeUser(ctx: Context){
        var changeWrapper = ctx.bodyAsClass(ChangeWrapper::class.java)
        try{
            var user = getUser(ctx.pathParam("cvu"))
            //var users = digitalWallet.users.map { UserWrapper(it) }.toMutableList()
            var users = digitalWallet.users.filter { it.email != user.email }.toMutableList()
            changeWrapper.validarCampos(users)
            user?.email = changeWrapper.email
            user?.firstName = changeWrapper.firstName
            var userWrapper = UserWithAccountWrapper(user)
            ctx.status(200)
            ctx.json(userWrapper)
        } catch(e:IllegalArgumentException){
            ctx.status(401)
            ctx.json(e.message!!)
        } catch (e:NoSuchElementException){
            ctx.status(401)
            ctx.json(e.message!!)
        }
    }

    fun getUser(cvu:String) =
        digitalWallet.users.firstOrNull{it.account!!.cvu == cvu}
            ?: throw NoSuchElementException("User with ${cvu} doesn't exist")


    fun registerUser(ctx: Context){
        val register = ctx.bodyAsClass(RegisterWrapper::class.java)
        var user = User(register.idCard,register.firstName,register.lastName,
            register.email,register.password,false)
        //var user2 = User("","","","","",false);
        try{
            //var account2 = Account(user2,"1234") //esto
            //digitalWallet.accounts.add(account2) // esto
            register.validarCampos()
            digitalWallet.register(user)
            var account = Account(user,DigitalWallet.generateNewCVU())
            digitalWallet.assignAccount(user, account)
            digitalWallet.addGift(DigitalWallet.createGift(account, 200.0))
            var userWithAccountWrapper = UserWithAccountWrapper(user)
            ctx.json(userWithAccountWrapper)
            ctx.status(201)
        }catch(e: IllegalArgumentException) {
            ctx.json("idCard ${user.idCard} o email ${user.email} ya registrados")
            ctx.status(403)
        }catch (e:RegisterError){
            ctx.json(e.message!!)
            ctx.status(400)
        }
    }

    fun getUsersList(ctx:Context){
        val users = digitalWallet.users.map{ SuperUserWrapper(it) }
        ctx.json(users)
    }

    fun deleteUser(ctx: Context){
        try {
            val account = digitalWallet.accountByCVU(ctx.pathParam("cvu"))
            digitalWallet.deleteUser(account.user)
            ctx.json(UserWrapper(account.user))
            ctx.status(204)
        } catch (e:NoSuchElementException){
            ctx.json("no existe una cuenta con cvu ${ctx.pathParam("cvu")}")
            ctx.status(401)
        } catch (e:IllegalArgumentException){
            ctx.status(401)
            ctx.json("No se puede eliminar un usuario con dinero")
        }
    }

    fun blockUser(ctx: Context){
        try{
            val account = digitalWallet.accountByCVU(ctx.pathParam("cvu"))
            digitalWallet.blockAccount(account)
            ctx.json("cuenta ${account.cvu} bloqueada")
            ctx.status(200)
        }catch (e:NoSuchElementException){
            ctx.json("no existe una cuenta con cvu ${ctx.pathParam("cvu")}")
            ctx.status(401)
        }
    }

    fun unblockUser(ctx: Context){
        try{
            val account = digitalWallet.accountByCVU(ctx.pathParam("cvu"))
            digitalWallet.unblockAccount(account)
            ctx.json("cuenta ${account.cvu} desbloqueada")
            ctx.status(200)
        }catch (e:NoSuchElementException){
            ctx.json("no existe una cuenta con cvu ${ctx.pathParam("cvu")}")
            ctx.status(401)
        }
    }

}

