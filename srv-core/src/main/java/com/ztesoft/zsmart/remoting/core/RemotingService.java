package com.ztesoft.zsmart.remoting.core;

public interface RemotingService {
    public void start();
    
    public void shtudown();
    
    public void registerRPCHook();
}
