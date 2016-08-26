package com.ztesoft.zsmart.remoting.core.exception;

/**
 * <Description> Client连接Server失败，抛出此异常 <br>
 * 
 * @author wang.jun<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月26日 <br>
 * @since V7.3<br>
 * @see com.ztesoft.zsmart.remoting.core.exception <br>
 */
public class RemotingConnectException extends RemotingException {

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = 4061966698630483792L;

    public RemotingConnectException(String message) {
        super(message);
    }

    public RemotingConnectException(String message, Throwable cause) {
        super(message, cause);
    }
}
