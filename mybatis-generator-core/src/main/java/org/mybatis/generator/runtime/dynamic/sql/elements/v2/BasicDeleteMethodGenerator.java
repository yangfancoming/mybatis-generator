
package org.mybatis.generator.runtime.dynamic.sql.elements.v2;

import java.util.HashSet;
import java.util.Set;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.runtime.dynamic.sql.elements.AbstractMethodGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.MethodAndImports;

public class BasicDeleteMethodGenerator extends AbstractMethodGenerator {

    private BasicDeleteMethodGenerator(Builder builder) {
        super(builder);
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        Set<FullyQualifiedJavaType> imports = new HashSet<>();
        
        FullyQualifiedJavaType parameterType =
                new FullyQualifiedJavaType(
                        "org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider"); //$NON-NLS-1$
        FullyQualifiedJavaType adapter =
                new FullyQualifiedJavaType("org.mybatis.dynamic.sql.util.SqlProviderAdapter"); //$NON-NLS-1$
        FullyQualifiedJavaType annotation =
                new FullyQualifiedJavaType("org.apache.ibatis.annotations.DeleteProvider"); //$NON-NLS-1$

        imports.add(parameterType);
        imports.add(adapter);
        imports.add(annotation);
        
        Method method = new Method("delete"); //$NON-NLS-1$
        method.setAbstract(true);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(parameterType, "deleteStatement")); //$NON-NLS-1$
        context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable, imports);
        method.addAnnotation("@DeleteProvider(type=SqlProviderAdapter.class, method=\"delete\")"); //$NON-NLS-1$

        return MethodAndImports.withMethod(method)
                .withImports(imports)
                .build();
    }

    @Override
    public boolean callPlugins(Method method, Interface interfaze) {
        return context.getPlugins().clientBasicDeleteMethodGenerated(method, interfaze, introspectedTable);
    }

    public static class Builder extends BaseBuilder<Builder, BasicDeleteMethodGenerator> {

        @Override
        public Builder getThis() {
            return this;
        }

        @Override
        public BasicDeleteMethodGenerator build() {
            return new BasicDeleteMethodGenerator(this);
        }
    }
}
