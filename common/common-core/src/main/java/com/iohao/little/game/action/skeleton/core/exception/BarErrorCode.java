package com.iohao.little.game.action.skeleton.core.exception;

/**
 * @author 洛朱
 * @date 2022-01-16
 */
public interface BarErrorCode {
    /** 系统其它错误 */
    int systemOtherErrCode = -1000;
    /** 参数验错误码 */
    int validateErrCode = -1001;
    /**
     * 路由错误码
     * <pre>
     *     一般是客户端请求了不存在的路由引起的
     * </pre>
     */
    int cmdInfoErrorCode = -1002;

}
