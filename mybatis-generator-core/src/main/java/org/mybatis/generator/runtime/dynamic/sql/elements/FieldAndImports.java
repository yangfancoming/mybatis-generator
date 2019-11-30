
package org.mybatis.generator.runtime.dynamic.sql.elements;

import java.util.HashSet;
import java.util.Set;

import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

public class FieldAndImports {

    private Field field;
    private Set<FullyQualifiedJavaType> imports;
    private Set<String> staticImports;
    
    private FieldAndImports(Builder builder) {
        field = builder.field;
        imports = builder.imports;
        staticImports = builder.staticImports;
    }
    
    public Field getField() {
        return field;
    }
    
    public Set<FullyQualifiedJavaType> getImports() {
        return imports;
    }
    
    public Set<String> getStaticImports() {
        return staticImports;
    }
    
    public static Builder withField(Field field) {
        return new Builder().withField(field);
    }
    
    public static class Builder {
        private Field field;
        private Set<FullyQualifiedJavaType> imports = new HashSet<>();
        private Set<String> staticImports = new HashSet<>();
        
        public Builder withField(Field field) {
            this.field = field;
            return this;
        }
        
        public Builder withImport(FullyQualifiedJavaType importedType) {
            this.imports.add(importedType);
            return this;
        }
        
        public Builder withImports(Set<FullyQualifiedJavaType> imports) {
            this.imports.addAll(imports);
            return this;
        }
        
        public Builder withStaticImport(String staticImport) {
            this.staticImports.add(staticImport);
            return this;
        }

        public Builder withStaticImports(Set<String> staticImports) {
            this.staticImports.addAll(staticImports);
            return this;
        }

        public FieldAndImports build() {
            return new FieldAndImports(this);
        }
    }
}
