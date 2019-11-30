
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

public class UpdateByPrimaryKeyMethodGenerator extends AbstractMethodGenerator {
    private FullyQualifiedJavaType recordType;
    private FragmentGenerator fragmentGenerator;
    
    private UpdateByPrimaryKeyMethodGenerator(Builder builder) {
        super(builder);
        recordType = builder.recordType;
        fragmentGenerator = builder.fragmentGenerator;
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        if (!introspectedTable.getRules().generateUpdateByPrimaryKeyWithBLOBs()
                && !introspectedTable.getRules().generateUpdateByPrimaryKeyWithoutBLOBs()) {
            return null;
        }

        Set<FullyQualifiedJavaType> imports = new HashSet<>();

        imports.add(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.update.UpdateDSL")); //$NON-NLS-1$
        imports.add(recordType);
        
        Method method = new Method("updateByPrimaryKey"); //$NON-NLS-1$
        method.setDefault(true);
        context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable, imports);
        
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(recordType, "record")); //$NON-NLS-1$

        method.addBodyLine("return UpdateDSL.updateWithMapper(this::update, " //$NON-NLS-1$
                + tableFieldName + ")"); //$NON-NLS-1$

        method.addBodyLines(fragmentGenerator.getSetEqualLines(introspectedTable.getNonPrimaryKeyColumns(), false));
        method.addBodyLines(fragmentGenerator.getPrimaryKeyWhereClauseForUpdate("        ")); //$NON-NLS-1$
        method.addBodyLine("        .build()"); //$NON-NLS-1$
        method.addBodyLine("        .execute();"); //$NON-NLS-1$

        return MethodAndImports.withMethod(method)
                .withImports(imports)
                .build();
    }

    @Override
    public boolean callPlugins(Method method, Interface interfaze) {
        return context.getPlugins().clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(method,
                interfaze, introspectedTable);
    }

    public static class Builder extends BaseBuilder<Builder, UpdateByPrimaryKeyMethodGenerator> {
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
        public UpdateByPrimaryKeyMethodGenerator build() {
            return new UpdateByPrimaryKeyMethodGenerator(this);
        }
    }
}
