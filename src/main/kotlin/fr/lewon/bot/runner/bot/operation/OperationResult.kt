package fr.lewon.bot.runner.bot.operation

class OperationResult(var isSuccess: Boolean = false, var message: String = "", var content: Any? = "") {

    override fun toString(): String {
        return "OperationResult(isSuccess=$isSuccess, message=$message, content=$content)"
    }

}
