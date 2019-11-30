
package org.mybatis.generator.codegen.mybatis3.javamapper.elements.sqlprovider;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractGenerator;

public abstract class AbstractJavaProviderMethodGenerator extends
        AbstractGenerator {

    protected static final FullyQualifiedJavaType NEW_BUILDER_IMPORT =
            new FullyQualifiedJavaType("org.apache.ibatis.jdbc.SQL"); //$NON-NLS-1$
    protected boolean useLegacyBuilder;
    protected final String builderPrefix;

    public AbstractJavaProviderMethodGenerator(boolean useLegacyBuilder) {
        super();
        this.useLegacyBuilder = useLegacyBuilder;
        if (useLegacyBuilder) {
            builderPrefix = ""; //$NON-NLS-1$
        } else {
            builderPrefix = "sql."; //$NON-NLS-1$
        }
    }

    public abstract void addClassElements(TopLevelClass topLevelClass);
}
