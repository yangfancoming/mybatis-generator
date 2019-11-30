
package mbg.test.mb3.dsql.v2.miscellaneous;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;

import mbg.test.mb3.generated.dsql.v2.miscellaneous.mapper.GeneratedalwaystestnoupdatesMapper;
import mbg.test.mb3.generated.dsql.v2.miscellaneous.model.Generatedalwaystestnoupdates;

public class GeneratedAlwaysNoUpdatesTest extends AbstractAnnotatedMiscellaneousTest {

    @Test
    public void testInsert() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            GeneratedalwaystestnoupdatesMapper mapper = sqlSession.getMapper(GeneratedalwaystestnoupdatesMapper.class);
            
            Generatedalwaystestnoupdates gaTest = new Generatedalwaystestnoupdates();
            gaTest.setId(1);
            gaTest.setIdPlus1(55);
            gaTest.setIdPlus2(66);
            int rows = mapper.insert(gaTest);
            assertEquals(1, rows);
            
            List<Generatedalwaystestnoupdates> returnedRecords = mapper.select(SelectDSLCompleter.allRows());
            assertEquals(1, returnedRecords.size());
            
            Generatedalwaystestnoupdates returnedRecord = returnedRecords.get(0);
            assertEquals(1, returnedRecord.getId().intValue());
            assertEquals(2, returnedRecord.getIdPlus1().intValue());
            assertEquals(3, returnedRecord.getIdPlus2().intValue());
        }
    }

    @Test
    public void testInsertSelective() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            GeneratedalwaystestnoupdatesMapper mapper = sqlSession.getMapper(GeneratedalwaystestnoupdatesMapper.class);
            
            Generatedalwaystestnoupdates gaTest = new Generatedalwaystestnoupdates();
            gaTest.setId(1);
            int rows = mapper.insertSelective(gaTest);
            assertEquals(1, rows);
            
            List<Generatedalwaystestnoupdates> returnedRecords = mapper.select(SelectDSLCompleter.allRows());
            assertEquals(1, returnedRecords.size());
            
            Generatedalwaystestnoupdates returnedRecord = returnedRecords.get(0);
            assertEquals(1, returnedRecord.getId().intValue());
            assertEquals(2, returnedRecord.getIdPlus1().intValue());
            assertEquals(3, returnedRecord.getIdPlus2().intValue());
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
