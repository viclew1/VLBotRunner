package fr.lewon.bot.runner.bot.operation

class OperationResult(var isSuccess: Boolean = false, var message: String = "", var resultType: ResultType = ResultType.STRING, var content: Any? = "") {

    override fun toString(): String {
        return "OperationResult(isSuccess=$isSuccess, message=$message, content=$content)"
    }

    companion object {

        fun empty(isSuccess: Boolean, message: String): OperationResult {
            return OperationResult(isSuccess, message)
        }

        fun ofString(isSuccess: Boolean, message: String, content: String): OperationResult {
            return OperationResult(isSuccess, message, ResultType.STRING, content)
        }

        fun ofList(isSuccess: Boolean, message: String, content: List<OperationResult>): OperationResult {
            return OperationResult(isSuccess, message, ResultType.LIST, content)
        }

        fun ofObject(isSuccess: Boolean, message: String, content: Any?): OperationResult {
            return OperationResult(isSuccess, message, ResultType.OBJECT, content)
        }

        fun ofGraph(isSuccess: Boolean, message: String, content: Any?): OperationResult {
            return OperationResult(isSuccess, message, ResultType.GRAPH, content)
        }

        fun ofImageUrl(isSuccess: Boolean, message: String, content: String): OperationResult {
            return OperationResult(isSuccess, message, ResultType.IMAGE_URL, content)
        }

    }

}
