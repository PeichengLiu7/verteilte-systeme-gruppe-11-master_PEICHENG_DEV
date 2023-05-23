package vsue.rmi;

import java.io.Serializable;

public class RequestMessage implements Serializable {
    private int objectID;
    private String methodName;
    private Object[] args;

    public RequestMessage(int objectID, String methodName, Object[] args) {
        this.objectID = objectID;
        this.methodName = methodName;
        this.args = args;
    }

    // getters
    public int getObjectID() {
        return objectID;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getArgs() {
        return args;
    }
}

