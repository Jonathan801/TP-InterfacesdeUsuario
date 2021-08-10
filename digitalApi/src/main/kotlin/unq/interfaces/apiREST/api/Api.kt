package unq.interfaces.apiREST.api

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.core.util.RouteOverviewPlugin
import unq.interfaces.apiREST.wrapper.*
import wallet.BlockedAccountException
import wallet.NoMoneyException

fun main() {
    val app = Javalin.create{
        it.enableCorsForAllOrigins()
        it.registerPlugin(RouteOverviewPlugin("/routes"))}.start(7000)
    //app.get("/") { ctx -> ctx.result("") }
    var controller = AccountController()
    controller.addLoyal()
    var userController = UserController(controller.digitalWallet)
    var transferController = TransferController(controller.digitalWallet)


    app.routes {
        path("users") {
            get(userController::getUsersList)
            path("login") {
                post(userController::loginUser)
            }
            path("register") {
                post(userController::registerUser)
            }
            path("change"){
                path(":cvu"){
                    put(userController::changeUser)
                }
            }
            path(":cvu") {
                delete(userController::deleteUser)
                get(userController::getUserByCVU)
            }
            path("block") {
                path(":cvu") {
                    put(userController::blockUser)
                }
            }
            path("unblock") {
                path(":cvu") {
                    put(userController::unblockUser)
                }
            }
        }


        path("accounts") {
            get(controller::getAccounts)
            path(":cvu") {
                get(controller::getAccountByCVU)
            }
        }
    }

    app.get("/transaccions/:cvu") {
        transferController.transaccions(it)
    }

    app.post("/transfer") {
        transferController.transfer(it)
    }

    /*app.get("/account/:cvu") {
        controller.getAccountByCVU(it)

    }*/
    app.post("/cashIn") {
        transferController.cashIn(it)
    }
}