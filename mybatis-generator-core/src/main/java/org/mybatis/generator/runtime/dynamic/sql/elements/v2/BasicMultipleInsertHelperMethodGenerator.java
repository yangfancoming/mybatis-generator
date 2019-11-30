
package org.mybatis.generator.runtime.dynamic.sql.elements.v2;

import java.util.HashSet;
import java.util.Set;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.runtime.dynamic.sql.elements.AbstractMethodGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.MethodAndImports;

public class BasicMultipleInsertHelperMethodGenerator extends AbstractMethodGenerator {
    
    private FullyQualifiedJavaType recordType;
    
    private BasicMultipleInsertHelperMethodGenerator(Builder builder) {
        super(builder);
        recordType = builder.recordType;
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        if (!Utils.generateMultipleRowInsert(introspectedTable)) {
            return null;
        }
        
        Set<FullyQualifiedJavaType> imports = new HashSet<>();
        
        imports.add(new FullyQualifiedJavaType(
                "org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider")); //$NON-NLS-1$
        
        FullyQualifiedJavaType parameterType =
                new FullyQualifiedJavaType(
                        "org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider"); //$NON-NLS-1$
        imports.add(recordType);
        parameterType.addTypeArgument(recordType);
        
        Method method = new Method("insertMultiple"); //$NON-NLS-1$
        method.setDefault(true);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(parameterType, "multipleInsertStatement")); //$NON-NLS-1$
        context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable, imports);
        method.addBodyLine(
                "return insertMultiple(multipleInsertStatement.getInsertStatement()," //$NON-NLS-1$
                + " multipleInsertStatement.getRecords());"); //$NON-NLS-1$

        MethodAndImports.Builder builder = MethodAndImports.withMethod(method)
                .withImports(imports);
      
        return builder.build();
    }
    
    @Override
    public boolean callPlugins(Method method, Interface interfaze) {
        return context.getPlugins().clientBasicInsertMultipleHelperMethodGenerated(method, interfaze,
                introspectedTable);
    }

    public static class Builder extends BaseBuilder<Builder, BasicMultipleInsertHelperMethodGenerator> {

        private FullyQualifiedJavaType recordType;
        
        public Builder withRecordType(FullyQualifiedJavaType recordType) {
            this.recordType = recordType;
            return this;
        }
        
        @Override
        public Builder getThis() {
            return this;
        }

        @Override
        public BasicMultipleInsertHelperMethodGenerator build() {
            return new BasicMultipleInsertHelperMethodGenerator(this);
        }
    }
}
