
package org.mybatis.generator.runtime.kotlin.elements;

import org.mybatis.generator.api.dom.kotlin.KotlinArg;
import org.mybatis.generator.api.dom.kotlin.KotlinFile;
import org.mybatis.generator.api.dom.kotlin.KotlinFunction;

public class GeneralSelectDistinctMethodGenerator extends AbstractKotlinFunctionGenerator {
    private String mapperName;
    
    private GeneralSelectDistinctMethodGenerator(Builder builder) {
        super(builder);
        mapperName = builder.mapperName;
    }

    @Override
    public KotlinFunctionAndImports generateMethodAndImports() {
        KotlinFunctionAndImports functionAndImports = KotlinFunctionAndImports.withFunction(
                KotlinFunction.newOneLineFunction(mapperName + ".selectDistinct") //$NON-NLS-1$
                .withArgument(KotlinArg.newArg("completer") //$NON-NLS-1$
                        .withDataType("SelectCompleter") //$NON-NLS-1$
                        .build())
                .withCodeLine("selectDistinct(this::selectMany, columnList, " + tableFieldName //$NON-NLS-1$
                        + ", completer)") //$NON-NLS-1$
                .build())
                .withImport("org.mybatis.dynamic.sql.util.kotlin.*") //$NON-NLS-1$
                .withImport("org.mybatis.dynamic.sql.util.kotlin.mybatis3.*") //$NON-NLS-1$
                .build();

        addFunctionComment(functionAndImports);
        return functionAndImports;
    }

    @Override
    public boolean callPlugins(KotlinFunction kotlinFunction, KotlinFile kotlinFile) {
        return context.getPlugins().clientGeneralSelectDistinctMethodGenerated(kotlinFunction, kotlinFile,
                introspectedTable);
    }

    public static class Builder extends BaseBuilder<Builder, GeneralSelectDistinctMethodGenerator> {
        private String mapperName;
        
        public Builder withMapperName(String mapperName) {
            this.mapperName = mapperName;
            return this;
        }
        
        @Override
        public Builder getThis() {
            return this;
        }

        @Override
        public GeneralSelectDistinctMethodGenerator build() {
            return new GeneralSelectDistinctMethodGenerator(this);
        }
    }
}
