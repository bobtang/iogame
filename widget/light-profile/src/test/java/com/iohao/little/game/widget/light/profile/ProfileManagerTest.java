package com.iohao.little.game.widget.light.profile;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author 洛朱
 * @date 2022-01-02
 */
@Slf4j
public class ProfileManagerTest {

    @Test
    public void profile() {
        // 加载环境配置
        String profileConfigName = "local";
        ProfileManager.loadMainProfile(profileConfigName);

        Profile profile = ProfileManager.profile();
        // 掉用配置文件中的配置
        String jdbcDriver = profile.get("jdbcDriver");
        boolean devMode = profile.getBool("devMode");
        int maxVip = profile.getInt("maxVip");

        log.info("profile::: {} {} {}", jdbcDriver, devMode, maxVip);
    }
}