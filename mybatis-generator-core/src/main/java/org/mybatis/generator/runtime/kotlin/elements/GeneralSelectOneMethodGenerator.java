
package org.mybatis.generator.runtime.kotlin.elements;

import java.util.Objects;

import org.mybatis.generator.api.dom.kotlin.KotlinArg;
import org.mybatis.generator.api.dom.kotlin.KotlinFile;
import org.mybatis.generator.api.dom.kotlin.KotlinFunction;

public class GeneralSelectOneMethodGenerator extends AbstractKotlinFunctionGenerator {
    private String mapperName;
    
    private GeneralSelectOneMethodGenerator(Builder builder) {
        super(builder);
        mapperName = Objects.requireNonNull(builder.mapperName);
    }

    @Override
    public KotlinFunctionAndImports generateMethodAndImports() {
        KotlinFunctionAndImports functionAndImports = KotlinFunctionAndImports.withFunction(
                KotlinFunction.newOneLineFunction(mapperName + ".selectOne") //$NON-NLS-1$
                .withArgument(KotlinArg.newArg("completer") //$NON-NLS-1$
                        .withDataType("SelectCompleter") //$NON-NLS-1$
                        .build())
                .withCodeLine("selectOne(this::selectOne, columnList, " + tableFieldName //$NON-NLS-1$
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
        return context.getPlugins().clientSelectOneMethodGenerated(kotlinFunction, kotlinFile, introspectedTable);
    }

    public static class Builder extends BaseBuilder<Builder, GeneralSelectOneMethodGenerator> {
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
        public GeneralSelectOneMethodGenerator build() {
            return new GeneralSelectOneMethodGenerator(this);
        }
    }
}
