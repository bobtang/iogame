package com.iohao.little.game.action.skeleton.core.doc;

import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 洛朱
 * @date 2022-01-28
 */
public class JavaClassDocInfo {
    final JavaClass javaClass;
    Map<String, JavaMethod> javaMethodMap = new HashMap<>();

    public JavaClassDocInfo(JavaClass javaClass) {
        this.javaClass = javaClass;

        List<JavaMethod> methods = javaClass.getMethods();
        for (JavaMethod method : methods) {
            javaMethodMap.put(method.toString(), method);
        }
    }

    public ActionCommandDoc createActionCommandDoc(Method method) {
        JavaMethod javaMethod = javaMethodMap.get(method.toString());

        ActionCommandDoc actionCommandDoc = new ActionCommandDoc();

        actionCommandDoc.setClassComment(this.javaClass.getComment());
        actionCommandDoc.setClassLineNumber(this.javaClass.getLineNumber());
        actionCommandDoc.setComment(javaMethod.getComment());
        actionCommandDoc.setLineNumber(javaMethod.getLineNumber());

        if (actionCommandDoc.getClassComment() == null) {
            actionCommandDoc.setClassComment("");
        }

        if (actionCommandDoc.getComment() == null) {
            actionCommandDoc.setComment("");
        }

        return actionCommandDoc;
    }
}
