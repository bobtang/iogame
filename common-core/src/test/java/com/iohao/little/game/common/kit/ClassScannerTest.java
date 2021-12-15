package com.iohao.little.game.common.kit;

import cn.hutool.core.lang.Filter;
import cn.hutool.core.util.ClassUtil;
import com.iohao.little.game.action.skeleton.annotation.ActionController;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

@Slf4j
public class ClassScannerTest {

    @Test
    public void scan() {
        Predicate<Class<?>> predicateFilter = (clazz) -> {
            ActionController annotation = clazz.getAnnotation(ActionController.class);
            return annotation != null;
        };

        String packagePath = "com.iohao.little.game";
        packagePath = "com.iohao.little.game.action.skeleton.core.action";
        ClassScanner scanner = new ClassScanner(packagePath, predicateFilter);

        List<Class<?>> classList = scanner.listScan();

        for (Class<?> clazz : classList) {
            log.info("clazz: {}", clazz);
        }


    }
}