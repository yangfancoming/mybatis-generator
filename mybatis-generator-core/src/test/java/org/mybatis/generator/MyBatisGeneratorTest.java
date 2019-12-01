
package org.mybatis.generator;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.ConnectionFactoryConfiguration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

public class MyBatisGeneratorTest {

    // mybatis 自动生成测试类
    @Test
    public void tes111t() throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {
        List<String> warnings = new ArrayList<>();
//        File configFile = new File("scripts/goatConfig.xml");
        File configFile = new File(getClass().getClassLoader().getResource("scripts/goatConfig.xml").getFile());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

    // mybatis 自动生成测试类
    @Test
    public void mybatisGeneratorTest() {
        List<String> warnings = new ArrayList<>();
        String genCfg = "scripts/goatConfig.xml";
        File configFile = new File(getClass().getClassLoader().getResource(genCfg).getFile());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = null;
        try {
            config = cp.parseConfiguration(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLParserException e) {
            e.printStackTrace();
        }
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = null;
        try {
            myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        try {
            myBatisGenerator.generate(null);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




    @Test
    public void test() throws Exception {
        List<String> warnings = new ArrayList<>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(this.getClass().getClassLoader().getResourceAsStream("scripts/goatConfig.xml"));
        DefaultShellCallback shellCallback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
        myBatisGenerator.generate(null, null, null, false);
    }

    @Test
    public void testGenerateMyBatis3WithInvalidConfig() throws Exception {
        List<String> warnings = new ArrayList<>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(this.getClass().getClassLoader().getResourceAsStream("generatorConfigMyBatis3_badConfig.xml"));
        DefaultShellCallback shellCallback = new DefaultShellCallback(true);

        InvalidConfigurationException e = 
                assertThrows(InvalidConfigurationException.class, () -> {
                    MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
                    myBatisGenerator.generate(null, null, null, false);
                });

        assertEquals(2, e.getErrors().size());
    }

    @Test
    public void testGenerateInvalidConfigWithNoConnectionSources() throws Exception {
        List<String> warnings = new ArrayList<>();
        Configuration config = new Configuration();
        Context context = new Context(ModelType.HIERARCHICAL);
        context.setId("MyContext");
        config.addContext(context);

        DefaultShellCallback shellCallback = new DefaultShellCallback(true);

        InvalidConfigurationException e =
                assertThrows(InvalidConfigurationException.class, () -> {
                    MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
                    myBatisGenerator.generate(null, null, null, false);
                });
        assertEquals(3, e.getErrors().size());
    }

    @Test
    public void testGenerateInvalidConfigWithTwoConnectionSources() throws Exception {
        List<String> warnings = new ArrayList<>();
        Configuration config = new Configuration();
        Context context = new Context(ModelType.HIERARCHICAL);
        context.setId("MyContext");
        context.setConnectionFactoryConfiguration(new ConnectionFactoryConfiguration());
        context.setJdbcConnectionConfiguration(new JDBCConnectionConfiguration());
        config.addContext(context);

        DefaultShellCallback shellCallback = new DefaultShellCallback(true);

        InvalidConfigurationException e =
                assertThrows(InvalidConfigurationException.class, () -> {
                    MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
                    myBatisGenerator.generate(null, null, null, false);
                });
        assertEquals(3, e.getErrors().size());
    }
}
