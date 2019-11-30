
package org.mybatis.generator.api.dom.java;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mybatis.generator.api.dom.java.render.TypeParameterRenderer;

public class TypeParameterTest {

    @Test
    public void testConstructor() {

        TypeParameter typeParameter = new TypeParameter("T");
        assertNotNull(typeParameter);
        assertEquals("T", typeParameter.getName());
        assertNotNull(typeParameter.getExtendsTypes());
        assertEquals(0, typeParameter.getExtendsTypes().size());
    }

    @Test
    public void testConstructorWIthExtends() {

        FullyQualifiedJavaType list = FullyQualifiedJavaType.getNewListInstance();
        FullyQualifiedJavaType compare = new FullyQualifiedJavaType("java.util.Comparator");

        TypeParameter typeParameter = new TypeParameter("T", Arrays.asList(list, compare));
        assertNotNull(typeParameter);
        assertEquals("T", typeParameter.getName());
        assertNotNull(typeParameter.getExtendsTypes());
        assertEquals(2, typeParameter.getExtendsTypes().size());
    }

    @Test
    public void testGetFormattedContent() {
        TypeParameterRenderer renderer = new TypeParameterRenderer();

        FullyQualifiedJavaType list = FullyQualifiedJavaType.getNewListInstance();
        FullyQualifiedJavaType compare = new FullyQualifiedJavaType("java.util.Comparator");

        TypeParameter typeParameter = new TypeParameter("T", Arrays.asList(list, compare));
        assertNotNull(typeParameter);
        assertEquals("T extends List & Comparator", renderer.render(typeParameter, null));

        TopLevelClass compilationUnit = new TopLevelClass("java.util.Test");
        assertEquals("T extends List & Comparator", renderer.render(typeParameter, compilationUnit));

        TopLevelClass compilationUnit2 = new TopLevelClass("java.lang.Test");
        assertEquals("T extends java.util.List & java.util.Comparator", renderer.render(typeParameter, compilationUnit2));
    }

    @Test
    public void testToString() {

        FullyQualifiedJavaType list = FullyQualifiedJavaType.getNewListInstance();
        FullyQualifiedJavaType compare = new FullyQualifiedJavaType("java.util.Comparator");

        TypeParameter typeParameter = new TypeParameter("T", Arrays.asList(list, compare));
        assertNotNull(typeParameter);
        assertEquals("T extends List & Comparator", typeParameter.toString());
    }
}
