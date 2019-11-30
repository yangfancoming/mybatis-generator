
package org.mybatis.generator.runtime.kotlin.elements;

import org.mybatis.generator.api.dom.kotlin.KotlinArg;
import org.mybatis.generator.api.dom.kotlin.KotlinFile;
import org.mybatis.generator.api.dom.kotlin.KotlinFunction;

public class BasicDeleteMethodGenerator extends AbstractKotlinFunctionGenerator {

    private BasicDeleteMethodGenerator(Builder builder) {
        super(builder);
    }

    @Override
    public KotlinFunctionAndImports generateMethodAndImports() {
        KotlinFunctionAndImports functionAndImports = KotlinFunctionAndImports.withFunction(
                KotlinFunction.newOneLineFunction("delete") //$NON-NLS-1$
                .withExplicitReturnType("Int") //$NON-NLS-1$
                .withArgument(KotlinArg.newArg("deleteStatement") //$NON-NLS-1$
                        .withDataType("DeleteStatementProvider") //$NON-NLS-1$)
                        .build())
                .withAnnotation("@DeleteProvider(type=SqlProviderAdapter::class, method=\"delete\")") //$NON-NLS-1$
                .build())
                .withImport("org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider") //$NON-NLS-1$
                .withImport("org.mybatis.dynamic.sql.util.SqlProviderAdapter") //$NON-NLS-1$
                .withImport("org.apache.ibatis.annotations.DeleteProvider") //$NON-NLS-1$
                .build();
        
        addFunctionComment(functionAndImports);
        return functionAndImports;
    }

    @Override
    public boolean callPlugins(KotlinFunction function, KotlinFile kotlinFile) {
        return context.getPlugins().clientBasicDeleteMethodGenerated(function, kotlinFile, introspectedTable);
    }

    public static class Builder extends BaseBuilder<Builder, BasicDeleteMethodGenerator> {

        @Override
        public Builder getThis() {
            return this;
        }

        @Override
        public BasicDeleteMethodGenerator build() {
            return new BasicDeleteMethodGenerator(this);
        }
    }
}
