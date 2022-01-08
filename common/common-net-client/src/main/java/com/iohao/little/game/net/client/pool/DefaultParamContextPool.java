package com.iohao.little.game.net.client.pool;

import com.iohao.little.game.action.skeleton.core.DefaultParamContext;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.time.Duration;

public class DefaultParamContextPool extends GenericObjectPool<DefaultParamContext> {

    public DefaultParamContextPool(PooledObjectFactory<DefaultParamContext> factory, GenericObjectPoolConfig<DefaultParamContext> config) {
        super(factory, config);
    }

    private DefaultParamContextPool(InnerConfig innerConfig) {
        this(innerConfig.factory, innerConfig.config);
    }

    private static class InnerConfig {
        PooledObjectFactory<DefaultParamContext> factory = new DefaultParamContextFactory();
        GenericObjectPoolConfig<DefaultParamContext> config = new GenericObjectPoolConfig<>();

        public InnerConfig() {
            this.config.setMaxTotal(1024);
            this.config.setMaxWait(Duration.ofSeconds(1));
        }
    }

    public static DefaultParamContextPool me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final DefaultParamContextPool ME = new DefaultParamContextPool(new InnerConfig());

    }
}
