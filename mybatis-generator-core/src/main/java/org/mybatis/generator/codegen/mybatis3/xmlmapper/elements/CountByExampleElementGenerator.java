
package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class CountByExampleElementGenerator extends AbstractXmlElementGenerator {

    public CountByExampleElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        String fqjt = introspectedTable.getExampleType();

        answer.addAttribute(new Attribute(
                "id", introspectedTable.getCountByExampleStatementId())); //$NON-NLS-1$
        answer.addAttribute(new Attribute("parameterType", fqjt)); //$NON-NLS-1$
        answer.addAttribute(new Attribute("resultType", "java.lang.Long")); //$NON-NLS-1$ //$NON-NLS-2$

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(getExampleIncludeElement());

        if (context.getPlugins().sqlMapCountByExampleElementGenerated(
                answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
