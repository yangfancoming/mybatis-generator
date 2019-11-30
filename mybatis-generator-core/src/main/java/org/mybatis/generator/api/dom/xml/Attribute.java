
package org.mybatis.generator.api.dom.xml;

import java.util.Objects;

public class Attribute {

    private String name;

    private String value;

    public Attribute(String name, String value) {
        this.name = Objects.requireNonNull(name);
        this.value = Objects.requireNonNull(value);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
