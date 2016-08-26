package com.ztesoft.zsmart.remoting.core;

import java.util.List;
import java.util.concurrent.ExecutorService;

import com.ztesoft.zsmart.remoting.core.exception.RemotingConnectException;
import com.ztesoft.zsmart.remoting.core.exception.RemotingSendRequestException;
import com.ztesoft.zsmart.remoting.core.exception.RemotingTimeoutException;
import com.ztesoft.zsmart.remoting.core.netty.NettyRequestProcessor;
import com.ztesoft.zsmart.remoting.core.protocol.RemotingCommand;

/**
 * <Description>远程通信 client接口 <br>
 * 
 * @author wang.jun<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月26日 <br>
 * @since V7.3<br>
 * @see com.ztesoft.zsmart.remoting.core <br>
 */
public interface RemotingClient extends RemotingService {
    public void updateNameServerAddressList(final List<String> addressList);

    public List<String> getNameServerAddressList();

    public RemotingCommand invokeSync(final String addr, final RemotingCommand request, final long timeoutMillis)
        throws InterruptedException, RemotingConnectException, RemotingSendRequestException, RemotingTimeoutException;

    public RemotingCommand invokeASync(final String addr, final RemotingCommand request, final long timeoutMillis)
        throws InterruptedException, RemotingConnectException, RemotingSendRequestException, RemotingTimeoutException;

    public RemotingCommand invokeOneway(final String addr, final RemotingCommand request, final long timeoutMillis)
        throws InterruptedException, RemotingConnectException, RemotingSendRequestException, RemotingTimeoutException;

    public void registerProcessor(final int requestCode, final NettyRequestProcessor processor,
        final ExecutorService executor);

    public boolean isChannelWriteable(final String addr);
}
