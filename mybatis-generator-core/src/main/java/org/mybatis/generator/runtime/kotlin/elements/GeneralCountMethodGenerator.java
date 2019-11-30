
package org.mybatis.generator.runtime.kotlin.elements;

import java.util.Objects;

import org.mybatis.generator.api.dom.kotlin.KotlinArg;
import org.mybatis.generator.api.dom.kotlin.KotlinFile;
import org.mybatis.generator.api.dom.kotlin.KotlinFunction;

public class GeneralCountMethodGenerator extends AbstractKotlinFunctionGenerator {

    private String mapperName;
    
    private GeneralCountMethodGenerator(Builder builder) {
        super(builder);
        mapperName = Objects.requireNonNull(builder.mapperName);
    }
    
    @Override
    public KotlinFunctionAndImports generateMethodAndImports() {
        KotlinFunctionAndImports functionAndImports = KotlinFunctionAndImports.withFunction(
                KotlinFunction.newOneLineFunction(mapperName + ".count") //$NON-NLS-1$
                .withArgument(KotlinArg.newArg("completer") //$NON-NLS-1$
                        .withDataType("CountCompleter") //$NON-NLS-1$
                        .build())
                .withCodeLine("countFrom(this::count, " + tableFieldName + ", completer)") //$NON-NLS-1$ //$NON-NLS-2$
                .build())
                .withImport("org.mybatis.dynamic.sql.util.kotlin.*") //$NON-NLS-1$
                .withImport("org.mybatis.dynamic.sql.util.kotlin.mybatis3.*") //$NON-NLS-1$
                .withImport(tableFieldImport)
                .build();

        addFunctionComment(functionAndImports);
        return functionAndImports;
    }

    @Override
    public boolean callPlugins(KotlinFunction kotlinFunction, KotlinFile kotlinFile) {
        return context.getPlugins().clientGeneralCountMethodGenerated(kotlinFunction, kotlinFile, introspectedTable);
    }

    public static class Builder extends BaseBuilder<Builder, GeneralCountMethodGenerator> {
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
        public GeneralCountMethodGenerator build() {
            return new GeneralCountMethodGenerator(this);
        }
    }
}
