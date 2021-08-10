package unq.interfaces.apiREST.api

class RegisterError(message: String?) : Exception(message)

class NegativeAmountError(message: String?) : Exception(message)

class CashInError(message: String?) : Exception(message)