
package org.mybatis.generator.runtime.dynamic.sql.elements.v2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.runtime.dynamic.sql.elements.AbstractMethodGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.MethodAndImports;

public class InsertSelectiveMethodGeneratorV2 extends AbstractMethodGenerator {
    private FullyQualifiedJavaType recordType;
    
    private InsertSelectiveMethodGeneratorV2(Builder builder) {
        super(builder);
        recordType = builder.recordType;
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        Set<FullyQualifiedJavaType> imports = new HashSet<>();
        
        imports.add(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils")); //$NON-NLS-1$
        imports.add(recordType);
        
        Method method = new Method("insertSelective"); //$NON-NLS-1$
        method.setDefault(true);
        context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable, imports);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(recordType, "record")); //$NON-NLS-1$
        
        method.addBodyLine("return MyBatis3Utils.insert(this::insert, record, " + tableFieldName + //$NON-NLS-1$
                ", c ->"); //$NON-NLS-1$
        
        List<IntrospectedColumn> columns = ListUtilities.removeIdentityAndGeneratedAlwaysColumns(
                introspectedTable.getAllColumns());
        boolean first = true;
        for (IntrospectedColumn column : columns) {
            String fieldName = calculateFieldName(column);
            if (column.isSequenceColumn()) {
                if (first) {
                    method.addBodyLine("    c.map(" + fieldName //$NON-NLS-1$
                            + ").toProperty(\"" + column.getJavaProperty() //$NON-NLS-1$
                            + "\")"); //$NON-NLS-1$
                    first = false;
                } else {
                    method.addBodyLine("    .map(" + fieldName //$NON-NLS-1$
                            + ").toProperty(\"" + column.getJavaProperty() //$NON-NLS-1$
                            + "\")"); //$NON-NLS-1$
                }
            } else {
                String methodName =
                        JavaBeansUtil.getGetterMethodName(column.getJavaProperty(),
                                column.getFullyQualifiedJavaType());
                if (first) {
                    method.addBodyLine("    c.map(" + fieldName //$NON-NLS-1$
                            + ").toPropertyWhenPresent(\"" + column.getJavaProperty() //$NON-NLS-1$
                            + "\", record::" + methodName //$NON-NLS-1$
                            + ")"); //$NON-NLS-1$
                    first = false;
                } else {
                    method.addBodyLine("    .map(" + fieldName //$NON-NLS-1$
                            + ").toPropertyWhenPresent(\"" + column.getJavaProperty() //$NON-NLS-1$
                            + "\", record::" + methodName //$NON-NLS-1$
                            + ")"); //$NON-NLS-1$
                }
            }
        }
        
        method.addBodyLine(");"); //$NON-NLS-1$
        
        return MethodAndImports.withMethod(method)
                .withImports(imports)
                .build();
    }

    @Override
    public boolean callPlugins(Method method, Interface interfaze) {
        return context.getPlugins().clientInsertSelectiveMethodGenerated(method, interfaze, introspectedTable);
    }

    public static class Builder extends BaseBuilder<Builder, InsertSelectiveMethodGeneratorV2> {
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
        public InsertSelectiveMethodGeneratorV2 build() {
            return new InsertSelectiveMethodGeneratorV2(this);
        }
    }
}
