
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

public class UpdateByExampleSelectiveMethodGenerator extends AbstractMethodGenerator {
    private FullyQualifiedJavaType recordType;
    private FragmentGenerator fragmentGenerator;
    
    private UpdateByExampleSelectiveMethodGenerator(Builder builder) {
        super(builder);
        recordType = builder.recordType;
        fragmentGenerator = builder.fragmentGenerator;
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        if (!introspectedTable.getRules().generateUpdateByExampleSelective()) {
            return null;
        }

        Set<FullyQualifiedJavaType> imports = new HashSet<>();

        imports.add(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.update.UpdateDSL")); //$NON-NLS-1$
        imports.add(new FullyQualifiedJavaType(
                "org.mybatis.dynamic.sql.update.MyBatis3UpdateModelAdapter")); //$NON-NLS-1$
        imports.add(recordType);
        
        Method method = new Method("updateByExampleSelective"); //$NON-NLS-1$
        method.setDefault(true);
        context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable, imports);
        
        FullyQualifiedJavaType returnType =
                new FullyQualifiedJavaType("UpdateDSL<MyBatis3UpdateModelAdapter<Integer>>"); //$NON-NLS-1$
        method.setReturnType(returnType);
        method.addParameter(new Parameter(recordType, "record")); //$NON-NLS-1$

        method.addBodyLine("return UpdateDSL.updateWithMapper(this::update, " //$NON-NLS-1$
                + tableFieldName + ")"); //$NON-NLS-1$

        method.addBodyLines(fragmentGenerator.getSetEqualWhenPresentLines(introspectedTable.getAllColumns(), true));

        return MethodAndImports.withMethod(method)
                .withImports(imports)
                .build();
    }

    @Override
    public boolean callPlugins(Method method, Interface interfaze) {
        return context.getPlugins().clientUpdateByExampleSelectiveMethodGenerated(method, interfaze, introspectedTable);
    }

    public static class Builder extends BaseBuilder<Builder, UpdateByExampleSelectiveMethodGenerator> {
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
        public UpdateByExampleSelectiveMethodGenerator build() {
            return new UpdateByExampleSelectiveMethodGenerator(this);
        }
    }
}
