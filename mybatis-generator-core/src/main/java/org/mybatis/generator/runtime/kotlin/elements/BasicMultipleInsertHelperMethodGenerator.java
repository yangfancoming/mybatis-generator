
package org.mybatis.generator.runtime.kotlin.elements;

import java.util.Objects;

import org.mybatis.generator.api.dom.kotlin.FullyQualifiedKotlinType;
import org.mybatis.generator.api.dom.kotlin.KotlinArg;
import org.mybatis.generator.api.dom.kotlin.KotlinFile;
import org.mybatis.generator.api.dom.kotlin.KotlinFunction;
import org.mybatis.generator.runtime.dynamic.sql.elements.v2.Utils;

public class BasicMultipleInsertHelperMethodGenerator extends AbstractKotlinFunctionGenerator {
    
    private FullyQualifiedKotlinType recordType;
    private String mapperName;
    
    private BasicMultipleInsertHelperMethodGenerator(Builder builder) {
        super(builder);
        recordType = builder.recordType;
        mapperName = Objects.requireNonNull(builder.mapperName);
    }

    @Override
    public KotlinFunctionAndImports generateMethodAndImports() {
        if (!Utils.generateMultipleRowInsertHelper(introspectedTable)) {
            return null;
        }
  
        KotlinFunctionAndImports functionAndImports = KotlinFunctionAndImports.withFunction(
                KotlinFunction.newOneLineFunction(mapperName + ".insertMultipleHelper") //$NON-NLS-1$
                .withArgument(KotlinArg.newArg("multipleInsertStatement") //$NON-NLS-1$
                        .withDataType("MultiRowInsertStatementProvider<" //$NON-NLS-1$
                                + recordType.getShortNameWithTypeArguments()
                                + ">") //$NON-NLS-1$
                        .build())
                .withCodeLine("insertMultiple(multipleInsertStatement.insertStatement," //$NON-NLS-1$
                        + " multipleInsertStatement.records)") //$NON-NLS-1$
                .build())
                .withImport("org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider") //$NON-NLS-1$
                .withImports(recordType.getImportList())
                .build();
        
        addFunctionComment(functionAndImports);
        return functionAndImports;
    }
    
    @Override
    public boolean callPlugins(KotlinFunction kotlinFunction, KotlinFile kotlinFile) {
        return context.getPlugins().clientBasicInsertMultipleHelperMethodGenerated(kotlinFunction, kotlinFile,
                introspectedTable);
    }

    public static class Builder extends BaseBuilder<Builder, BasicMultipleInsertHelperMethodGenerator> {

        private FullyQualifiedKotlinType recordType;
        private String mapperName;
        
        public Builder withRecordType(FullyQualifiedKotlinType recordType) {
            this.recordType = recordType;
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
        public BasicMultipleInsertHelperMethodGenerator build() {
            return new BasicMultipleInsertHelperMethodGenerator(this);
        }
    }
}
