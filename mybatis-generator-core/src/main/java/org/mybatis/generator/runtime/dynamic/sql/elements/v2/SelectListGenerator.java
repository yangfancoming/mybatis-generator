
package org.mybatis.generator.runtime.dynamic.sql.elements.v2;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.runtime.dynamic.sql.elements.FieldAndImports;
import org.mybatis.generator.runtime.dynamic.sql.elements.FragmentGenerator;

public class SelectListGenerator {

    private FragmentGenerator fragmentGenerator;
    private Context context;
    private IntrospectedTable introspectedTable;
    
    private SelectListGenerator(Builder builder) {
        this.fragmentGenerator = Objects.requireNonNull(builder.fragmentGenerator);
        this.context = Objects.requireNonNull(builder.context);
        this.introspectedTable = Objects.requireNonNull(builder.introspectedTable);
    }
    
    public FieldAndImports generateFieldAndImports() {
        Set<FullyQualifiedJavaType> imports = new HashSet<>();
        
        FullyQualifiedJavaType fieldType =
                new FullyQualifiedJavaType("org.mybatis.dynamic.sql.BasicColumn[]"); //$NON-NLS-1$
        imports.add(fieldType);
        Field field = new Field("selectList", fieldType); //$NON-NLS-1$
        field.setInitializationString("BasicColumn.columnList(" //$NON-NLS-1$
                + fragmentGenerator.getSelectList() + ")"); //$NON-NLS-1$
        context.getCommentGenerator().addFieldAnnotation(field, introspectedTable, imports);
        
        return FieldAndImports.withField(field)
                .withImports(imports)
                .build();
    }

    public boolean callPlugins(Field field, Interface interfaze) {
        return context.getPlugins().clientSelectListFieldGenerated(field, interfaze, introspectedTable);
    }
    
    public static class Builder {
        private FragmentGenerator fragmentGenerator;
        private Context context;
        private IntrospectedTable introspectedTable;
        
        public Builder withFragmentGenerator(FragmentGenerator fragmentGenerator) {
            this.fragmentGenerator = fragmentGenerator;
            return this;
        }
        
        public Builder withContext(Context context) {
            this.context = context;
            return this;
        }
        
        public Builder withIntrospectedTable(IntrospectedTable introspectedTable) {
            this.introspectedTable = introspectedTable;
            return this;
        }
        
        public SelectListGenerator build() {
            return new SelectListGenerator(this);
        }
    }
}