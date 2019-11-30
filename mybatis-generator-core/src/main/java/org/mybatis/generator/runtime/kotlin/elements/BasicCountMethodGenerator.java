
package org.mybatis.generator.runtime.kotlin.elements;

import org.mybatis.generator.api.dom.kotlin.KotlinArg;
import org.mybatis.generator.api.dom.kotlin.KotlinFile;
import org.mybatis.generator.api.dom.kotlin.KotlinFunction;

public class BasicCountMethodGenerator extends AbstractKotlinFunctionGenerator {
    
    private BasicCountMethodGenerator(Builder builder) {
        super(builder);
    }

    @Override
    public KotlinFunctionAndImports generateMethodAndImports() {
        KotlinFunctionAndImports functionAndImports = KotlinFunctionAndImports.withFunction(
                KotlinFunction.newOneLineFunction("count") //$NON-NLS-1$
                .withExplicitReturnType("Long") //$NON-NLS-1$
                .withArgument(KotlinArg.newArg("selectStatement") //$NON-NLS-1$
                        .withDataType("SelectStatementProvider") //$NON-NLS-1$
                        .build())
                .withAnnotation("@SelectProvider(type=SqlProviderAdapter::class, method=\"select\")") //$NON-NLS-1$
                .build())
                .withImport("org.mybatis.dynamic.sql.select.render.SelectStatementProvider") //$NON-NLS-1$
                .withImport("org.mybatis.dynamic.sql.util.SqlProviderAdapter") //$NON-NLS-1$
                .withImport("org.apache.ibatis.annotations.SelectProvider") //$NON-NLS-1$
                .build();
        
        addFunctionComment(functionAndImports);
        return functionAndImports;
    }
    
    @Override
    public boolean callPlugins(KotlinFunction function, KotlinFile kotlinFile) {
        return context.getPlugins().clientBasicCountMethodGenerated(function, kotlinFile, introspectedTable);
    }
    
    public static class Builder extends BaseBuilder<Builder, BasicCountMethodGenerator> {
        @Override
        public Builder getThis() {
            return this;
        }
        
        @Override
        public BasicCountMethodGenerator build() {
            return new BasicCountMethodGenerator(this);
        }
    }
}
