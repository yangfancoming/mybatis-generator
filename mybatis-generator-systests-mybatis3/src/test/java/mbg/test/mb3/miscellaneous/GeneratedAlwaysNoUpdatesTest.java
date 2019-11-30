
package mbg.test.mb3.miscellaneous;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import mbg.test.mb3.generated.miscellaneous.mapper.GeneratedalwaystestnoupdatesMapper;
import mbg.test.mb3.generated.miscellaneous.model.Generatedalwaystestnoupdates;

public class GeneratedAlwaysNoUpdatesTest extends AbstractMiscellaneousTest {

    @Test
    public void testInsert() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            GeneratedalwaystestnoupdatesMapper mapper = sqlSession.getMapper(GeneratedalwaystestnoupdatesMapper.class);
            
            Generatedalwaystestnoupdates gaTest = new Generatedalwaystestnoupdates();
            gaTest.setId(1);
            gaTest.setIdPlus1(55);
            gaTest.setIdPlus2(66);
            int rows = mapper.insert(gaTest);
            assertEquals(1, rows);
            
            List<Generatedalwaystestnoupdates> returnedRecords = mapper.selectByExample(null);
            assertEquals(1, returnedRecords.size());
            
            Generatedalwaystestnoupdates returnedRecord = returnedRecords.get(0);
            assertEquals(1, returnedRecord.getId().intValue());
            assertEquals(2, returnedRecord.getIdPlus1().intValue());
            assertEquals(3, returnedRecord.getIdPlus2().intValue());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsertSelective() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            GeneratedalwaystestnoupdatesMapper mapper = sqlSession.getMapper(GeneratedalwaystestnoupdatesMapper.class);
            
            Generatedalwaystestnoupdates gaTest = new Generatedalwaystestnoupdates();
            gaTest.setId(1);
            int rows = mapper.insertSelective(gaTest);
            assertEquals(1, rows);
            
            List<Generatedalwaystestnoupdates> returnedRecords = mapper.selectByExample(null);
            assertEquals(1, returnedRecords.size());
            
            Generatedalwaystestnoupdates returnedRecord = returnedRecords.get(0);
            assertEquals(1, returnedRecord.getId().intValue());
            assertEquals(2, returnedRecord.getIdPlus1().intValue());
            assertEquals(3, returnedRecord.getIdPlus2().intValue());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testThatUpdatesByPrimaryKeyDidNotGetGenerated() {
        Method[] methods = GeneratedalwaystestnoupdatesMapper.class.getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("updateByPrimaryKey")) {
                fail("Method " + method.getName() + " should not be generated");
            }
        }
    }
}
