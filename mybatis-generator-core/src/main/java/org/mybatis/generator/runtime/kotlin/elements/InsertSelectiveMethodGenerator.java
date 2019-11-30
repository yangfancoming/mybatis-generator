
package org.mybatis.generator.runtime.kotlin.elements;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.kotlin.FullyQualifiedKotlinType;
import org.mybatis.generator.api.dom.kotlin.KotlinArg;
import org.mybatis.generator.api.dom.kotlin.KotlinFile;
import org.mybatis.generator.api.dom.kotlin.KotlinFunction;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;

public class InsertSelectiveMethodGenerator extends AbstractKotlinFunctionGenerator {
    private FullyQualifiedKotlinType recordType;
    private String mapperName;
    private String tableFieldImport;
    
    private InsertSelectiveMethodGenerator(Builder builder) {
        super(builder);
        recordType = builder.recordType;
        mapperName = builder.mapperName;
        tableFieldImport = builder.tableFieldImport;
    }

    @Override
    public KotlinFunctionAndImports generateMethodAndImports() {
        KotlinFunctionAndImports functionAndImports = KotlinFunctionAndImports.withFunction(
                KotlinFunction.newOneLineFunction(mapperName + ".insertSelective") //$NON-NLS-1$
                .withArgument(KotlinArg.newArg("record") //$NON-NLS-1$
                        .withDataType(recordType.getShortNameWithTypeArguments())
                        .build())
                .build())
                .withImport("org.mybatis.dynamic.sql.util.kotlin.mybatis3.*") //$NON-NLS-1$
                .withImports(recordType.getImportList())
                .build();

        addFunctionComment(functionAndImports);
        
        KotlinFunction function = functionAndImports.getFunction();
        
        function.addCodeLine("insert(this::insert, record, " + tableFieldName + //$NON-NLS-1$
                ") {"); //$NON-NLS-1$
        
        List<IntrospectedColumn> columns =
                ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns());
        for (IntrospectedColumn column : columns) {
            String fieldName = column.getJavaProperty();
            functionAndImports.getImports().add(tableFieldImport + "." + fieldName); //$NON-NLS-1$
            
            if (column.isSequenceColumn()) {
                function.addCodeLine("    map(" + fieldName //$NON-NLS-1$
                        + ").toProperty(\"" + column.getJavaProperty() //$NON-NLS-1$
                        + "\")"); //$NON-NLS-1$
            } else {
                function.addCodeLine("    map(" + fieldName //$NON-NLS-1$
                        + ").toPropertyWhenPresent(\"" + column.getJavaProperty() //$NON-NLS-1$
                        + "\", record::" //$NON-NLS-1$
                        + fieldName + ")"); //$NON-NLS-1$
            }
        }
        
        function.addCodeLine("}"); //$NON-NLS-1$
        
        return functionAndImports;
    }

    @Override
    public boolean callPlugins(KotlinFunction kotlinFunction, KotlinFile kotlinFile) {
        return context.getPlugins().clientInsertSelectiveMethodGenerated(kotlinFunction, kotlinFile, introspectedTable);
    }

    public static class Builder extends BaseBuilder<Builder, InsertSelectiveMethodGenerator> {
        private FullyQualifiedKotlinType recordType;
        private String mapperName;
        private String tableFieldImport;
        
        public Builder withRecordType(FullyQualifiedKotlinType recordType) {
            this.recordType = recordType;
            return this;
        }
        
        public Builder withMapperName(String mapperName) {
            this.mapperName = mapperName;
            return this;
        }
        
        public Builder withTableFieldImport(String tableFieldImport) {
            this.tableFieldImport = tableFieldImport;
            return this;
        }
        
        @Override
        public Builder getThis() {
            return this;
        }

        @Override
        public InsertSelectiveMethodGenerator build() {
            return new InsertSelectiveMethodGenerator(this);
        }
    }
}
