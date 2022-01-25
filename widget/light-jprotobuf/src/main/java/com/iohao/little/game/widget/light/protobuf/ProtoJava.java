package com.iohao.little.game.widget.light.protobuf;

import cn.hutool.core.util.StrUtil;
import com.thoughtworks.qdox.model.JavaClass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 洛朱
 * @date 2022-01-24
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Accessors(chain = true)
public class ProtoJava {
    Class<?> clazz;
    String className;

    String comment;

    String fileName;
    String filePackage;

    JavaClass javaClass;

    List<ProtoJavaField> protoJavaFieldList = new ArrayList<>();

    public void addProtoJavaFiled(ProtoJavaField protoJavaField) {
        this.protoJavaFieldList.add(protoJavaField);
    }

    public boolean inThisFile(ProtoJava protoJava) {
        return Objects.equals(this.fileName, protoJava.fileName) && Objects.equals(this.filePackage, protoJava.filePackage);
    }

    public ProtoJavaRegionKey getProtoJavaRegionKey() {
        return new ProtoJavaRegionKey(this.fileName, this.filePackage);

    }

    public String toProtoMessage() {

        String fieldsString = protoJavaFieldList
                .stream()
                .map(ProtoJavaField::toProtoFieldLine)
                .collect(Collectors.joining());

        Map<String, String> messageMap = new HashMap<>();
        messageMap.put("className", this.className);
        messageMap.put("fieldsString", fieldsString);
        messageMap.put("classComment", this.comment);

        String template = """
                // {classComment}
                message {className} {
                {fieldsString}
                }
                                
                """;

        return StrUtil.format(template, messageMap);
    }

}
