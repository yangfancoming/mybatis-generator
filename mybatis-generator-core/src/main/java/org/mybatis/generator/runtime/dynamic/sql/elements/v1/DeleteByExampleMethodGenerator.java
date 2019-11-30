
package org.mybatis.generator.runtime.dynamic.sql.elements.v1;

import java.util.HashSet;
import java.util.Set;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.runtime.dynamic.sql.elements.AbstractMethodGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.MethodAndImports;

public class DeleteByExampleMethodGenerator extends AbstractMethodGenerator {
    
    private DeleteByExampleMethodGenerator(Builder builder) {
        super(builder);
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        if (!introspectedTable.getRules().generateDeleteByExample()) {
            return null;
        }
        
        Set<FullyQualifiedJavaType> imports = new HashSet<>();

        imports.add(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.delete.DeleteDSL")); //$NON-NLS-1$
        imports.add(new FullyQualifiedJavaType(
                "org.mybatis.dynamic.sql.delete.MyBatis3DeleteModelAdapter")); //$NON-NLS-1$
        
        Method method = new Method("deleteByExample"); //$NON-NLS-1$
        method.setDefault(true);
        context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable, imports);
        
        FullyQualifiedJavaType returnType =
                new FullyQualifiedJavaType("DeleteDSL<MyBatis3DeleteModelAdapter<Integer>>"); //$NON-NLS-1$
        method.setReturnType(returnType);
        method.addBodyLine(
                "return DeleteDSL.deleteFromWithMapper(this::delete, " //$NON-NLS-1$
                        + tableFieldName + ");"); //$NON-NLS-1$
        
        return MethodAndImports.withMethod(method)
                .withImports(imports)
                .build();
    }

    @Override
    public boolean callPlugins(Method method, Interface interfaze) {
        return context.getPlugins().clientDeleteByExampleMethodGenerated(method, interfaze, introspectedTable);
    }

    public static class Builder extends BaseBuilder<Builder, DeleteByExampleMethodGenerator> {

        @Override
        public Builder getThis() {
            return this;
        }

        @Override
        public DeleteByExampleMethodGenerator build() {
            return new DeleteByExampleMethodGenerator(this);
        }
    }
}
