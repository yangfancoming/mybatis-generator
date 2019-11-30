
package org.mybatis.generator.runtime.kotlin.elements;

import org.mybatis.generator.api.dom.kotlin.KotlinArg;
import org.mybatis.generator.api.dom.kotlin.KotlinFile;
import org.mybatis.generator.api.dom.kotlin.KotlinFunction;

public class BasicUpdateMethodGenerator extends AbstractKotlinFunctionGenerator {
    
    private BasicUpdateMethodGenerator(Builder builder) {
        super(builder);
    }

    @Override
    public KotlinFunctionAndImports generateMethodAndImports() {
        KotlinFunctionAndImports functionAndImports = KotlinFunctionAndImports.withFunction(
                KotlinFunction.newOneLineFunction("update") //$NON-NLS-1$
                .withExplicitReturnType("Int") //$NON-NLS-1$
                .withArgument(KotlinArg.newArg("updateStatement") //$NON-NLS-1$
                        .withDataType("UpdateStatementProvider") //$NON-NLS-1$
                        .build())
                .withAnnotation("@UpdateProvider(type=SqlProviderAdapter::class, method=\"update\")") //$NON-NLS-1$
                .build())
                .withImport("org.mybatis.dynamic.sql.update.render.UpdateStatementProvider") //$NON-NLS-1$
                .withImport("org.mybatis.dynamic.sql.util.SqlProviderAdapter") //$NON-NLS-1$
                .withImport("org.apache.ibatis.annotations.UpdateProvider") //$NON-NLS-1$
                .build();
        
        addFunctionComment(functionAndImports);
        return functionAndImports;
    }

    @Override
    public boolean callPlugins(KotlinFunction kotlinFunction, KotlinFile kotlinFile) {
        return context.getPlugins().clientBasicUpdateMethodGenerated(kotlinFunction, kotlinFile, introspectedTable);
    }

    public static class Builder extends BaseBuilder<Builder, BasicUpdateMethodGenerator> {

        @Override
        public Builder getThis() {
            return this;
        }

        @Override
        public BasicUpdateMethodGenerator build() {
            return new BasicUpdateMethodGenerator(this);
        }
    }
}
