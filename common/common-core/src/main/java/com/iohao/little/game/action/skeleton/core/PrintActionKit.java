package com.iohao.little.game.action.skeleton.core;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.iohao.little.game.action.skeleton.core.flow.ActionMethodInOut;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import lombok.experimental.UtilityClass;
import org.fusesource.jansi.Ansi;

import java.util.*;

/**
 * 打印 action
 * <BR>
 *
 * @author 洛朱
 * @Date 2021-12-12
 */
@UtilityClass
public class PrintActionKit {
    /**
     * 打印 inout
     *
     * @param inOuts inOuts
     */
    public void printInout(List<ActionMethodInOut> inOuts) {
        String title = "@|CYAN ======================== InOut ========================= |@";
        System.out.println(Ansi.ansi().eraseScreen().render(title));
        System.out.println("如果需要关闭日志, 查看 BehaviorConfig#setPrintInout");
        for (ActionMethodInOut inOut : inOuts) {
            String info = String.format("@|BLUE %s |@", inOut.getClass());
            System.out.println(Ansi.ansi().eraseScreen().render(info));
        }
    }

    public void printHandler(List<Handler<RequestMessage>> handlers) {
        String iohaoTitle = "@|CYAN ======================== 业务框架 iohao ========================= |@";
        System.out.println(Ansi.ansi().eraseScreen().render(iohaoTitle));
        String colorStr = "@|BLACK BLACK|@ @|RED RED|@ @|GREEN GREEN|@ @|YELLOW YELLOW|@ @|BLUE BLUE|@ @|MAGENTA MAGENTA|@ @|CYAN CYAN|@ @|WHITE WHITE|@ @|DEFAULT DEFAULT|@";
        System.out.println(Ansi.ansi().eraseScreen().render(colorStr));

        String title = "@|CYAN ======================== Handler ========================= |@";
        System.out.println(Ansi.ansi().eraseScreen().render(title));
        System.out.println("如果需要关闭日志, 查看 BehaviorConfig#setPrintHandler");
        for (Handler<RequestMessage> handler : handlers) {
            String info = String.format("@|BLUE %s |@", handler.getClass()
            );
            System.out.println(Ansi.ansi().eraseScreen().render(info));
        }
    }

    /**
     * 打印 action
     *
     * @param behaviors behaviors
     */
    void printActionCommand(ActionCommand[][] behaviors) {
        printActionCommand(behaviors, false);
    }

    void printActionCommand(ActionCommand[][] behaviors, boolean shortName) {
        String title = "@|CYAN ======================== action ========================= |@";
        System.out.println(Ansi.ansi().eraseScreen().render(title));
        String tip = """
                如果需要关闭日志, 查看 BarSkeletonSetting#setPrintAction;
                如需要打印（class method params return）完整的包名, 查看 BarSkeletonSetting#printActionShort;
                """;
        System.out.print(tip);

        for (int cmd = 0; cmd < behaviors.length; cmd++) {
            ActionCommand[] subBehavior = behaviors[cmd];

            if (Objects.isNull(subBehavior)) {
                continue;
            }

            for (int subCmd = 0; subCmd < subBehavior.length; subCmd++) {
                ActionCommand behavior = subBehavior[subCmd];

                if (Objects.isNull(behavior)) {
                    continue;
                }

                ActionCommand.ParamInfo[] paramInfos = behavior.getParamInfos();
                String paramInfo = "";
                String paramInfoShort = "";

                if (ArrayUtil.isNotEmpty(paramInfos)) {
                    var infos = Arrays.stream(paramInfos)
                            .map(ActionCommand.ParamInfo::toStringShort)
                            .toArray();
                    paramInfoShort = ArrayUtil.join(infos, ", ");

                    paramInfo = ArrayUtil.join(paramInfos, ", ");
                }

                Map<String, Object> params = new HashMap<>();
                params.put("cmd", cmd);
                params.put("subCmd", subCmd);
                params.put("actionName", behavior.getActionControllerClazz().getName());
                params.put("methodName", behavior.getActionMethodName());
                params.put("paramInfo", paramInfo);

                params.put("paramInfoShort", paramInfoShort);
                params.put("actionNameShort", behavior.getActionControllerClazz().getSimpleName());

                shortName(params, shortName);
                String template = "@|red 路由: {cmd} - {subCmd}|@ @|WHITE --- tcp action 类.方法(参数):|@ @|BLUE {actionName}|@.@|green {methodName}|@(@|RED {paramInfo}|@)";
                String info = StrUtil.format(template, params);

                // 返回类型
                String returnInfoTemplate = " --- @|DEFAULT return|@ @|MAGENTA {returnTypeClazz}|@";
                ActionCommand.ActionMethodReturnInfo actionMethodReturnInfo = behavior.getActionMethodReturnInfo();
                final Class<?> returnTypeClazz = actionMethodReturnInfo.getReturnTypeClazz();
                params.put("returnTypeClazz", returnTypeClazz.getName());
                params.put("returnTypeClazzShort", returnTypeClazz.getSimpleName());


                if (List.class.isAssignableFrom(returnTypeClazz)) {
                    returnInfoTemplate = returnInfoTemplate + "<@|RED {actualTypeArgumentClazz}|@>";
                    params.put("actualTypeArgumentClazz", actionMethodReturnInfo.getActualTypeArgumentClazz());
                    params.put("actualTypeArgumentClazzShort", actionMethodReturnInfo.getActualTypeArgumentClazz().getSimpleName());
                }

                shortName(params, shortName);
                String returnInfo = StrUtil.format(returnInfoTemplate, params);

                String jumpTemplate = " ~~~ see.({actionSimpleName}.java:1).{methodName}";
                params.put("actionSimpleName", behavior.getActionControllerClazz().getSimpleName());
                String jumpInfo = StrUtil.format(jumpTemplate, params);

                String text = info + returnInfo + jumpInfo;
                System.out.println(Ansi.ansi().eraseScreen().render(text));
            }
        }
        System.out.println();
    }

    private void shortName(Map<String, Object> params, boolean shortName) {
        if (!shortName) {
            return;
        }

        params.put("paramInfo", params.get("paramInfoShort"));
        params.put("actionName", params.get("actionNameShort"));
        params.put("returnTypeClazz", params.get("returnTypeClazzShort"));
        params.put("actualTypeArgumentClazz", params.get("actualTypeArgumentClazzShort"));

    }
}
