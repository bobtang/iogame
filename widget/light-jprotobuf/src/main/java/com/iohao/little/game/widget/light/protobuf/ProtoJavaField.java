package com.iohao.little.game.widget.light.protobuf;

import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 洛朱
 * @date 2022-01-24
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
public class ProtoJavaField {
    boolean repeated;
    String fieldName;
    String comment;
    int order;
    Class<?> fieldTypeClass;
    String fieldProtoType;
    Field field;

    ProtoJava protoJavaParent;

    boolean isMap() {
        return Map.class.equals(fieldTypeClass);
    }

    boolean isList() {
        return List.class.equals(fieldTypeClass);
    }

    private Map<String, String> createParam() {
        Map<String, String> messageMap = new HashMap<>();

        messageMap.put("comment", this.comment);
        messageMap.put("repeated", "");
        messageMap.put("fieldProtoType", this.fieldProtoType);
        messageMap.put("fieldName", this.fieldName);
        messageMap.put("order", String.valueOf(this.order));

        if (this.repeated) {
            messageMap.put("repeated", "repeated ");
        }

        return messageMap;
    }

    public String toProtoFieldLine() {
        Map<String, String> messageMap = this.createParam();

        String templateFiled = getTemplateFiled();

        return StrUtil.format(templateFiled, messageMap);
    }

    private String getTemplateFiled() {
        String templateFiled = """
                  // {comment}
                  {repeated}{fieldProtoType} {fieldName} = {order};
                """;

        if (Objects.isNull(this.comment)) {
            templateFiled = """
                      {repeated}{fieldProtoType} {fieldName} = {order};
                    """;
        }

        return templateFiled;
    }
}
