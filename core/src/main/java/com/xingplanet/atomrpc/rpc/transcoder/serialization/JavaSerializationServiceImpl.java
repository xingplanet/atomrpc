package com.xingplanet.atomrpc.rpc.transcoder.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * java自带的序列化工具
 *
 * @author wangjin
 */
public class JavaSerializationServiceImpl implements SerializationService {

    @Override
    public byte[] encode(Object value) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(value);
        return bos.toByteArray();
    }

    @Override
    public Object decode(byte[] encodedBytes) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(encodedBytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
    }
}
