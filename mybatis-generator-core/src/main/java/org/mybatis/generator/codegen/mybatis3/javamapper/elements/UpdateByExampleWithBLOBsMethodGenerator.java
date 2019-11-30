
package org.mybatis.generator.codegen.mybatis3.javamapper.elements;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

public class UpdateByExampleWithBLOBsMethodGenerator extends
        AbstractJavaMapperMethodGenerator {

    public UpdateByExampleWithBLOBsMethodGenerator() {
        super();
    }

    @Override
    public void addInterfaceElements(Interface interfaze) {
        Method method = new Method(introspectedTable
                .getUpdateByExampleWithBLOBsStatementId());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setAbstract(true);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());

        FullyQualifiedJavaType parameterType;
        if (introspectedTable.getRules().generateRecordWithBLOBsClass()) {
            parameterType = new FullyQualifiedJavaType(introspectedTable
                    .getRecordWithBLOBsType());
        } else {
            parameterType = new FullyQualifiedJavaType(introspectedTable
                    .getBaseRecordType());
        }
        method.addParameter(new Parameter(parameterType,
                "record", "@Param(\"record\")")); //$NON-NLS-1$ //$NON-NLS-2$

        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();
        importedTypes.add(parameterType);

        FullyQualifiedJavaType exampleType = new FullyQualifiedJavaType(
                introspectedTable.getExampleType());
        method.addParameter(new Parameter(exampleType,
                "example", "@Param(\"example\")")); //$NON-NLS-1$ //$NON-NLS-2$
        importedTypes.add(exampleType);

        importedTypes.add(new FullyQualifiedJavaType(
                "org.apache.ibatis.annotations.Param")); //$NON-NLS-1$

        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);

        addMapperAnnotations(method);
        
        if (context.getPlugins()
                .clientUpdateByExampleWithBLOBsMethodGenerated(method, interfaze,
                        introspectedTable)) {
            addExtraImports(interfaze);
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }
    }

    public void addMapperAnnotations(Method method) {
        // extension point for subclasses
    }

    public void addExtraImports(Interface interfaze) {
        // extension point for subclasses
    }
}
