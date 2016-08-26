package com.ztesoft.zsmart.remoting.core;

import com.ztesoft.zsmart.remoting.core.exception.RemotingCommandException;

public interface CommandCustomHeader {
    void checkFields () throws RemotingCommandException;
}
