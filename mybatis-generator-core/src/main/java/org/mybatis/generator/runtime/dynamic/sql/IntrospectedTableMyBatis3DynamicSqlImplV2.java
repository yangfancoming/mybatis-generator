
package org.mybatis.generator.runtime.dynamic.sql;

import org.mybatis.generator.codegen.AbstractJavaClientGenerator;

public class IntrospectedTableMyBatis3DynamicSqlImplV2 extends IntrospectedTableMyBatis3DynamicSqlImplV1 {

    @Override
    protected AbstractJavaClientGenerator createJavaClientGenerator() {
        if (context.getJavaClientGeneratorConfiguration() == null) {
            return null;
        }

        return new DynamicSqlMapperGeneratorV2(getClientProject());
    }
}
