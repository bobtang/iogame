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
        String profileConfigName = "local";
        ProfileManager.loadMainProfile(profileConfigName);

        Profile profile = ProfileManager.profile();

        log.info("profile::: {}", profile);
    }
}