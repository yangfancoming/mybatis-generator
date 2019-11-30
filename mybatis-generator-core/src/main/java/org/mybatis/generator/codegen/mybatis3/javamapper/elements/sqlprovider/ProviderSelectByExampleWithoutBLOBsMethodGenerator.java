
package org.mybatis.generator.codegen.mybatis3.javamapper.elements.sqlprovider;

import static org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities.getSelectListPhrase;
import static org.mybatis.generator.internal.util.StringUtility.escapeStringForJava;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class ProviderSelectByExampleWithoutBLOBsMethodGenerator extends AbstractJavaProviderMethodGenerator {

    public ProviderSelectByExampleWithoutBLOBsMethodGenerator(boolean useLegacyBuilder) {
        super(useLegacyBuilder);
    }

    @Override
    public void addClassElements(TopLevelClass topLevelClass) {
        Set<String> staticImports = new TreeSet<>();
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();

        if (useLegacyBuilder) {
            staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.BEGIN"); //$NON-NLS-1$
            staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.SELECT"); //$NON-NLS-1$
            staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.SELECT_DISTINCT"); //$NON-NLS-1$
            staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.FROM"); //$NON-NLS-1$
            staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.ORDER_BY"); //$NON-NLS-1$
            staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.SQL"); //$NON-NLS-1$
        } else {
            importedTypes.add(NEW_BUILDER_IMPORT);
        }

        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(introspectedTable.getExampleType());
        importedTypes.add(fqjt);

        Method method = new Method(getMethodName());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getStringInstance());
        method.addParameter(new Parameter(fqjt, "example")); //$NON-NLS-1$
        
        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);
        
        if (useLegacyBuilder) {
            method.addBodyLine("BEGIN();"); //$NON-NLS-1$
        } else {
            method.addBodyLine("SQL sql = new SQL();"); //$NON-NLS-1$
        }

        boolean distinctCheck = true;
        for (IntrospectedColumn introspectedColumn : getColumns()) {
            if (distinctCheck) {
                method.addBodyLine("if (example != null && example.isDistinct()) {"); //$NON-NLS-1$
                method.addBodyLine(String.format("%sSELECT_DISTINCT(\"%s\");", //$NON-NLS-1$
                        builderPrefix,
                        escapeStringForJava(getSelectListPhrase(introspectedColumn))));
                method.addBodyLine("} else {"); //$NON-NLS-1$
                method.addBodyLine(String.format("%sSELECT(\"%s\");", //$NON-NLS-1$
                        builderPrefix,
                        escapeStringForJava(getSelectListPhrase(introspectedColumn))));
                method.addBodyLine("}"); //$NON-NLS-1$
            } else {
                method.addBodyLine(String.format("%sSELECT(\"%s\");", //$NON-NLS-1$
                        builderPrefix,
                        escapeStringForJava(getSelectListPhrase(introspectedColumn))));
            }

            distinctCheck = false;
        }

        method.addBodyLine(String.format("%sFROM(\"%s\");", //$NON-NLS-1$
                builderPrefix,
                escapeStringForJava(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime())));
        if (useLegacyBuilder) {
            method.addBodyLine("applyWhere(example, false);"); //$NON-NLS-1$
        } else {
            method.addBodyLine("applyWhere(sql, example, false);"); //$NON-NLS-1$
        }

        method.addBodyLine(""); //$NON-NLS-1$
        method.addBodyLine("if (example != null && example.getOrderByClause() != null) {"); //$NON-NLS-1$
        method.addBodyLine(String.format("%sORDER_BY(example.getOrderByClause());", builderPrefix)); //$NON-NLS-1$
        method.addBodyLine("}"); //$NON-NLS-1$

        method.addBodyLine(""); //$NON-NLS-1$
        if (useLegacyBuilder) {
            method.addBodyLine("return SQL();"); //$NON-NLS-1$
        } else {
            method.addBodyLine("return sql.toString();"); //$NON-NLS-1$
        }

        if (callPlugins(method, topLevelClass)) {
            topLevelClass.addStaticImports(staticImports);
            topLevelClass.addImportedTypes(importedTypes);
            topLevelClass.addMethod(method);
        }
    }

    public List<IntrospectedColumn> getColumns() {
        return introspectedTable.getNonBLOBColumns();
    }

    public String getMethodName() {
        return introspectedTable.getSelectByExampleStatementId();
    }

    public boolean callPlugins(Method method, TopLevelClass topLevelClass) {
        return context.getPlugins().providerSelectByExampleWithoutBLOBsMethodGenerated(method, topLevelClass,
                introspectedTable);
    }
}
