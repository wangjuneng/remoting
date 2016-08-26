package com.ztesoft.zsmart.remoting.core.exception;

/**
 * 
 * <Description>RPC 调用中 客户端发送请求失败  <br> 
 *  
 * @author wang.jun<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月26日 <br>
 * @since V7.3<br>
 * @see com.ztesoft.zsmart.remoting.core.exception <br>
 */
public class RemotingSendRequestException extends RemotingException {

    /*
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = -6820356757208948061L;

    public RemotingSendRequestException(String message) {
        super(message);
    }

    public RemotingSendRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
