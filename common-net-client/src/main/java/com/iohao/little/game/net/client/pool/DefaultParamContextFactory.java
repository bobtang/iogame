package com.iohao.little.game.net.client.pool;

import com.iohao.little.game.action.skeleton.core.DefaultParamContext;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class DefaultParamContextFactory implements PooledObjectFactory<DefaultParamContext> {

    @Override
    public void activateObject(PooledObject<DefaultParamContext> pooledObject) {
        // 激活一个对象，使其可用用

    }

    @Override
    public void destroyObject(PooledObject<DefaultParamContext> pooledObject) {
        // 销毁对象
    }

    @Override
    public PooledObject<DefaultParamContext> makeObject() {
        // 构造一个封装对象
        return new DefaultPooledObject<>(new DefaultParamContext());
    }

    @Override
    public void passivateObject(PooledObject<DefaultParamContext> pooledObject) {
        // 钝化一个对象,也可以理解为反初始化
        var o = pooledObject.getObject();
        o.setAsyncCtx(null);
        o.setBizCtx(null);
    }

    @Override
    public boolean validateObject(PooledObject<DefaultParamContext> pooledObject) {
        // 验证对象是否可用
        return true;
    }
}