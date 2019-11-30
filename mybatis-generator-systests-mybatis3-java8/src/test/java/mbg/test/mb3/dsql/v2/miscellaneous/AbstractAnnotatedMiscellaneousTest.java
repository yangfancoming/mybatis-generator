
package mbg.test.mb3.dsql.v2.miscellaneous;

import static mbg.test.common.util.TestUtilities.createDatabase;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.jupiter.api.BeforeEach;

import mbg.test.mb3.generated.dsql.v2.miscellaneous.mapper.EnumtestMapper;
import mbg.test.mb3.generated.dsql.v2.miscellaneous.mapper.GeneratedalwaystestMapper;
import mbg.test.mb3.generated.dsql.v2.miscellaneous.mapper.GeneratedalwaystestnoupdatesMapper;
import mbg.test.mb3.generated.dsql.v2.miscellaneous.mapper.MyObjectMapper;
import mbg.test.mb3.generated.dsql.v2.miscellaneous.mapper.RegexrenameMapper;

public abstract class AbstractAnnotatedMiscellaneousTest {

    private static final String JDBC_URL = "jdbc:hsqldb:mem:aname";
    private static final String JDBC_DRIVER = "org.hsqldb.jdbcDriver"; 

    protected SqlSessionFactory sqlSessionFactory;

    @BeforeEach
    public void setUp() throws Exception {
        createDatabase();

        UnpooledDataSource ds = new UnpooledDataSource(JDBC_DRIVER, JDBC_URL, "sa", "");
        Environment environment = new Environment("test", new JdbcTransactionFactory(), ds);
        Configuration config = new Configuration(environment);
        config.addMapper(EnumtestMapper.class);
        config.addMapper(GeneratedalwaystestMapper.class);
        config.addMapper(GeneratedalwaystestnoupdatesMapper.class);
        config.addMapper(MyObjectMapper.class);
        config.addMapper(RegexrenameMapper.class);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(config);
    }
}
