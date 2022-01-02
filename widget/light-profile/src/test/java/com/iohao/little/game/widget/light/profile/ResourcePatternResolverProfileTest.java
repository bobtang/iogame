package com.iohao.little.game.widget.light.profile;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 洛朱
 * @date 2022-01-02
 */
@Slf4j
public class ResourcePatternResolverProfileTest {
    @Test
    public void loadLocal() {
        ResourcePatternResolverProfile config = new ResourcePatternResolverProfile();
        // 加载 local 目录
        config.addDir("local");

        List<URL> urls = config.toUrls();
        log.info("local profile {}", urls.size());


    }
}