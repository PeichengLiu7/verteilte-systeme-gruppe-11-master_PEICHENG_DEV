package vsue.rmi;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

public class VSInvocationHandler implements InvocationHandler , Serializable {
    private final VSRemoteReference remoteReference;

    public VSInvocationHandler(VSRemoteReference remoteReference) {
        this.remoteReference = remoteReference;
    }

    public Object invoke(Object proxy , Method method , Object[] args) throws Throwable {
        // Open connection to the server
        //参数是服务器的地址和端口号，调用方法传递的实际参数是服务器的地址和端口号
        VSObjectConnection connection = new VSObjectConnection(remoteReference.getHost(), remoteReference.getPort());

        // Generate and send request
        // Consider creating a RequestMessage class to encapsulate the request
//        vsue.rmi.RequestMessage

//        RequestMessage request = new RequestMessage() {
//
//}
