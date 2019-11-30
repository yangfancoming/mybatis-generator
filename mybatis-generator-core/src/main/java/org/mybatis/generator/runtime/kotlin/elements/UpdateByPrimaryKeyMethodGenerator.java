
package org.mybatis.generator.runtime.kotlin.elements;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.kotlin.FullyQualifiedKotlinType;
import org.mybatis.generator.api.dom.kotlin.KotlinArg;
import org.mybatis.generator.api.dom.kotlin.KotlinFile;
import org.mybatis.generator.api.dom.kotlin.KotlinFunction;
import org.mybatis.generator.runtime.dynamic.sql.elements.v2.Utils;

public class UpdateByPrimaryKeyMethodGenerator extends AbstractKotlinFunctionGenerator {
    private FullyQualifiedKotlinType recordType;
    private KotlinFragmentGenerator fragmentGenerator;
    private String mapperName;
    
    private UpdateByPrimaryKeyMethodGenerator(Builder builder) {
        super(builder);
        recordType = builder.recordType;
        fragmentGenerator = builder.fragmentGenerator;
        mapperName = builder.mapperName;
    }

    @Override
    public KotlinFunctionAndImports generateMethodAndImports() {
        if (!Utils.generateUpdateByPrimaryKey(introspectedTable)) {
            return null;
        }
        
        KotlinFunctionAndImports functionAndImports = KotlinFunctionAndImports.withFunction(
                KotlinFunction.newOneLineFunction(mapperName + ".updateByPrimaryKey") //$NON-NLS-1$
                .withArgument(KotlinArg.newArg("record") //$NON-NLS-1$
                        .withDataType(recordType.getShortNameWithTypeArguments())
                        .build())
                .withCodeLine("update {") //$NON-NLS-1$
                .build())
                .withImports(recordType.getImportList())
                .build();
        
        addFunctionComment(functionAndImports);
        
        List<IntrospectedColumn> columns = introspectedTable.getNonPrimaryKeyColumns();
        KotlinFunctionParts functionParts = fragmentGenerator.getSetEqualLines(columns);
        acceptParts(functionAndImports, functionParts);
        
        functionParts = fragmentGenerator.getPrimaryKeyWhereClauseForUpdate();
        acceptParts(functionAndImports, functionParts);
        
        return functionAndImports;
    }

    @Override
    public boolean callPlugins(KotlinFunction kotlinFunction, KotlinFile kotlinFile) {
        return context.getPlugins().clientUpdateByPrimaryKeyMethodGenerated(kotlinFunction,
                kotlinFile, introspectedTable);
    }

    public static class Builder extends BaseBuilder<Builder, UpdateByPrimaryKeyMethodGenerator> {
        private FullyQualifiedKotlinType recordType;
        private KotlinFragmentGenerator fragmentGenerator;
        private String mapperName;
        
        public Builder withRecordType(FullyQualifiedKotlinType recordType) {
            this.recordType = recordType;
            return this;
        }
        
        public Builder withFragmentGenerator(KotlinFragmentGenerator fragmentGenerator) {
            this.fragmentGenerator = fragmentGenerator;
            return this;
        }

        public Builder withMapperName(String mapperName) {
            this.mapperName = mapperName;
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
