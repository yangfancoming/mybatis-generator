
package org.mybatis.generator.config.xml;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.exception.XMLParserException;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ConfigurationParser {

    private List<String> warnings;
    private List<String> parseErrors;
    private Properties extraProperties;

    public ConfigurationParser(List<String> warnings) {
        this(null, warnings);
    }

    /**
     * This constructor accepts a properties object which may be used to specify an additional property set.
     * Typically this property set will be Ant or Maven properties specified in the build.xml file or the POM.
     * <p>If there are name collisions between the different property sets, they will be  resolved in this order:
     * <ol>
     *   <li>System properties take highest precedence</li>
     *   <li>Properties specified in the &lt;properties&gt; configuration element are next</li>
     *   <li>Properties specified in this "extra" property set are lowest precedence.</li>
     * </ol>
     * @param extraProperties an (optional) set of properties used to resolve property references in the configuration file
     * @param warnings any warnings are added to this array
     */
    public ConfigurationParser(Properties extraProperties, List<String> warnings) {
        super();
        this.extraProperties = extraProperties;
        this.warnings =(warnings == null) ?  new ArrayList<>() : warnings;
        parseErrors = new ArrayList<>();
    }

    public Configuration parseConfiguration(File inputFile) throws IOException, XMLParserException {
        FileReader fr = new FileReader(inputFile);
        return parseConfiguration(fr);
    }

    public Configuration parseConfiguration(Reader reader) throws IOException,XMLParserException {
        InputSource is = new InputSource(reader);
        return parseConfiguration(is);
    }

    public Configuration parseConfiguration(InputStream inputStream) throws IOException, XMLParserException {
        InputSource is = new InputSource(inputStream);
        return parseConfiguration(is);
    }

    private Configuration parseConfiguration(InputSource inputSource)throws IOException, XMLParserException {
        parseErrors.clear();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);

        try {
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            // dcoument 组装器
            DocumentBuilder builder = factory.newDocumentBuilder();
            //1 设置校验的dtd资源文件
            builder.setEntityResolver(new ParserEntityResolver());
            // 实例化一个错误处理类，处理的错误和警告信息会以list形式保存  //2 解析配置
            ParserErrorHandler handler = new ParserErrorHandler(warnings,  parseErrors);
            builder.setErrorHandler(handler);
            // 使用w3c 文档解析
            Document document = null;
            try {
                // 将内存文件转换为w3c文档对象
                document = builder.parse(inputSource);
            } catch (SAXParseException e) {
                // 抛除自定义xml转换错误异常
                throw new XMLParserException(parseErrors);
            } catch (SAXException e) {
                // 其他异常处理
                if (e.getException() == null) {
                    parseErrors.add(e.getMessage());
                } else {
                    parseErrors.add(e.getException().getMessage());
                }
            }

            if (document == null || !parseErrors.isEmpty()) {
                throw new XMLParserException(parseErrors);
            }
            // 返回的配置文件对象
            Configuration config;
            // 获取根节点
            Element rootNode = document.getDocumentElement();
            // 获取文档类型
            DocumentType docType = document.getDoctype();
            if (rootNode.getNodeType() == Node.ELEMENT_NODE  && docType.getPublicId().equals( XmlConstants.MYBATIS_GENERATOR_CONFIG_PUBLIC_ID)) {
                // 该DTD运行此分支，也就是mybatis处理分支
                config = parseMyBatisGeneratorConfiguration(rootNode);
            } else {
                throw new XMLParserException(getString("RuntimeError.5")); //$NON-NLS-1$   This is not a MyBatis Generator Configuration File
            }
            if (!parseErrors.isEmpty()) {
                throw new XMLParserException(parseErrors);
            }
            return config;
        } catch (ParserConfigurationException e) {
            parseErrors.add(e.getMessage());
            throw new XMLParserException(parseErrors);
        }
    }

    private Configuration parseMyBatisGeneratorConfiguration(Element rootNode)  throws XMLParserException {
        MyBatisGeneratorConfigurationParser parser = new MyBatisGeneratorConfigurationParser(extraProperties);
        return parser.parseConfiguration(rootNode);
    }
}
