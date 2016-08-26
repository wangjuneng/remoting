package com.ztesoft.zsmart.remoting.core;

import java.util.concurrent.ExecutorService;

import io.netty.channel.Channel;

import com.ztesoft.zsmart.remoting.core.common.Pair;
import com.ztesoft.zsmart.remoting.core.exception.RemotingSendRequestException;
import com.ztesoft.zsmart.remoting.core.exception.RemotingTimeoutException;
import com.ztesoft.zsmart.remoting.core.exception.RemotingTooMuchRequestException;
import com.ztesoft.zsmart.remoting.core.netty.NettyRequestProcessor;
import com.ztesoft.zsmart.remoting.core.protocol.RemotingCommand;

/**
 * <Description> 远程通信 Server接口<br>
 * 
 * @author wang.jun<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月26日 <br>
 * @since V7.3<br>
 * @see com.ztesoft.zsmart.remoting.core <br>
 */
public interface RemotingServer {
    /**
     * 注册请求处理器，ExecutorService必须要对应一个队列大小有限制的阻塞队列，防止OOM
     * 
     * @param requestCode
     * @param processor
     * @param executor
     */
    public void registerProcessor(final int requestCode, final NettyRequestProcessor processor,
        final ExecutorService executor);

    public void registerDefaultProcessor(final NettyRequestProcessor processor, final ExecutorService executor);

    /**
     * 服务器绑定的本地端口
     * 
     * @return PORT
     */
    public int localListenPort();

    public Pair<NettyRequestProcessor, ExecutorService> getProcessorPair(final int requestCode);

    public RemotingCommand invokeSync(final Channel channel, final RemotingCommand request, final long timeoutMillis)
        throws InterruptedException, RemotingSendRequestException, RemotingTimeoutException;

    public void invokeAsync(final Channel channel, final RemotingCommand request, final long timeoutMillis,
        final InvokeCallback invokeCallback) throws InterruptedException, RemotingTooMuchRequestException,
        RemotingTimeoutException, RemotingSendRequestException;

    public void invokeOneway(final Channel channel, final RemotingCommand request, final long timeoutMillis)
        throws InterruptedException, RemotingTooMuchRequestException, RemotingTimeoutException,
        RemotingSendRequestException;
}
