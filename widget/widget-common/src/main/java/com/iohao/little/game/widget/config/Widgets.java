package com.iohao.little.game.widget.config;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Widgets {
    List<Widget> widgetList = new LinkedList<>();

    public void add(Widget widget) {
        if (Objects.isNull(widget)) {
            return;
        }
        widgetList.add(widget);
    }

    public List<Widget> listWidget() {
        return widgetList;
    }

}
