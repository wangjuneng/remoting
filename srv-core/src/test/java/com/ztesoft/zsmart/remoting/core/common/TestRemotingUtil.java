package com.ztesoft.zsmart.remoting.core.common;

import java.net.UnknownHostException;

import junit.framework.TestCase;

public class TestRemotingUtil extends TestCase {

    public void testLocalAddress() throws UnknownHostException{
        
        System.out.println(RemotingUtil.getLocalAddress());
    }
}
