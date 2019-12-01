
package org.mybatis.generator;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XmlCodeGenerationTest {

    @ParameterizedTest
    @MethodSource("generateXmlFiles")
    public void testXmlParse(GeneratedXmlFile generatedXmlFile) {
        ByteArrayInputStream is = new ByteArrayInputStream(generatedXmlFile.getFormattedContent().getBytes());
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver(new TestEntityResolver());
            builder.setErrorHandler(new TestErrorHandler());
            builder.parse(is);
        } catch (Exception e) {
            fail("Generated XML File " + generatedXmlFile.getFileName() + " will not parse");
        }
    }

    public static List<GeneratedXmlFile> generateXmlFiles() throws Exception {
        List<GeneratedXmlFile> generatedFiles = new ArrayList<>();
        generatedFiles.addAll(generateXmlFilesMybatis());
        return generatedFiles;
    }

    private static List<GeneratedXmlFile> generateXmlFilesMybatis() throws Exception {
        JavaCodeGenerationTest.createDatabase();
//        return generateXmlFiles("/scripts/generatorConfig.xml");
        return generateXmlFiles("/scripts/goatConfig.xml");
    }

    private static List<GeneratedXmlFile> generateXmlFiles(String configFile) throws Exception {
        List<String> warnings = new ArrayList<>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(JavaCodeGenerationTest.class.getResourceAsStream(configFile));
        DefaultShellCallback shellCallback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
        myBatisGenerator.generate(null, null, null, false);
        return myBatisGenerator.getGeneratedXmlFiles();
    }

    public static class TestEntityResolver implements EntityResolver {

        @Override
        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            // just return an empty string.  this should stop the parser from trying to access the network
            return new InputSource(new ByteArrayInputStream("".getBytes()));
        }
    }

    public static class TestErrorHandler implements ErrorHandler {

        private List<String> errors = new ArrayList<>();
        private List<String> warnings = new ArrayList<>();

        @Override
        public void warning(SAXParseException exception) throws SAXException {
            warnings.add(exception.getMessage());
        }

        @Override
        public void error(SAXParseException exception) throws SAXException {
            errors.add(exception.getMessage());
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            errors.add(exception.getMessage());
        }

        public List<String> getErrors() {
            return errors;
        }

        public List<String> getWarnings() {
            return warnings;
        }
    }
}
