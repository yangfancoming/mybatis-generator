
package mbg.test.mb3.dsql.v2;

import static mbg.test.common.util.TestUtilities.createDatabase;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.jupiter.api.BeforeEach;

import mbg.test.mb3.generated.dsql.v2.mapper.AwfulTableMapper;
import mbg.test.mb3.generated.dsql.v2.mapper.FieldsblobsMapper;
import mbg.test.mb3.generated.dsql.v2.mapper.FieldsonlyMapper;
import mbg.test.mb3.generated.dsql.v2.mapper.PkblobsMapper;
import mbg.test.mb3.generated.dsql.v2.mapper.PkfieldsMapper;
import mbg.test.mb3.generated.dsql.v2.mapper.PkfieldsblobsMapper;
import mbg.test.mb3.generated.dsql.v2.mapper.PkonlyMapper;
import mbg.test.mb3.generated.dsql.v2.mapper.mbgtest.IdMapper;
import mbg.test.mb3.generated.dsql.v2.mapper.mbgtest.TranslationMapper;

/**
 * @author Jeff Butler
 * 
 */
public abstract class AbstractTest {

    private static final String JDBC_URL = "jdbc:hsqldb:mem:aname";
    private static final String JDBC_DRIVER = "org.hsqldb.jdbcDriver"; 

    protected SqlSessionFactory sqlSessionFactory;

    @BeforeEach
    public void setUp() throws Exception {
        createDatabase();

        UnpooledDataSource ds = new UnpooledDataSource(JDBC_DRIVER, JDBC_URL, "sa", "");
        Environment environment = new Environment("test", new JdbcTransactionFactory(), ds);
        Configuration config = new Configuration(environment);
        config.addMapper(AwfulTableMapper.class);
        config.addMapper(FieldsblobsMapper.class);
        config.addMapper(FieldsonlyMapper.class);
        config.addMapper(PkblobsMapper.class);
        config.addMapper(PkfieldsblobsMapper.class);
        config.addMapper(PkfieldsMapper.class);
        config.addMapper(PkonlyMapper.class);
        config.addMapper(TranslationMapper.class);
        config.addMapper(IdMapper.class);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(config);
    }
}
