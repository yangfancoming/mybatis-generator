
package org.mybatis.generator.codegen.mybatis3.javamapper.elements.sqlprovider;

import static org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities.getAliasedEscapedColumnName;
import static org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities.getParameterClause;
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
import org.mybatis.generator.codegen.mybatis3.ListUtilities;

public class ProviderUpdateByExampleWithoutBLOBsMethodGenerator extends
        AbstractJavaProviderMethodGenerator {

    public ProviderUpdateByExampleWithoutBLOBsMethodGenerator(boolean useLegacyBuilder) {
        super(useLegacyBuilder);
    }

    @Override
    public void addClassElements(TopLevelClass topLevelClass) {
        Set<String> staticImports = new TreeSet<>();
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();

        if (useLegacyBuilder) {
            staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.BEGIN"); //$NON-NLS-1$
            staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.UPDATE"); //$NON-NLS-1$
            staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.SET"); //$NON-NLS-1$
            staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.SQL"); //$NON-NLS-1$
        } else {
            importedTypes.add(NEW_BUILDER_IMPORT);
        }

        importedTypes.add(new FullyQualifiedJavaType("java.util.Map")); //$NON-NLS-1$

        Method method = new Method(getMethodName());
        method.setReturnType(FullyQualifiedJavaType.getStringInstance());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addParameter(new Parameter(
                new FullyQualifiedJavaType("java.util.Map<java.lang.String, java.lang.Object>"), //$NON-NLS-1$
                "parameter")); //$NON-NLS-1$
        
        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);

        if (useLegacyBuilder) {
            method.addBodyLine("BEGIN();"); //$NON-NLS-1$
        } else {
            method.addBodyLine("SQL sql = new SQL();"); //$NON-NLS-1$
        }

        method.addBodyLine(String.format("%sUPDATE(\"%s\");", //$NON-NLS-1$
                builderPrefix,
                escapeStringForJava(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime())));
        method.addBodyLine(""); //$NON-NLS-1$

        for (IntrospectedColumn introspectedColumn : ListUtilities.removeGeneratedAlwaysColumns(getColumns())) {
            StringBuilder sb = new StringBuilder();
            sb.append(getParameterClause(introspectedColumn));
            sb.insert(2, "record."); //$NON-NLS-1$

            method.addBodyLine(String.format("%sSET(\"%s = %s\");", //$NON-NLS-1$
                    builderPrefix,
                    escapeStringForJava(getAliasedEscapedColumnName(introspectedColumn)),
                    sb.toString()));
        }

        method.addBodyLine(""); //$NON-NLS-1$
        
        FullyQualifiedJavaType example =
                new FullyQualifiedJavaType(introspectedTable.getExampleType());
        importedTypes.add(example);
        method.addBodyLine(String.format("%s example = (%s) parameter.get(\"example\");", //$NON-NLS-1$
                example.getShortName(), example.getShortName()));

        if (useLegacyBuilder) {
            method.addBodyLine("applyWhere(example, true);"); //$NON-NLS-1$
            method.addBodyLine("return SQL();"); //$NON-NLS-1$
        } else {
            method.addBodyLine("applyWhere(sql, example, true);"); //$NON-NLS-1$
            method.addBodyLine("return sql.toString();"); //$NON-NLS-1$
        }

        if (callPlugins(method, topLevelClass)) {
            topLevelClass.addStaticImports(staticImports);
            topLevelClass.addImportedTypes(importedTypes);
            topLevelClass.addMethod(method);
        }
    }

    public String getMethodName() {
        return introspectedTable.getUpdateByExampleStatementId();
    }

    public List<IntrospectedColumn> getColumns() {
        return introspectedTable.getNonBLOBColumns();
    }

    public boolean callPlugins(Method method, TopLevelClass topLevelClass) {
        return context.getPlugins().providerUpdateByExampleWithoutBLOBsMethodGenerated(method, topLevelClass,
                introspectedTable);
    }
}
