package com.ztesoft.zsmart.remoting.core.protocol;

import java.nio.charset.Charset;

import com.alibaba.fastjson.JSON;

/**
 * <Description> json 序列化<br>
 * 
 * @author wang.jun<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年8月26日 <br>
 * @since V7.3<br>
 * @see com.ztesoft.zsmart.remoting.core.protocol <br>
 */
public abstract class RemotingSerializable {

    public String toJson() {
        return toJson(false);
    }

    public String toJson(boolean perttyFormat) {
        return toJson(this, perttyFormat);
    }

    public static String toJson(final Object obj, boolean prettyFormat) {
        return JSON.toJSONString(obj, prettyFormat);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return JSON.parseObject(json, classOfT);
    }

    public byte[] encode() {
        final String json = this.toJson();
        if (json != null) {
            return json.getBytes(Charset.forName("UTF-8"));
        }
        return null;
    }

    public static byte[] encode(final Object obj) {
        final String json = toJson(obj, false);
        if (json != null) {
            return json.getBytes(Charset.forName("UTF-8"));
        }
        return null;
    }

    public static <T> T decode(final byte[] data, Class<T> clazz) {
        final String json = new String(data, Charset.forName("UTF-8"));
        return fromJson(json, clazz);
    }

}
