package com.yr.util;

import java.io.*;

/**
 * 
 * 开发公司：SOJSON在线工具 <p>
 * 版权所有：© www.sojson.com<p>
 * 博客地址：http://www.sojson.com/blog/  <p>
 * <p>
 * 
 * Java原生版的 Serialize
 * 
 * <p>
 * 
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　周柏成　2016年6月2日 　<br/>
 *
 * @author zhou-baicheng
 * @email  so@sojson.com
 * @version 1.0,2016年6月2日 <br/>
 * 
 */
@SuppressWarnings("unchecked")
public class SerializeUtil {
	static final Class<?> CLAZZ = SerializeUtil.class;

    /**
     * 将对象序列化成byte数组的流
     * @param value
     * @return byte[]
     */
    public static byte[] serialize(Object value) {
        if (value == null) { 
            throw new NullPointerException("Can't serialize null");
        }
        byte[] rv = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(value);
            os.close();
            bos.close();
            rv = bos.toByteArray();
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            close(os);
            close(bos);
        }
        return rv;
    }

    /**
     * 将byte数组的流还原成对象
     * @param in
     * @return
     */
	public static Object deserialize(byte[] in) {
        return deserialize(in, Object.class);
    }

    /**
     * 根据byte数组,class返回一个指定类型的对象
     * @param in
     * @param requiredType
     * @param <T>
     * @return T
     */
    public static <T> T deserialize(byte[] in, Class<T>...requiredType) {
        Object rv = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                rv = is.readObject();
            }
        } catch (Exception e) {
        	 e.printStackTrace();
        } finally {
            close(is);
            close(bis);
        }
        return (T) rv;
    }

    private static void close(Closeable closeable) {
        if (closeable != null)
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

}
