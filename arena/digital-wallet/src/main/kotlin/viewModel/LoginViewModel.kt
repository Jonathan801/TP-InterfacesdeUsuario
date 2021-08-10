package viewModel

import org.uqbar.commons.model.annotations.Observable
import wallet.DigitalWallet

@Observable
class LoginViewModel(var digital: DigitalWallet) {
    var mail : String = ""
    var password = ""
//    var digital : DigitalWallet = wallet




}