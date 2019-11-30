
package org.mybatis.generator.runtime.kotlin.elements;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.kotlin.FullyQualifiedKotlinType;
import org.mybatis.generator.api.dom.kotlin.KotlinArg;
import org.mybatis.generator.api.dom.kotlin.KotlinFile;
import org.mybatis.generator.api.dom.kotlin.KotlinFunction;

public class UpdateSelectiveColumnsMethodGenerator extends AbstractKotlinFunctionGenerator {
    private FullyQualifiedKotlinType recordType;
    private KotlinFragmentGenerator fragmentGenerator;
    
    private UpdateSelectiveColumnsMethodGenerator(Builder builder) {
        super(builder);
        recordType = builder.recordType;
        fragmentGenerator = builder.fragmentGenerator;
    }

    @Override
    public KotlinFunctionAndImports generateMethodAndImports() {

        KotlinFunctionAndImports functionAndImports = KotlinFunctionAndImports.withFunction(
                KotlinFunction.newOneLineFunction("KotlinUpdateBuilder.updateSelectiveColumns") //$NON-NLS-1$
                .withArgument(KotlinArg.newArg("record") //$NON-NLS-1$
                        .withDataType(recordType.getShortNameWithTypeArguments())
                        .build())
                .build())
                .withImport("org.mybatis.dynamic.sql.util.kotlin.*") //$NON-NLS-1$
                .withImports(recordType.getImportList())
                .build();

        addFunctionComment(functionAndImports);
        
        KotlinFunction function = functionAndImports.getFunction();
        
        function.addCodeLine("apply {"); //$NON-NLS-1$
        
        List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
        KotlinFunctionParts functionParts = fragmentGenerator.getSetEqualWhenPresentLines(columns);
        
        acceptParts(functionAndImports, functionParts);
        
        function.addCodeLine("}"); //$NON-NLS-1$
        
        return functionAndImports;
    }

    @Override
    public boolean callPlugins(KotlinFunction kotlinFunction, KotlinFile kotlinFile) {
        return context.getPlugins().clientUpdateSelectiveColumnsMethodGenerated(kotlinFunction, kotlinFile,
                introspectedTable);
    }

    public static class Builder extends BaseBuilder<Builder, UpdateSelectiveColumnsMethodGenerator> {
        private FullyQualifiedKotlinType recordType;
        private KotlinFragmentGenerator fragmentGenerator;
        
        public Builder withRecordType(FullyQualifiedKotlinType recordType) {
            this.recordType = recordType;
            return this;
        }
        
        public Builder withFragmentGenerator(KotlinFragmentGenerator fragmentGenerator) {
            this.fragmentGenerator = fragmentGenerator;
            return this;
        }

        @Override
        public Builder getThis() {
            return this;
        }

        @Override
        public UpdateSelectiveColumnsMethodGenerator build() {
            return new UpdateSelectiveColumnsMethodGenerator(this);
        }
    }
}
