
package org.mybatis.generator.codegen.mybatis3.javamapper.elements.annotated;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.DeleteByExampleMethodGenerator;

public class AnnotatedDeleteByExampleMethodGenerator extends
        DeleteByExampleMethodGenerator {

    public AnnotatedDeleteByExampleMethodGenerator() {
        super();
    }

    @Override
    public void addMapperAnnotations(Method method) {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(introspectedTable.getMyBatis3SqlProviderType());
        StringBuilder sb = new StringBuilder();
        sb.append("@DeleteProvider(type="); //$NON-NLS-1$
        sb.append(fqjt.getShortName());
        sb.append(".class, method=\""); //$NON-NLS-1$
        sb.append(introspectedTable.getDeleteByExampleStatementId());
        sb.append("\")"); //$NON-NLS-1$

        method.addAnnotation(sb.toString());
    }

    @Override
    public void addExtraImports(Interface interfaze) {
        interfaze.addImportedType(
                new FullyQualifiedJavaType("org.apache.ibatis.annotations.DeleteProvider")); //$NON-NLS-1$
    }
}
