package com.iohao.little.game.widget.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

@Slf4j
public class WidgetOptionsTest {

    @Test
    public void option() {
        WidgetOptions widgetOptions = new WidgetOptions();

        WidgetOption<String> name = WidgetOption.valueOf("jack", "jack A");
        WidgetOption<String> nameLili = WidgetOption.valueOf("nameLili", "nameLili A");

        widgetOptions.option(name, "jack a is a");
        widgetOptions.option(nameLili, "nameLili a is a");

        log.info("{}", widgetOptions.getOptions());


    }
}