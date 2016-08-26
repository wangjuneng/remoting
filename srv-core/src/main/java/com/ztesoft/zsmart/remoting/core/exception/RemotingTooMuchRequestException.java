package com.ztesoft.zsmart.remoting.core.exception;

/**
 * <Description> 异步调用或者Oneway调用，堆积的请求超过信号量最大值 <br>
 * 
 * @author wang.jun<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月26日 <br>
 * @since V7.3<br>
 * @see com.ztesoft.zsmart.remoting.core.exception <br>
 */
public class RemotingTooMuchRequestException extends RemotingException {
    private static final long serialVersionUID = 4326919581254519654L;

    public RemotingTooMuchRequestException(String message) {
        super(message);
    }
}
