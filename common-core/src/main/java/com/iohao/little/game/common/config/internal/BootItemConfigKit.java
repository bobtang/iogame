package com.iohao.little.game.common.config.internal;


import com.iohao.little.game.common.kit.ClassScanner;

import java.util.List;
import java.util.function.Predicate;

/**
 * BootItem 配置项工具
 *
 * @author 洛朱
 * @Date 2021-12-20
 */
public class BootItemConfigKit {
    /**
     * 加载配置
     * <pre>
     *     这里参数使用类,而没有使用 str 的包路径是因为:
     *     如果 str 所在的配置项变更了路径, 会导致这里加载不到(除非手动改这个 str, 毕竟有时会忘记改参数)
     *     所以这里使用类来当做参数, 毕竟类是一定存在的, str 的包缺不一定存在, 相当于做一个代码级别的验证.
     * </pre>
     *
     * @param packClazz 任意 class 都可以, 会扫描该 class 包下面的所有 class, 并加载
     */
    public static void loadBootItemConfig(Class<? extends BootItemConfig> packClazz) {
        // 过滤条件
        Predicate<Class<?>> predicate = BootItemConfig.class::isAssignableFrom;
        // 扫描路径
        String packagePath = packClazz.getPackageName();
        ClassScanner classScanner = new ClassScanner(packagePath, predicate);

        List<Class<?>> classList = classScanner.listScan();
        for (Class<?> clazz : classList) {
            try {
                BootItemConfig itemConfig = (BootItemConfig) clazz.getDeclaredConstructor().newInstance();
                itemConfig.config();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
