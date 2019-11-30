
package mbg.test.mb3.annotated.flat;

import org.junit.jupiter.api.BeforeEach;

import mbg.test.mb3.AbstractTest;
import mbg.test.mb3.generated.annotated.flat.mapper.AwfulTableMapper;
import mbg.test.mb3.generated.annotated.flat.mapper.FieldsblobsMapper;
import mbg.test.mb3.generated.annotated.flat.mapper.PkblobsMapper;
import mbg.test.mb3.generated.annotated.flat.mapper.PkfieldsMapper;
import mbg.test.mb3.generated.annotated.flat.mapper.PkfieldsblobsMapper;
import mbg.test.mb3.generated.annotated.flat.mapper.PkonlyMapper;
import mbg.test.mb3.generated.annotated.flat.mapper.subpackage.FieldsonlyMapper;

public abstract class AbstractAnnotatedFlatTest extends AbstractTest {

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
