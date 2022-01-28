package com.iohao.little.game.common.kit;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.IOException;

/**
 * 模板工具
 *
 * @author 洛朱
 * @date 2022-01-29
 */
public class Templates {
    /** 字符串模板 */
    final GroupTemplate strGt;
    final GroupTemplate gt;


    public Templates() {
        GroupTemplate strGt = null;
        GroupTemplate gt = null;
        try {
            // 初始化字符串模板
            StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
            Configuration cfg = Configuration.defaultConfiguration();
            strGt = new GroupTemplate(resourceLoader, cfg);


            // 初始化 文件模板
            String root = "templates";
            ClasspathResourceLoader classpathResourceLoader = new ClasspathResourceLoader(root);
            gt = new GroupTemplate(classpathResourceLoader, cfg);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.strGt = strGt;
        this.gt = gt;
    }

    public Template getTemplate(String templateContent) {
        return strGt.getTemplate(templateContent);
    }

    public static Templates me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final Templates ME = new Templates();
    }
}
