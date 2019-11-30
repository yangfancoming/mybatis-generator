
package mbg.test.mb3.annotated.miscellaneous;

import org.junit.jupiter.api.BeforeEach;

import mbg.test.mb3.AbstractTest;
import mbg.test.mb3.generated.annotated.miscellaneous.mapper.EnumtestMapper;
import mbg.test.mb3.generated.annotated.miscellaneous.mapper.GeneratedalwaystestMapper;
import mbg.test.mb3.generated.annotated.miscellaneous.mapper.GeneratedalwaystestnoupdatesMapper;
import mbg.test.mb3.generated.annotated.miscellaneous.mapper.MyObjectMapper;
import mbg.test.mb3.generated.annotated.miscellaneous.mapper.RegexrenameMapper;

public abstract class AbstractAnnotatedMiscellaneousTest extends AbstractTest {

    @BeforeEach
    @Override
    public void setUp() throws Exception {
        super.setUp();
        sqlSessionFactory.getConfiguration().addMapper(EnumtestMapper.class);
        sqlSessionFactory.getConfiguration().addMapper(MyObjectMapper.class);
        sqlSessionFactory.getConfiguration().addMapper(RegexrenameMapper.class);
        sqlSessionFactory.getConfiguration().addMapper(GeneratedalwaystestMapper.class);
        sqlSessionFactory.getConfiguration().addMapper(GeneratedalwaystestnoupdatesMapper.class);
    }

    @Override
    public String getMyBatisConfigFile() {
        return "mbg/test/mb3/annotated/MapperConfig.xml";
    }
}
