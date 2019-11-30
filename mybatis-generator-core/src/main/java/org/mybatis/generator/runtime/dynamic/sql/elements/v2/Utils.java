
package org.mybatis.generator.runtime.dynamic.sql.elements.v2;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.config.GeneratedKey;

public class Utils {

    public static boolean generateDeleteByPrimaryKey(IntrospectedTable introspectedTable) {
        return introspectedTable.hasPrimaryKeyColumns();
    }

    public static boolean generateMultipleRowInsert(IntrospectedTable introspectedTable) {
        // multi row inserts work if we don't expect generated keys, or of the generated keys are
        // JDBC standard.
        GeneratedKey gk = introspectedTable.getGeneratedKey();
        return gk == null || gk.isJdbcStandard();
    }

    public static boolean generateMultipleRowInsertHelper(IntrospectedTable introspectedTable) {
        // if the generated keys are JDBC standard, then we need a helper method
        // if no generated keys, or not JDBC, then we can't retrieve them anyway
        GeneratedKey gk = introspectedTable.getGeneratedKey();
        return gk != null && gk.isJdbcStandard();
    }

    public static boolean generateSelectByPrimaryKey(IntrospectedTable introspectedTable) {
        return introspectedTable.hasPrimaryKeyColumns()
                && (introspectedTable.hasBaseColumns() || introspectedTable
                        .hasBLOBColumns());
    }

    public static boolean generateUpdateByPrimaryKey(IntrospectedTable introspectedTable) {
        if (ListUtilities.removeGeneratedAlwaysColumns(introspectedTable.getNonPrimaryKeyColumns()).isEmpty()) {
            return false;
        }

        return introspectedTable.hasPrimaryKeyColumns()
                && (introspectedTable.hasBLOBColumns() || introspectedTable
                        .hasBaseColumns());
    }
}
