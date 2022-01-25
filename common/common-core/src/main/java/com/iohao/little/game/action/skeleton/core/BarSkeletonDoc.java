package com.iohao.little.game.action.skeleton.core;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import com.iohao.little.game.action.skeleton.core.flow.FlowContext;

import java.util.*;
import java.util.function.Consumer;

/**
 * 游戏文档生成
 *
 * @author 洛朱
 * @date 2022-01-23
 */
public class BarSkeletonDoc {

    List<BarSkeleton> skeletonList = new LinkedList<>();

    public void addSkeleton(BarSkeleton barSkeleton) {
        skeletonList.add(barSkeleton);
    }

    public void buildDoc() {
        this.buildDoc(null);
    }

    public void buildDoc(String docPath) {
        if (Objects.isNull(docPath)) {
            docPath = SystemUtil.getUserInfo().getCurrentDir() + "doc_game.txt";
        }


        if (FileUtil.isDirectory(docPath)) {
            throw new RuntimeException("file is Directory ");

        }

        StringBuilder stringBuilder = new StringBuilder(8000);

        Consumer<ActionCommand[][]> consumer = behaviors -> {
            Map<String, Object> paramMap = new HashMap<>();

            for (int cmd = 0; cmd < behaviors.length; cmd++) {
                ActionCommand[] subBehaviors = behaviors[cmd];

                if (Objects.isNull(subBehaviors)) {
                    continue;
                }

                boolean first = true;

                for (int subCmd = 0; subCmd < subBehaviors.length; subCmd++) {
                    ActionCommand subBehavior = subBehaviors[subCmd];

                    if (Objects.isNull(subBehavior)) {
                        continue;
                    }

                    paramMap.clear();
                    ActionCommandDoc actionCommandDoc = subBehavior.getActionCommandDoc();

                    if (first) {
                        first = false;

                        String template = """
                                ==================== {actionSimpleName} {classComment} ====================
                                """;
                        paramMap.put("actionSimpleName", subBehavior.getActionControllerClazz().getSimpleName());
                        paramMap.put("classComment", actionCommandDoc.getClassComment());
                        String message = StrUtil.format(template, paramMap);
                        stringBuilder.append(message);

                    }

                    paramMap.put("cmd", cmd);
                    paramMap.put("subCmd", subCmd);
                    paramMap.put("actionSimpleName", subBehavior.getActionControllerClazz().getSimpleName());
                    paramMap.put("methodName", subBehavior.getActionMethodName());
                    paramMap.put("methodComment", actionCommandDoc.getComment());
                    paramMap.put("methodParam", "");
                    paramMap.put("returnTypeClazz", subBehavior.getActionMethodReturnInfo().getReturnTypeClazz().getName());
                    paramMap.put("lineNumber", actionCommandDoc.getLineNumber());

                    for (ActionCommand.ParamInfo paramInfo : subBehavior.paramInfos) {

                        Class<?> paramClazz = paramInfo.getParamClazz();

                        if (FlowContext.class.equals(paramClazz) || "userId".equals(paramInfo.getName())) {
                            continue;
                        }

                        paramMap.put("methodParam", paramClazz.getName());
                    }


                    String template = """
                            路由: {cmd} - {subCmd}  --- 【{methodComment}】 --- 【{actionSimpleName}:{lineNumber}】
                                {actionSimpleName}.{methodName}
                                方法参数: {methodParam}
                                方法返回值: {returnTypeClazz}
                                
                            """;


                    String message = StrUtil.format(template, paramMap);
                    stringBuilder.append(message);
                }

            }
        };

        // 生成文档
        skeletonList.stream()
                .map(barSkeleton -> barSkeleton.actionCommandManager)
                .map(actionCommandManager -> actionCommandManager.actionCommands)
                .forEach(consumer);

        FileUtil.writeUtf8String(stringBuilder.toString(), docPath);

    }

    public static BarSkeletonDoc me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final BarSkeletonDoc ME = new BarSkeletonDoc();
    }
}
