package com.iohao.little.game.common.kit;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import lombok.experimental.UtilityClass;

import java.util.Objects;

/**
 * @author 洛朱
 * @date 2022-01-11
 */
@UtilityClass
public class ProtoKit {
    final byte[] emptyBytes = new byte[0];

    /**
     * 将对象转为 pb 字节数组
     *
     * @param data 对象
     * @return 字节数组 （一定不为null）
     */
    @SuppressWarnings("unchecked")
    public byte[] toBytes(Object data) {

        if (Objects.isNull(data)) {
            return emptyBytes;
        }

        Class clazz = data.getClass();
        Codec<Object> codec = ProtobufProxy.create(clazz);

        try {
            return codec.encode(data);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return emptyBytes;
    }

    /**
     * 将字节解析成 pb 对象
     *
     * @param dataContent pb 字节
     * @param clazz       pb class
     * @param <T>         t
     * @return pb 对象
     */
    public <T> T parseProtoByte(byte[] dataContent, Class<T> clazz) {

        if (Objects.isNull(dataContent) || dataContent.length == 0) {
            return null;
        }

        Codec<T> codec = ProtobufProxy.create(clazz);

        try {
            return codec.decode(dataContent);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }
}
