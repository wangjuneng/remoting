package com.ztesoft.zsmart.remoting.core.protocol;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.alibaba.fastjson.annotation.JSONField;
import com.ztesoft.zsmart.remoting.core.CommandCustomHeader;
import com.ztesoft.zsmart.remoting.core.annotation.CFNotNull;
import com.ztesoft.zsmart.remoting.core.exception.RemotingCommandException;

public class RemotingCommand {
    public static String RemotingVersionKey = "remoting.version";

    private static volatile int ConfigVersion = -1;

    private static AtomicLong RequestId = new AtomicLong(0);

    private static final int RPC_TYPE = 0;// 0 REQUEST_COMMAND 1 RESPONSE_COMMAND

    private static final int RPC_ONEWAY = 1;// 0 PRC 1 OneWay

    // header 部分
    private int code;

    private LanguageCode language = LanguageCode.JAVA;

    private int version = 0;

    private long opaque = 0;

    private int flag = 0;

    private String remark;

    private HashMap<String, String> extFields;

    private transient CommandCustomHeader customHeader;

    private transient byte[] body; // 协议请求体

    protected RemotingCommand() {

    }

    /**
     * Description:创建请求command <br>
     * 
     * @author wang.jun<br>
     * @taskId <br>
     * @param code
     * @param customHeader
     * @return <br>
     */
    public static RemotingCommand createRequestCommand(int code, CommandCustomHeader customHeader) {
        RemotingCommand cmd = new RemotingCommand();
        cmd.setCode(code);
        cmd.customHeader = customHeader;
        setCmdVersion(cmd);
        return cmd;
    }

    public static RemotingCommand createResponseCommand(Class<? extends CommandCustomHeader> classHeader) {
        return createResponseCommand(RemotingSysResponseCode.SYSTEM_ERROR, "not set any response code", classHeader);
    }

    public static RemotingCommand createResponseCommand(int code, String remark) {
        return createResponseCommand(code, remark, null);
    }

    /**
     * Description: <br>
     * 
     * @author wang.jun<br>
     * @taskId <br>
     * @param code
     * @param remark
     * @param classHeader
     * @return <br>
     */
    public static RemotingCommand createResponseCommand(int code, String remark,
        Class<? extends CommandCustomHeader> classHeader) {
        RemotingCommand cmd = new RemotingCommand();
        cmd.markResponseType();
        cmd.setCode(code);
        cmd.setRemark(remark);
        setCmdVersion(cmd);
        if (classHeader != null) {
            try {
                CommandCustomHeader objHeader = classHeader.newInstance();
                cmd.customHeader = objHeader;
            }
            catch (InstantiationException e) {
                return null;
            }
            catch (IllegalAccessException e) {
                return null;
            }
        }

        return cmd;
    }

    private static void setCmdVersion(RemotingCommand cmd) {
        if (ConfigVersion >= 0) {
            cmd.setVersion(ConfigVersion);
        }
        else {
            String v = System.getProperty(RemotingVersionKey);
            if (v != null) {
                int value = Integer.parseInt(v);
                cmd.setVersion(value);
                ConfigVersion = value;
            }
        }
    }

    public void makeCustomHeaderToNet() {
        if (this.customHeader != null) {
            Field[] fields = this.customHeader.getClass().getDeclaredFields();

            if (null == this.extFields) {
                for (Field field : fields) {
                    if (!Modifier.isStatic(field.getModifiers())) {
                        String name = field.getName();
                        if (!name.startsWith("this")) {
                            Object value = null;
                            try {
                                field.setAccessible(true);
                                value = field.get(this.customHeader);
                            }
                            catch (IllegalArgumentException e) {
                            }
                            catch (IllegalAccessException e) {
                            }

                            if (value != null) {
                                this.extFields.put(name, value.toString());
                            }
                        }
                    }
                }
            }
        }
    }

    public CommandCustomHeader readCustomHeader() {
        return customHeader;
    }

    private static final String StringName = String.class.getCanonicalName();

    private static final String IntegerName1 = Integer.class.getCanonicalName();//

    private static final String IntegerName2 = int.class.getCanonicalName();//

    private static final String LongName1 = Long.class.getCanonicalName();//

    private static final String LongName2 = long.class.getCanonicalName();//

    private static final String BooleanName1 = Boolean.class.getCanonicalName();//

    private static final String BooleanName2 = boolean.class.getCanonicalName();//

    private static final String DoubleName1 = Double.class.getCanonicalName();//

    private static final String DoubleName2 = double.class.getCanonicalName();//

    public CommandCustomHeader decodeCommandCustomHeader(Class<? extends CommandCustomHeader> classHeader)
        throws RemotingCommandException {
        if (this.extFields != null) {
            CommandCustomHeader objectHeader;
            try {
                objectHeader = classHeader.newInstance();
            }
            catch (InstantiationException e) {
                return null;
            }
            catch (IllegalAccessException e) {
                return null;
            }

            // 检查返回对象是否有效
            Field[] fields = objectHeader.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    String fieldName = field.getName();
                    if (!fieldName.startsWith("this")) {
                        String value = this.extFields.get(fieldName);
                        try {
                            if (null == value) {
                                Annotation annotation = field.getAnnotation(CFNotNull.class);
                                if (annotation != null) {
                                    throw new RemotingCommandException("the custom field <" + fieldName + "> is null");
                                }

                                continue;
                            }
                            field.setAccessible(true);
                            String type = field.getType().getCanonicalName();
                            Object valueParsed = null;

                            if (type.equals(StringName)) {
                                valueParsed = value;
                            }
                            else if (type.equals(IntegerName1) || type.equals(IntegerName2)) {
                                valueParsed = Integer.parseInt(value);
                            }
                            else if (type.equals(LongName1) || type.equals(LongName2)) {
                                valueParsed = Long.parseLong(value);
                            }
                            else if (type.equals(BooleanName1) || type.equals(BooleanName2)) {
                                valueParsed = Boolean.parseBoolean(value);
                            }
                            else if (type.equals(DoubleName1) || type.equals(DoubleName2)) {
                                valueParsed = Double.parseDouble(value);
                            }
                            else {
                                throw new RemotingCommandException("the custom field <" + fieldName
                                    + "> type is not supported");
                            }

                            field.set(objectHeader, valueParsed);
                        }
                        catch (Throwable e) {
                        }
                    }
                }
            }
            objectHeader.checkFields();
            return objectHeader;
        }

        return null;
    }

    private byte[] buildHeader() {
        this.makeCustomHeaderToNet();
        return RemotingSerializable.encode(this);
    }

    public ByteBuffer encode() {
        // 1> header length size
        int length = 4;

        // 2> header data length
        byte[] headerData = this.buildHeader();
        length += headerData.length;

        // 3> body data length
        if (this.body != null) {
            length += body.length;
        }

        ByteBuffer result = ByteBuffer.allocate(4 + length);

        // length
        result.putInt(length);

        // header length
        result.putInt(headerData.length);

        // header data
        result.put(headerData);

        // body data
        if (this.body != null) {
            result.put(this.body);
        }

        result.flip();

        return result;
    }

    public ByteBuffer encodeHeader() {
        return encodeHeader(this.body != null ? this.body.length : 0);
    }

    /**
     * 只打包Header，body部分独立传输
     */
    public ByteBuffer encodeHeader(final int bodyLength) {
        // 1> header length size
        int length = 4;

        // 2> header data length
        byte[] headerData = this.buildHeader();
        length += headerData.length;

        // 3> body data length
        length += bodyLength;

        ByteBuffer result = ByteBuffer.allocate(4 + length - bodyLength);

        // length
        result.putInt(length);

        // header length
        result.putInt(headerData.length);

        // header data
        result.put(headerData);

        result.flip();

        return result;
    }

    public static RemotingCommand decode(final byte[] array) {
        ByteBuffer buffer = ByteBuffer.wrap(array);
        return decode(buffer);
    }

    public static RemotingCommand decode(final ByteBuffer byteBuffer) {
        int length = byteBuffer.limit();
        int headerLength = byteBuffer.getInt();

        byte[] headerData = new byte[headerLength];
        byteBuffer.get(headerData);

        int bodyLength = length - 4 - headerLength;
        byte[] bodyData = null;
        if (bodyLength > 0) {
            bodyData = new byte[bodyLength];
            byteBuffer.get(bodyData);
        }

        RemotingCommand cmd = RemotingSerializable.decode(headerData, RemotingCommand.class);
        cmd.body = bodyData;

        return cmd;
    }

    public void writeCustomHeader(CommandCustomHeader customHeader) {
        this.customHeader = customHeader;
    }

    public void markResponseType() {
        int bits = 1 << RPC_TYPE;
        this.flag |= bits;
    }

    @JSONField(serialize = false)
    public boolean isResponseType() {
        int bits = 1 << RPC_TYPE;
        return (this.flag & bits) == bits;
    }

    public void markOnewayRPC() {
        int bits = 1 << RPC_ONEWAY;
        this.flag |= bits;
    }

    @JSONField(serialize = false)
    public boolean isOnewayRPC() {
        int bits = 1 << RPC_ONEWAY;
        return (this.flag & bits) == bits;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public LanguageCode getLanguage() {
        return language;
    }

    public void setLanguage(LanguageCode language) {
        this.language = language;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public long getOpaque() {
        return opaque;
    }

    public void setOpaque(long opaque) {
        this.opaque = opaque;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public HashMap<String, String> getExtFields() {
        return extFields;
    }

    public void setExtFields(HashMap<String, String> extFields) {
        this.extFields = extFields;
    }

    public static long createNewRequestId() {
        return RequestId.incrementAndGet();
    }

    public void addExtField(String key, String value) {
        if (null == extFields) {
            extFields = new HashMap<String, String>();
        }
        extFields.put(key, value);
    }

    @Override
    public String toString() {
        return "RemotingCommand [code=" + code + ", language=" + language + ", version=" + version + ", opaque="
            + opaque + ", flag(B)=" + Integer.toBinaryString(flag) + ", remark=" + remark + ", extFields=" + extFields
            + "]";
    }

}
