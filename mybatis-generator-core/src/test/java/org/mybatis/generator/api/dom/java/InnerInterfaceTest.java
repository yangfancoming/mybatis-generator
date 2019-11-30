
package org.mybatis.generator.api.dom.java;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InnerInterfaceTest {

    @Test
    public void testConstructor() {

        InnerInterface innerInterface = new InnerInterface("com.foo.InnerUserInterface");
        assertNotNull(innerInterface);
    }

    @Test
    public void testAddSuperInterface() {

        InnerInterface innerInterface = new InnerInterface("com.foo.InnerUserInterface");
        FullyQualifiedJavaType superInterType = new FullyQualifiedJavaType("com.foo.SuperUserInterface");
        innerInterface.addSuperInterface(superInterType);
        assertNotNull(innerInterface.getSuperInterfaceTypes());
        assertTrue(innerInterface.getSuperInterfaceTypes().contains(superInterType));
    }

    @Test
    public void testAddMethod() {

        InnerInterface interfaze = new InnerInterface("com.foo.UserInterface");
        Method method = new Method("foo");
        interfaze.addMethod(method);

        assertNotNull(interfaze.getMethods());
        assertEquals(1, interfaze.getMethods().size());
        assertSame(interfaze.getMethods().get(0), method);
    }

    @Test
    public void testGetType() {

        InnerInterface innerInterface = new InnerInterface("com.foo.InnerUserInterface");
        assertNotNull(innerInterface.getType());
        assertEquals("com.foo.InnerUserInterface", innerInterface.getType().getFullyQualifiedName());
    }

    @Test
    public void testAddInnerInterfaces() {

        InnerInterface interfaze = new InnerInterface("com.foo.UserInterface");
        InnerInterface innerInterfaze = new InnerInterface("com.foo.InnerUserInterface");

        interfaze.addInnerInterface(innerInterfaze);
        assertNotNull(interfaze.getInnerInterfaces());
        assertEquals(1, interfaze.getInnerInterfaces().size());
        assertSame(interfaze.getInnerInterfaces().get(0), innerInterfaze);
    }
}
