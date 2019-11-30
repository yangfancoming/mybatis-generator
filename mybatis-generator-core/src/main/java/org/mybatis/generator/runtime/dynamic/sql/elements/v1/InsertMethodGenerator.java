
package org.mybatis.generator.runtime.dynamic.sql.elements.v1;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.runtime.dynamic.sql.elements.AbstractMethodGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.MethodAndImports;

public class InsertMethodGenerator extends AbstractMethodGenerator {
    private FullyQualifiedJavaType recordType;
    
    private InsertMethodGenerator(Builder builder) {
        super(builder);
        recordType = builder.recordType;
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        if (!introspectedTable.getRules().generateInsert()) {
            return null;
        }
        
        Set<FullyQualifiedJavaType> imports = new HashSet<>();

        imports.add(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.SqlBuilder")); //$NON-NLS-1$
        imports.add(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.render.RenderingStrategy")); //$NON-NLS-1$
        imports.add(recordType);
        
        Method method = new Method("insert"); //$NON-NLS-1$
        method.setDefault(true);
        context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable, imports);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(recordType, "record")); //$NON-NLS-1$
        
        method.addBodyLine("return insert(SqlBuilder.insert(record)"); //$NON-NLS-1$
        method.addBodyLine("        .into(" + tableFieldName + ")"); //$NON-NLS-1$ //$NON-NLS-2$
        
        List<IntrospectedColumn> columns =
                ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns());
        for (IntrospectedColumn column : columns) {
            String fieldName = calculateFieldName(column);
            
            method.addBodyLine("        .map(" + fieldName //$NON-NLS-1$
                    + ").toProperty(\"" + column.getJavaProperty() //$NON-NLS-1$
                    + "\")"); //$NON-NLS-1$
        }
        
        method.addBodyLine("        .build()"); //$NON-NLS-1$
        method.addBodyLine("        .render(RenderingStrategy.MYBATIS3));"); //$NON-NLS-1$
        
        return MethodAndImports.withMethod(method)
                .withImports(imports)
                .build();
    }

    @Override
    public boolean callPlugins(Method method, Interface interfaze) {
        return context.getPlugins().clientInsertMethodGenerated(method, interfaze, introspectedTable);
    }

    public static class Builder extends BaseBuilder<Builder, InsertMethodGenerator> {
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
        public InsertMethodGenerator build() {
            return new InsertMethodGenerator(this);
        }
    }
}
