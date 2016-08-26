package com.ztesoft.zsmart.remoting.core.exception;

/**
 * 
 * <Description>RPC 调用超时异常 <br> 
 *  
 * @author wang.jun<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月26日 <br>
 * @since V7.3<br>
 * @see com.ztesoft.zsmart.remoting.core.exception <br>
 */
public class RemotingTimeoutException extends RemotingException {

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = 2348426278156636525L;

    public RemotingTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
 
    public RemotingTimeoutException(String addr, long timeoutMillis) {
        this(addr, timeoutMillis, null);
    }


    public RemotingTimeoutException(String addr, long timeoutMillis, Throwable cause) {
        super("wait response on the channel <" + addr + "> timeout, " + timeoutMillis + "(ms)", cause);
    }
}
