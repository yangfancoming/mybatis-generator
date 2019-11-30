
package org.mybatis.generator.runtime.kotlin.elements;

import org.mybatis.generator.api.dom.kotlin.KotlinFile;
import org.mybatis.generator.api.dom.kotlin.KotlinFunction;
import org.mybatis.generator.runtime.dynamic.sql.elements.v2.Utils;

public class SelectByPrimaryKeyMethodGenerator extends AbstractKotlinFunctionGenerator {
    private String mapperName;
    private KotlinFragmentGenerator fragmentGenerator;
    
    private SelectByPrimaryKeyMethodGenerator(Builder builder) {
        super(builder);
        mapperName = builder.mapperName;
        fragmentGenerator = builder.fragmentGenerator;
    }

    @Override
    public KotlinFunctionAndImports generateMethodAndImports() {
        if (!Utils.generateSelectByPrimaryKey(introspectedTable)) {
            return null;
        }

        KotlinFunctionAndImports functionAndImports = KotlinFunctionAndImports.withFunction(
                KotlinFunction.newOneLineFunction(mapperName + ".selectByPrimaryKey") //$NON-NLS-1$
                .withCodeLine("selectOne {") //$NON-NLS-1$
                .build())
                .withImport("org.mybatis.dynamic.sql.SqlBuilder.isEqualTo") //$NON-NLS-1$
                .build();
        
        addFunctionComment(functionAndImports);

        KotlinFunctionParts functionParts = fragmentGenerator.getPrimaryKeyWhereClauseAndParameters();
        acceptParts(functionAndImports, functionParts);
        
        return functionAndImports;
    }

    @Override
    public boolean callPlugins(KotlinFunction kotlinFunction, KotlinFile kotlinFile) {
        return context.getPlugins().clientSelectByPrimaryKeyMethodGenerated(kotlinFunction, kotlinFile,
                introspectedTable);
    }

    public static class Builder extends BaseBuilder<Builder, SelectByPrimaryKeyMethodGenerator> {
        private String mapperName;
        private KotlinFragmentGenerator fragmentGenerator;
        
        public Builder withMapperName(String mapperName) {
            this.mapperName = mapperName;
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
        public SelectByPrimaryKeyMethodGenerator build() {
            return new SelectByPrimaryKeyMethodGenerator(this);
        }
    }
}
