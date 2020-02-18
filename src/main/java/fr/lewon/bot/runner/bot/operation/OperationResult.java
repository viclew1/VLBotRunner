package fr.lewon.bot.runner.bot.operation;

public class OperationResult {

    private boolean success;
    private String message;
    private Object content;

    public OperationResult(boolean success, String message, Object content) {
        this.success = success;
        this.message = message;
        this.content = content;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getContent() {
        return this.content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "OperationResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", content=" + content +
                '}';
    }
}
