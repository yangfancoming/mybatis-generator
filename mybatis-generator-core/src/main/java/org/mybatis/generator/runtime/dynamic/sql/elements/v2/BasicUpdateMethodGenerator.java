
package org.mybatis.generator.runtime.dynamic.sql.elements.v2;

import java.util.HashSet;
import java.util.Set;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.runtime.dynamic.sql.elements.AbstractMethodGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.MethodAndImports;

public class BasicUpdateMethodGenerator extends AbstractMethodGenerator {
    
    private BasicUpdateMethodGenerator(Builder builder) {
        super(builder);
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        Set<FullyQualifiedJavaType> imports = new HashSet<>();
        
        FullyQualifiedJavaType parameterType =
                new FullyQualifiedJavaType(
                        "org.mybatis.dynamic.sql.update.render.UpdateStatementProvider"); //$NON-NLS-1$
        FullyQualifiedJavaType adapter =
                new FullyQualifiedJavaType("org.mybatis.dynamic.sql.util.SqlProviderAdapter"); //$NON-NLS-1$
        FullyQualifiedJavaType annotation =
                new FullyQualifiedJavaType("org.apache.ibatis.annotations.UpdateProvider"); //$NON-NLS-1$
        
        imports.add(parameterType);
        imports.add(adapter);
        imports.add(annotation);
        
        Method method = new Method("update"); //$NON-NLS-1$
        method.setAbstract(true);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(parameterType, "updateStatement")); //$NON-NLS-1$
        context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable, imports);
        method.addAnnotation("@UpdateProvider(type=SqlProviderAdapter.class, method=\"update\")"); //$NON-NLS-1$

        return MethodAndImports.withMethod(method)
                .withImports(imports)
                .build();
    }

    @Override
    public boolean callPlugins(Method method, Interface interfaze) {
        return context.getPlugins().clientBasicUpdateMethodGenerated(method, interfaze, introspectedTable);
    }

    public static class Builder extends BaseBuilder<Builder, BasicUpdateMethodGenerator> {

        @Override
        public Builder getThis() {
            return this;
        }

        @Override
        public BasicUpdateMethodGenerator build() {
            return new BasicUpdateMethodGenerator(this);
        }
    }
}
