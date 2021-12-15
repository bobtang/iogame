package com.iohao.little.game.common.config.internal;

import com.iohao.core.config.scan.ScanClass;

import java.util.Set;

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
        ScanClass bootItemConfigScan = ScanClass.createScanClass(BootItemConfig.class::isAssignableFrom);
        bootItemConfigScan.add(packClazz);
        Set<Class<?>> set = bootItemConfigScan.listScan();
        for (Class<?> clazz : set) {
            try {
                BootItemConfig itemConfig = (BootItemConfig) clazz.getDeclaredConstructor().newInstance();
                itemConfig.config();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
