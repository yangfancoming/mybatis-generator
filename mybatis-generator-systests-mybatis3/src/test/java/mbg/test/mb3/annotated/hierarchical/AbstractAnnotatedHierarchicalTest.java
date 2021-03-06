
package mbg.test.mb3.annotated.hierarchical;

import org.junit.jupiter.api.BeforeEach;

import mbg.test.mb3.AbstractTest;
import mbg.test.mb3.generated.annotated.hierarchical.mapper.AwfulTableMapper;
import mbg.test.mb3.generated.annotated.hierarchical.mapper.FieldsblobsMapper;
import mbg.test.mb3.generated.annotated.hierarchical.mapper.FieldsonlyMapper;
import mbg.test.mb3.generated.annotated.hierarchical.mapper.PkblobsMapper;
import mbg.test.mb3.generated.annotated.hierarchical.mapper.PkfieldsMapper;
import mbg.test.mb3.generated.annotated.hierarchical.mapper.PkfieldsblobsMapper;
import mbg.test.mb3.generated.annotated.hierarchical.mapper.PkonlyMapper;

public abstract class AbstractAnnotatedHierarchicalTest extends AbstractTest {

    @BeforeEach
    @Override
    public void setUp() throws Exception {
        super.setUp();
        sqlSessionFactory.getConfiguration().addMapper(AwfulTableMapper.class);
        sqlSessionFactory.getConfiguration().addMapper(FieldsblobsMapper.class);
        sqlSessionFactory.getConfiguration().addMapper(FieldsonlyMapper.class);
        sqlSessionFactory.getConfiguration().addMapper(PkblobsMapper.class);
        sqlSessionFactory.getConfiguration().addMapper(PkfieldsblobsMapper.class);
        sqlSessionFactory.getConfiguration().addMapper(PkfieldsMapper.class);
        sqlSessionFactory.getConfiguration().addMapper(PkonlyMapper.class);
    }

    @Override
    public String getMyBatisConfigFile() {
        return "mbg/test/mb3/annotated/MapperConfig.xml";
    }
}
