
package org.mybatis.generator.runtime.dynamic.sql.elements.v1;

import java.util.HashSet;
import java.util.Set;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.runtime.dynamic.sql.elements.AbstractMethodGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.FragmentGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.MethodAndImports;
import org.mybatis.generator.runtime.dynamic.sql.elements.MethodParts;

public class SelectByPrimaryKeyMethodGenerator extends AbstractMethodGenerator {
    private FullyQualifiedJavaType recordType;
    private FragmentGenerator fragmentGenerator;
    
    private SelectByPrimaryKeyMethodGenerator(Builder builder) {
        super(builder);
        recordType = builder.recordType;
        fragmentGenerator = builder.fragmentGenerator;
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        if (!introspectedTable.getRules().generateSelectByPrimaryKey()) {
            return null;
        }
        
        Set<FullyQualifiedJavaType> imports = new HashSet<>();
        
        imports.add(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.select.SelectDSL")); //$NON-NLS-1$
        imports.add(recordType);
        
        Method method = new Method("selectByPrimaryKey"); //$NON-NLS-1$
        method.setDefault(true);
        context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable, imports);
        method.setReturnType(recordType);
        
        StringBuilder sb = new StringBuilder();
        sb.append("return SelectDSL.selectWithMapper(this::selectOne, "); //$NON-NLS-1$
        sb.append(fragmentGenerator.getSelectList());
        sb.append(')');
        method.addBodyLine(sb.toString());
        
        method.addBodyLine("        .from(" + tableFieldName + ")"); //$NON-NLS-1$ //$NON-NLS-2$
        
        MethodAndImports.Builder builder = MethodAndImports.withMethod(method)
                .withStaticImport("org.mybatis.dynamic.sql.SqlBuilder.*") //$NON-NLS-1$
                .withImports(imports);
        
        MethodParts methodParts = fragmentGenerator.getPrimaryKeyWhereClauseAndParameters();
        acceptParts(builder, method, methodParts);
        
        method.addBodyLine("        .build()"); //$NON-NLS-1$
        method.addBodyLine("        .execute();"); //$NON-NLS-1$
        
        return builder.build();
    }

    @Override
    public boolean callPlugins(Method method, Interface interfaze) {
        return context.getPlugins().clientSelectByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable);
    }

    public static class Builder extends BaseBuilder<Builder, SelectByPrimaryKeyMethodGenerator> {
        private FullyQualifiedJavaType recordType;
        private FragmentGenerator fragmentGenerator;
        
        public Builder withRecordType(FullyQualifiedJavaType recordType) {
            this.recordType = recordType;
            return this;
        }
        
        public Builder withFragmentGenerator(FragmentGenerator fragmentGenerator) {
            this.fragmentGenerator = fragmentGenerator;
            return this;
        }

        @Override
        public Builder getThis() {
            return this;
        }

        @Override
        public SelectByPrimaryKeyMethodGenerator build() {
            return new SelectByPrimaryKeyMethodGenerator(this);
        }
    }
}
