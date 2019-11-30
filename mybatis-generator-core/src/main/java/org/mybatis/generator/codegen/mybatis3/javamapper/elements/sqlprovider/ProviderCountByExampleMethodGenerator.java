
package org.mybatis.generator.codegen.mybatis3.javamapper.elements.sqlprovider;

import static org.mybatis.generator.internal.util.StringUtility.escapeStringForJava;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class ProviderCountByExampleMethodGenerator extends AbstractJavaProviderMethodGenerator {

    public ProviderCountByExampleMethodGenerator(boolean useLegacyBuilder) {
        super(useLegacyBuilder);
    }

    @Override
    public void addClassElements(TopLevelClass topLevelClass) {
        Set<String> staticImports = new TreeSet<>();
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();

        if (useLegacyBuilder) {
            staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.BEGIN"); //$NON-NLS-1$
            staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.FROM"); //$NON-NLS-1$
            staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.SELECT"); //$NON-NLS-1$
            staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.SQL"); //$NON-NLS-1$
        } else {
            importedTypes.add(NEW_BUILDER_IMPORT);
        }

        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(introspectedTable.getExampleType());
        importedTypes.add(fqjt);

        Method method = new Method(
                introspectedTable.getCountByExampleStatementId());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getStringInstance());
        method.addParameter(new Parameter(fqjt, "example")); //$NON-NLS-1$
        
        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);

        if (useLegacyBuilder) {
            method.addBodyLine("BEGIN();"); //$NON-NLS-1$
            method.addBodyLine("SELECT(\"count(*)\");"); //$NON-NLS-1$
            method.addBodyLine(String.format("FROM(\"%s\");", //$NON-NLS-1$
                    escapeStringForJava(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime())));
            method.addBodyLine("applyWhere(example, false);"); //$NON-NLS-1$
            method.addBodyLine("return SQL();"); //$NON-NLS-1$
        } else {
            method.addBodyLine("SQL sql = new SQL();"); //$NON-NLS-1$
            method.addBodyLine(String.format("sql.SELECT(\"count(*)\").FROM(\"%s\");", //$NON-NLS-1$
                    escapeStringForJava(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime())));
            method.addBodyLine("applyWhere(sql, example, false);"); //$NON-NLS-1$
            method.addBodyLine("return sql.toString();"); //$NON-NLS-1$
        }
        
        if (context.getPlugins().providerCountByExampleMethodGenerated(method, topLevelClass,
                introspectedTable)) {
            topLevelClass.addStaticImports(staticImports);
            topLevelClass.addImportedTypes(importedTypes);
            topLevelClass.addMethod(method);
        }
    }
}
