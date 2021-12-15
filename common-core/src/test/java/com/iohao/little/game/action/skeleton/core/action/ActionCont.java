package com.iohao.little.game.action.skeleton.core.action;


public interface ActionCont {
    /**
     * bee 模块功能
     */
    interface BeeActionCont {
        /**
         * bee 模块 - 主 cmd
         */
        int CMD = 1;

        int HELLO = 0;
        int NAME = 1;
    }

    /**
     * bee 模块功能
     */
    interface BirdActionCont {
        /**
         * bee 模块 - 主 cmd
         */
        int CMD = 2;

        int BIRD_NAME = 0;
        int BIRD_AGE = 1;
    }

}
