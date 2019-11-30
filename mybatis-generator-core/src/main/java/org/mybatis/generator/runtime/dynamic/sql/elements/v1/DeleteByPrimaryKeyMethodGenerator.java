
package org.mybatis.generator.runtime.dynamic.sql.elements.v1;

import java.util.HashSet;
import java.util.Set;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.runtime.dynamic.sql.elements.AbstractMethodGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.FragmentGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.MethodAndImports;
import org.mybatis.generator.runtime.dynamic.sql.elements.MethodParts;

public class DeleteByPrimaryKeyMethodGenerator extends AbstractMethodGenerator {
    
    private FragmentGenerator fragmentGenerator;
    
    private DeleteByPrimaryKeyMethodGenerator(Builder builder) {
        super(builder);
        fragmentGenerator = builder.fragmentGenerator;
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        if (!introspectedTable.getRules().generateDeleteByPrimaryKey()) {
            return null;
        }

        Set<FullyQualifiedJavaType> imports = new HashSet<>();
        Set<String> staticImports = new HashSet<>();
        
        imports.add(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.delete.DeleteDSL")); //$NON-NLS-1$
        staticImports.add("org.mybatis.dynamic.sql.SqlBuilder.*"); //$NON-NLS-1$
        
        Method method = new Method("deleteByPrimaryKey"); //$NON-NLS-1$
        method.setDefault(true);
        context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable, imports);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        
        method.addBodyLine("return DeleteDSL.deleteFromWithMapper(this::delete, " //$NON-NLS-1$
                + tableFieldName + ")"); //$NON-NLS-1$
        
        MethodParts methodParts = fragmentGenerator.getPrimaryKeyWhereClauseAndParameters();
        for (Parameter parameter : methodParts.getParameters()) {
            method.addParameter(parameter);
        }
        method.addBodyLines(methodParts.getBodyLines());
        imports.addAll(methodParts.getImports());
        
        method.addBodyLine("        .build()"); //$NON-NLS-1$
        method.addBodyLine("        .execute();"); //$NON-NLS-1$
        
        return MethodAndImports.withMethod(method)
                .withImports(imports)
                .withStaticImports(staticImports)
                .build();
    }

    @Override
    public boolean callPlugins(Method method, Interface interfaze) {
        return context.getPlugins().clientDeleteByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable);
    }

    public static class Builder extends BaseBuilder<Builder, DeleteByPrimaryKeyMethodGenerator> {
        
        private FragmentGenerator fragmentGenerator;
        
        public Builder withFragmentGenerator(FragmentGenerator fragmentGenerator) {
            this.fragmentGenerator = fragmentGenerator;
            return this;
        }

        @Override
        public Builder getThis() {
            return this;
        }

        @Override
        public DeleteByPrimaryKeyMethodGenerator build() {
            return new DeleteByPrimaryKeyMethodGenerator(this);
        }
    }
}
