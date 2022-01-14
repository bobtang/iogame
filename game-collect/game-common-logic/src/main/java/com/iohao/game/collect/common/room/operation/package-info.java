/**
 * 房间内的玩法操作业务
 * <p>
 * 玩法操作业务 - 设计模式: 策略模式 + 享元模式
 * <pre>
 *     策略模式:
 *         定义一个接口，在写两个实现类并实现这个接口，这样就可以使用一个接口，在需要的时候，在根据情况使用哪一个实现类
 *     享元模式:
 *         维护 玩法接口的实现类实例 {@link com.iohao.game.collect.common.room.operation.OperationHandler}
 *
 *         将许多"虚拟"对象的状态集中管理, 减少运行时对象实例个数，节省内存
 * </pre>
 *
 * @author 洛朱
 * @date 2022-01-14
 */
package com.iohao.game.collect.common.room.operation;