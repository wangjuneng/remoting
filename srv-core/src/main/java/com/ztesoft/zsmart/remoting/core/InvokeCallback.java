package com.ztesoft.zsmart.remoting.core;

/**
 * 
 * <Description> 异步调用应答回调接口<br> 
 *  
 * @author wang.jun<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月26日 <br>
 * @since V7.3<br>
 * @see com.ztesoft.zsmart.remoting.core <br>
 */
public interface InvokeCallback {
    public void operationComplete(final ResponseFuture responseFuture);
}
