package com.ztesoft.zsmart.remoting.core.exception;

/**
 * <Description> 通信层异常父类<br>
 * 
 * @author wang.jun<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月26日 <br>
 * @since V7.3<br>
 * @see com.ztesoft.zsmart.remoting.core.exception <br>
 */
public class RemotingException extends Exception {

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = 2030522309319780548L;

    public RemotingException(String message) {
        super(message);
    }

    public RemotingException(String message, Throwable cause) {
        super(message, cause);
    }
}
