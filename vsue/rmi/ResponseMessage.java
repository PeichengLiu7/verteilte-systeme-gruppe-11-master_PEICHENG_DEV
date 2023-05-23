package vsue.rmi;

import java.io.Serializable;

public class ResponseMessage implements Serializable {
    private Object result;
    private Throwable exception;

    public ResponseMessage(Object result, Throwable exception) {
        this.result = result;
        this.exception = exception;
    }

    // getters
    public Object getResult() {
        return result;
    }

    public Throwable getException() {
        return exception;
    }
}

