package com.ztesoft.zsmart.remoting.core.protocol;

public class RemotingSysResponseCode {
    
    public static final int SUCCESS = 0; //成功
    
    public static final int SYSTEM_ERROR = 1;//发生了未捕获异常
    
    public static final int SYSTEM_BUSY = 2;//由于线程池拥堵 系统繁忙
    
    public static final int REQUEST_CODE_NOT_SUPPORTED = 3;// 请求代码不支持
    
    public static final int TRANSACTION_FAILED = 4;//事务失败 
}
