
package org.mybatis.generator.runtime.dynamic.sql.elements.v2;

import java.util.HashSet;
import java.util.Set;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.runtime.dynamic.sql.elements.AbstractMethodGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.FragmentGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.MethodAndImports;

public class UpdateByPrimaryKeySelectiveMethodGeneratorV2 extends AbstractMethodGenerator {
    private FullyQualifiedJavaType recordType;
    private FragmentGenerator fragmentGenerator;
    
    private UpdateByPrimaryKeySelectiveMethodGeneratorV2(Builder builder) {
        super(builder);
        recordType = builder.recordType;
        fragmentGenerator = builder.fragmentGenerator;
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        if (!Utils.generateUpdateByPrimaryKey(introspectedTable)) {
            return null;
        }
        
        Set<FullyQualifiedJavaType> imports = new HashSet<>();

        imports.add(recordType);
        
        Method method = new Method("updateByPrimaryKeySelective"); //$NON-NLS-1$
        method.setDefault(true);
        context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable, imports);
        
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(recordType, "record")); //$NON-NLS-1$

        method.addBodyLine("return update(c ->"); //$NON-NLS-1$

        method.addBodyLines(
                fragmentGenerator.getSetEqualWhenPresentLinesV2(introspectedTable.getNonPrimaryKeyColumns(),
                        "    c", "    ", false)); //$NON-NLS-1$ //$NON-NLS-2$
        method.addBodyLines(fragmentGenerator.getPrimaryKeyWhereClauseForUpdate("    ")); //$NON-NLS-1$

        method.addBodyLine(");"); //$NON-NLS-1$
        return MethodAndImports.withMethod(method)
                .withImports(imports)
                .build();
    }

    @Override
    public boolean callPlugins(Method method, Interface interfaze) {
        return context.getPlugins()
                .clientUpdateByPrimaryKeySelectiveMethodGenerated(method, interfaze, introspectedTable);
    }

    public static class Builder extends BaseBuilder<Builder, UpdateByPrimaryKeySelectiveMethodGeneratorV2> {
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
        public UpdateByPrimaryKeySelectiveMethodGeneratorV2 build() {
            return new UpdateByPrimaryKeySelectiveMethodGeneratorV2(this);
        }
    }
}
