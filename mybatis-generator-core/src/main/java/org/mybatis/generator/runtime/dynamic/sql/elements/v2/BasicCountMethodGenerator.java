
package org.mybatis.generator.runtime.dynamic.sql.elements.v2;

import java.util.HashSet;
import java.util.Set;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.runtime.dynamic.sql.elements.AbstractMethodGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.MethodAndImports;

public class BasicCountMethodGenerator extends AbstractMethodGenerator {
    
    private BasicCountMethodGenerator(Builder builder) {
        super(builder);
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        Set<FullyQualifiedJavaType> imports = new HashSet<>();
        
        FullyQualifiedJavaType parameterType =
                new FullyQualifiedJavaType(
                        "org.mybatis.dynamic.sql.select.render.SelectStatementProvider"); //$NON-NLS-1$
        FullyQualifiedJavaType adapter =
                new FullyQualifiedJavaType("org.mybatis.dynamic.sql.util.SqlProviderAdapter"); //$NON-NLS-1$
        FullyQualifiedJavaType annotation =
                new FullyQualifiedJavaType("org.apache.ibatis.annotations.SelectProvider"); //$NON-NLS-1$
        
        imports.add(parameterType);
        imports.add(adapter);
        imports.add(annotation);
        
        Method method = new Method("count"); //$NON-NLS-1$
        method.setAbstract(true);
        method.setReturnType(new FullyQualifiedJavaType("long")); //$NON-NLS-1$
        method.addParameter(new Parameter(parameterType, "selectStatement")); //$NON-NLS-1$
        context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable, imports);
        method.addAnnotation("@SelectProvider(type=SqlProviderAdapter.class, method=\"select\")"); //$NON-NLS-1$

        return MethodAndImports.withMethod(method)
                .withImports(imports)
                .build();
    }
    
    @Override
    public boolean callPlugins(Method method, Interface interfaze) {
        return context.getPlugins().clientBasicCountMethodGenerated(method, interfaze, introspectedTable);
    }
    
    public static class Builder extends BaseBuilder<Builder, BasicCountMethodGenerator> {
        @Override
        public Builder getThis() {
            return this;
        }
        
        @Override
        public BasicCountMethodGenerator build() {
            return new BasicCountMethodGenerator(this);
        }
    }
}
