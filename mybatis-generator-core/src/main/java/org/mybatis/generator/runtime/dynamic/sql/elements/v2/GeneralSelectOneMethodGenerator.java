
package org.mybatis.generator.runtime.dynamic.sql.elements.v2;

import java.util.HashSet;
import java.util.Set;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.runtime.dynamic.sql.elements.AbstractMethodGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.MethodAndImports;

public class GeneralSelectOneMethodGenerator extends AbstractMethodGenerator {
    private FullyQualifiedJavaType recordType;
    
    private GeneralSelectOneMethodGenerator(Builder builder) {
        super(builder);
        recordType = builder.recordType;
    }

    @Override
    public MethodAndImports generateMethodAndImports() {
        Set<FullyQualifiedJavaType> imports = new HashSet<>();
        
        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(
                "org.mybatis.dynamic.sql.select.SelectDSLCompleter"); //$NON-NLS-1$

        imports.add(parameterType);
        imports.add(new FullyQualifiedJavaType("org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils")); //$NON-NLS-1$
        
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("java.util.Optional"); //$NON-NLS-1$
        returnType.addTypeArgument(recordType);
        
        imports.add(returnType);
        
        Method method = new Method("selectOne"); //$NON-NLS-1$
        method.setDefault(true);
        method.addParameter(new Parameter(parameterType, "completer")); //$NON-NLS-1$
        
        context.getCommentGenerator().addGeneralMethodAnnotation(method, introspectedTable, imports);
        
        method.setReturnType(returnType);
        method.addBodyLine("return MyBatis3Utils.selectOne(this::selectOne, selectList, " + //$NON-NLS-1$
                tableFieldName + ", completer);"); //$NON-NLS-1$
        
        return MethodAndImports.withMethod(method)
                .withImports(imports)
                .build();
    }

    @Override
    public boolean callPlugins(Method method, Interface interfaze) {
        return context.getPlugins().clientSelectOneMethodGenerated(method, interfaze, introspectedTable);
    }

    public static class Builder extends BaseBuilder<Builder, GeneralSelectOneMethodGenerator> {
        private FullyQualifiedJavaType recordType;
        
        public Builder withRecordType(FullyQualifiedJavaType recordType) {
            this.recordType = recordType;
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
