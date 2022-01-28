package com.iohao.little.game.action.skeleton.core.doc;

import cn.hutool.core.io.FileUtil;
import cn.hutool.system.SystemUtil;
import com.iohao.little.game.action.skeleton.core.ActionCommand;
import com.iohao.little.game.action.skeleton.core.ActionCommandManager;
import com.iohao.little.game.action.skeleton.core.BarSkeleton;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 游戏文档生成
 *
 * @author 洛朱
 * @date 2022-01-23
 */
public class BarSkeletonDoc {

    List<BarSkeleton> skeletonList = new LinkedList<>();
    String docFileName = "doc_game.txt";

    public void addSkeleton(BarSkeleton barSkeleton) {
        skeletonList.add(barSkeleton);
    }

    public void buildDoc() {
        this.buildDoc(null);
    }

    public void buildDoc(String docPath) {

        if (Objects.isNull(docPath)) {
            docPath = SystemUtil.getUserInfo().getCurrentDir() + docFileName;
        }

        if (FileUtil.isDirectory(docPath)) {
            throw new RuntimeException("file is Directory ");

        }

        StringBuilder stringBuilder = new StringBuilder(8000);


        Consumer<ActionCommand[][]> consumer = behaviors -> {
            for (ActionCommand[] subBehaviors : behaviors) {
                if (Objects.isNull(subBehaviors)) {
                    continue;
                }

                DocInfo docInfo = new DocInfo();

                for (ActionCommand subBehavior : subBehaviors) {
                    if (Objects.isNull(subBehavior)) {
                        continue;
                    }

                    docInfo.setHead(subBehavior);
                    docInfo.add(subBehavior);
                }

                String render = docInfo.render();

                stringBuilder.append(render);
            }
        };

        // 生成文档
        skeletonList.stream()
                .map(BarSkeleton::getActionCommandManager)
                .map(ActionCommandManager::getActionCommands)
                .forEach(consumer);

        FileUtil.writeUtf8String(stringBuilder.toString(), docPath);
    }

//    public void buildDoc(String docPath) {
//
//        if (Objects.isNull(docPath)) {
//            docPath = SystemUtil.getUserInfo().getCurrentDir() + docFileName;
//        }
//
//        if (FileUtil.isDirectory(docPath)) {
//            throw new RuntimeException("file is Directory ");
//
//        }
//
//        StringBuilder stringBuilder = new StringBuilder(8000);
//
//        Consumer<ActionCommand[][]> consumer = behaviors -> {
//            Map<String, Object> paramMap = new HashMap<>();
//
//            for (int cmd = 0; cmd < behaviors.length; cmd++) {
//                ActionCommand[] subBehaviors = behaviors[cmd];
//
//                if (Objects.isNull(subBehaviors)) {
//                    continue;
//                }
//
//                boolean first = true;
//
//                for (int subCmd = 0; subCmd < subBehaviors.length; subCmd++) {
//                    ActionCommand subBehavior = subBehaviors[subCmd];
//
//                    if (Objects.isNull(subBehavior)) {
//                        continue;
//                    }
//
//                    paramMap.clear();
//                    ActionCommandDoc actionCommandDoc = subBehavior.getActionCommandDoc();
//
//                    if (first) {
//                        first = false;
//
//                        String template = """
//                                ==================== {actionSimpleName} {classComment} ====================
//                                """;
//                        paramMap.put("actionSimpleName", subBehavior.getActionControllerClazz().getSimpleName());
//                        paramMap.put("classComment", actionCommandDoc.getClassComment());
//                        String message = StrUtil.format(template, paramMap);
//                        stringBuilder.append(message);
//
//                    }
//
//                    var actionMethodReturnInfo = subBehavior.getActionMethodReturnInfo();
//
//                    paramMap.put("cmd", cmd);
//                    paramMap.put("subCmd", subCmd);
//                    paramMap.put("actionSimpleName", subBehavior.getActionControllerClazz().getSimpleName());
//                    paramMap.put("methodName", subBehavior.getActionMethodName());
//                    paramMap.put("methodComment", actionCommandDoc.getComment());
//                    paramMap.put("methodParam", "");
//                    paramMap.put("returnTypeClazz", actionMethodReturnInfo.isVoid() ? "" : actionMethodReturnInfo.getReturnTypeClazz().getName());
//                    paramMap.put("lineNumber", actionCommandDoc.getLineNumber());
//
//                    paramMap.put("docActionBroadcast", "");
//                    DocActionBroadcastInfo broadcastInfo = new DocActionBroadcastInfo(subBehavior);
//
//                    // 方法参数
//                    for (ActionCommand.ParamInfo paramInfo : subBehavior.getParamInfos()) {
//
//                        Class<?> paramClazz = paramInfo.getParamClazz();
//
//                        if (FlowContext.class.equals(paramClazz) || "userId".equals(paramInfo.getName())) {
//                            continue;
//                        }
//
//                        paramMap.put("methodParam", paramClazz.getName());
//                        broadcastInfo.setMethodParam(paramClazz.getName());
//                    }
//
//                    paramMap.put("docActionBroadcast", broadcastInfo.methodParam);
//
//                    String template = """
//                            路由: {cmd} - {subCmd}  --- 【{methodComment}】 --- 【{actionSimpleName}:{lineNumber}】【{methodName}】
//                                方法参数: {methodParam}
//                                方法返回值: {returnTypeClazz}
//                                广播: {docActionBroadcast}
//
//                            """;
//
//                    String message = StrUtil.format(template, paramMap);
//                    stringBuilder.append(message);
//                }
//
//            }
//        };
//
//        // 生成文档
//        skeletonList.stream()
//                .map(BarSkeleton::getActionCommandManager)
//                .map(ActionCommandManager::getActionCommands)
//                .forEach(consumer);
//
//        FileUtil.writeUtf8String(stringBuilder.toString(), docPath);
//
//    }


    public static BarSkeletonDoc me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final BarSkeletonDoc ME = new BarSkeletonDoc();
    }
}
