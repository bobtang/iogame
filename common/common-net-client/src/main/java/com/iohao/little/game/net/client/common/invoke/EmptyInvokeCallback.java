package com.iohao.little.game.net.client.common.invoke;

import com.alipay.remoting.InvokeCallback;

import java.util.concurrent.Executor;

public class EmptyInvokeCallback implements InvokeCallback {
    @Override
    public void onResponse(Object result) {

    }

    @Override
    public void onException(Throwable e) {

    }

    @Override
    public Executor getExecutor() {
        return null;
    }
}
