package com.iohao.little.game.action.skeleton.core.action;


public interface ActionCont {
    /**
     * bee 模块功能
     */
    interface BeeActionCont {
        /**
         * bee 模块 - 主 cmd
         */
        int cmd = 10;

        int hello = 0;
        int name = 1;
    }

    /**
     * bee 模块功能
     */
    interface BirdActionCont {
        /**
         * bee 模块 - 主 cmd
         */
        int cmd = 11;

        int bird_name = 0;
        int bird_age = 1;
    }

    interface DemoActionCont {
        /** 主 cmd */
        int cmd = 1;

        int hello = 0;

    }

}
