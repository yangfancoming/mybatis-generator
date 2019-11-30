
package org.mybatis.generator.codegen.mybatis3.javamapper.elements.sqlprovider;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class ProviderUpdateByExampleWithBLOBsMethodGenerator extends
        ProviderUpdateByExampleWithoutBLOBsMethodGenerator {

    public ProviderUpdateByExampleWithBLOBsMethodGenerator(boolean useLegacyBuilder) {
        super(useLegacyBuilder);
    }

    @Override
    public String getMethodName() {
        return introspectedTable.getUpdateByExampleWithBLOBsStatementId();
    }

    @Override
    public List<IntrospectedColumn> getColumns() {
        return introspectedTable.getAllColumns();
    }

    @Override
    public boolean callPlugins(Method method, TopLevelClass topLevelClass) {
        return context.getPlugins().providerUpdateByExampleWithBLOBsMethodGenerated(method, topLevelClass,
                introspectedTable);
    }
}
