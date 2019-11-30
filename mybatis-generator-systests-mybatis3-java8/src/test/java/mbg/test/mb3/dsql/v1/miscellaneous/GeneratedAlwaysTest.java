
package mbg.test.mb3.dsql.v1.miscellaneous;

import static mbg.test.mb3.generated.dsql.v1.miscellaneous.mapper.GeneratedalwaystestDynamicSqlSupport.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import mbg.test.common.util.TestUtilities;
import mbg.test.mb3.generated.dsql.v1.miscellaneous.mapper.GeneratedalwaystestMapper;
import mbg.test.mb3.generated.dsql.v1.miscellaneous.model.Generatedalwaystest;

public class GeneratedAlwaysTest extends AbstractAnnotatedMiscellaneousTest {

    @Test
    public void testInsert() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            GeneratedalwaystestMapper mapper = sqlSession.getMapper(GeneratedalwaystestMapper.class);
            
            Generatedalwaystest gaTest = new Generatedalwaystest();
            gaTest.setId(1);
            gaTest.setName("fred");
            gaTest.setIdPlus1(55);
            gaTest.setIdPlus2(66);
            gaTest.setBlob1(TestUtilities.generateRandomBlob());
            int rows = mapper.insert(gaTest);
            assertEquals(1, rows);
            
            List<Generatedalwaystest> returnedRecords = mapper.selectByExample()
                    .build()
                    .execute();
            assertEquals(1, returnedRecords.size());
            
            Generatedalwaystest returnedRecord = returnedRecords.get(0);
            assertEquals(1, returnedRecord.getId().intValue());
            assertEquals(2, returnedRecord.getIdPlus1().intValue());
            assertEquals(3, returnedRecord.getIdPlus2().intValue());
            assertEquals("fred", returnedRecord.getName());
            assertTrue(TestUtilities.blobsAreEqual(gaTest.getBlob1(), returnedRecord.getBlob1()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsertSelective() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            GeneratedalwaystestMapper mapper = sqlSession.getMapper(GeneratedalwaystestMapper.class);
            
            Generatedalwaystest gaTest = new Generatedalwaystest();
            gaTest.setId(1);
            gaTest.setName("fred");
            gaTest.setIdPlus1(55);
            gaTest.setIdPlus2(66);
            int rows = mapper.insert(gaTest);
            assertEquals(1, rows);
            
            List<Generatedalwaystest> returnedRecords = mapper.selectByExample()
                    .build()
                    .execute();
            assertEquals(1, returnedRecords.size());
            
            Generatedalwaystest returnedRecord = returnedRecords.get(0);
            assertEquals(1, returnedRecord.getId().intValue());
            assertEquals(2, returnedRecord.getIdPlus1().intValue());
            assertEquals(3, returnedRecord.getIdPlus2().intValue());
            assertEquals("fred", returnedRecord.getName());
            assertNull(returnedRecord.getBlob1());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateByExample() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            GeneratedalwaystestMapper mapper = sqlSession.getMapper(GeneratedalwaystestMapper.class);
            
            Generatedalwaystest gaTest = new Generatedalwaystest();
            gaTest.setId(1);
            gaTest.setName("fred");
            gaTest.setIdPlus1(55); // should be ignored
            gaTest.setIdPlus2(66); // should be ignored
            gaTest.setBlob1(TestUtilities.generateRandomBlob());
            int rows = mapper.insert(gaTest);
            assertEquals(1, rows);
            
            gaTest.setName("barney");
            gaTest.setIdPlus1(77); // should be ignored
            gaTest.setIdPlus2(88); // should be ignored
            gaTest.setBlob1(TestUtilities.generateRandomBlob());
            
            rows = mapper.updateByExample(gaTest)
                    .where(idPlus1, isEqualTo(2))
                    .and(idPlus2, isEqualTo(3))
                    .build()
                    .execute();
            assertEquals(1, rows);
            
            List<Generatedalwaystest> returnedRecords = mapper.selectByExample()
                    .where(idPlus1, isEqualTo(2))
                    .and(idPlus2, isEqualTo(3))
                    .build()
                    .execute();
            assertEquals(1, returnedRecords.size());
            
            Generatedalwaystest returnedRecord = returnedRecords.get(0);
            assertEquals(1, returnedRecord.getId().intValue());
            assertEquals(2, returnedRecord.getIdPlus1().intValue());
            assertEquals(3, returnedRecord.getIdPlus2().intValue());
            assertEquals("barney", returnedRecord.getName());
            // should not have update the BLOB in regular update by primary key
            assertTrue(TestUtilities.blobsAreEqual(gaTest.getBlob1(), returnedRecord.getBlob1()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateByExampleSelective() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            GeneratedalwaystestMapper mapper = sqlSession.getMapper(GeneratedalwaystestMapper.class);
            
            Generatedalwaystest gaTest = new Generatedalwaystest();
            gaTest.setId(1);
            gaTest.setName("fred");
            gaTest.setIdPlus1(55); // should be ignored
            gaTest.setIdPlus2(66); // should be ignored
            gaTest.setBlob1(TestUtilities.generateRandomBlob());
            int rows = mapper.insert(gaTest);
            assertEquals(1, rows);
            
            gaTest.setName(null);
            gaTest.setIdPlus1(77); // should be ignored
            gaTest.setIdPlus2(88); // should be ignored
            gaTest.setBlob1(TestUtilities.generateRandomBlob());

            rows = mapper.updateByExampleSelective(gaTest)
                    .where(idPlus1, isEqualTo(2))
                    .and(idPlus2, isEqualTo(3))
                    .build()
                    .execute();
            assertEquals(1, rows);
            
            List<Generatedalwaystest> returnedRecords = mapper.selectByExample()
                    .where(idPlus1, isEqualTo(2))
                    .and(idPlus2, isEqualTo(3))
                    .build()
                    .execute();
            assertEquals(1, returnedRecords.size());
            
            Generatedalwaystest returnedRecord = returnedRecords.get(0);
            assertEquals(1, returnedRecord.getId().intValue());
            assertEquals(2, returnedRecord.getIdPlus1().intValue());
            assertEquals(3, returnedRecord.getIdPlus2().intValue());
            assertEquals("fred", returnedRecord.getName());
            assertTrue(TestUtilities.blobsAreEqual(gaTest.getBlob1(), returnedRecord.getBlob1()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateByExampleWithBlobs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            GeneratedalwaystestMapper mapper = sqlSession.getMapper(GeneratedalwaystestMapper.class);
            
            Generatedalwaystest gaTest = new Generatedalwaystest();
            gaTest.setId(1);
            gaTest.setName("fred");
            gaTest.setIdPlus1(55); // should be ignored
            gaTest.setIdPlus2(66); // should be ignored
            gaTest.setBlob1(TestUtilities.generateRandomBlob());
            int rows = mapper.insert(gaTest);
            assertEquals(1, rows);
            
            gaTest.setName("barney");
            gaTest.setIdPlus1(77); // should be ignored
            gaTest.setIdPlus2(88); // should be ignored
            gaTest.setBlob1(TestUtilities.generateRandomBlob());

            rows = mapper.updateByExample(gaTest)
                    .where(idPlus1, isEqualTo(2))
                    .and(idPlus2, isEqualTo(3))
                    .build()
                    .execute();
            assertEquals(1, rows);
            
            List<Generatedalwaystest> returnedRecords = mapper.selectByExample()
                    .where(idPlus1, isEqualTo(2))
                    .and(idPlus2, isEqualTo(3))
                    .build()
                    .execute();
            assertEquals(1, returnedRecords.size());
            
            Generatedalwaystest returnedRecord = returnedRecords.get(0);
            assertEquals(1, returnedRecord.getId().intValue());
            assertEquals(2, returnedRecord.getIdPlus1().intValue());
            assertEquals(3, returnedRecord.getIdPlus2().intValue());
            assertEquals("barney", returnedRecord.getName());
            assertTrue(TestUtilities.blobsAreEqual(gaTest.getBlob1(), returnedRecord.getBlob1()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateByPrimaryKey() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            GeneratedalwaystestMapper mapper = sqlSession.getMapper(GeneratedalwaystestMapper.class);
            
            Generatedalwaystest gaTest = new Generatedalwaystest();
            gaTest.setId(1);
            gaTest.setName("fred");
            gaTest.setIdPlus1(55); // should be ignored
            gaTest.setIdPlus2(66); // should be ignored
            gaTest.setBlob1(TestUtilities.generateRandomBlob());
            int rows = mapper.insert(gaTest);
            assertEquals(1, rows);
            
            gaTest.setName("barney");
            gaTest.setIdPlus1(77); // should be ignored
            gaTest.setIdPlus2(88); // should be ignored
            gaTest.setBlob1(TestUtilities.generateRandomBlob());
            rows = mapper.updateByPrimaryKey(gaTest);
            assertEquals(1, rows);
            
            List<Generatedalwaystest> returnedRecords = mapper.selectByExample()
                    .build()
                    .execute();
            assertEquals(1, returnedRecords.size());
            
            Generatedalwaystest returnedRecord = returnedRecords.get(0);
            assertEquals(1, returnedRecord.getId().intValue());
            assertEquals(2, returnedRecord.getIdPlus1().intValue());
            assertEquals(3, returnedRecord.getIdPlus2().intValue());
            assertEquals("barney", returnedRecord.getName());
            assertTrue(TestUtilities.blobsAreEqual(gaTest.getBlob1(), returnedRecord.getBlob1()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateByPrimaryKeySelective() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            GeneratedalwaystestMapper mapper = sqlSession.getMapper(GeneratedalwaystestMapper.class);
            
            Generatedalwaystest gaTest = new Generatedalwaystest();
            gaTest.setId(1);
            gaTest.setName("fred");
            gaTest.setIdPlus1(55); // should be ignored
            gaTest.setIdPlus2(66); // should be ignored
            gaTest.setBlob1(TestUtilities.generateRandomBlob());
            int rows = mapper.insert(gaTest);
            assertEquals(1, rows);
            
            gaTest.setName(null);
            gaTest.setIdPlus1(77); // should be ignored
            gaTest.setIdPlus2(88); // should be ignored
            gaTest.setBlob1(TestUtilities.generateRandomBlob());
            rows = mapper.updateByPrimaryKeySelective(gaTest);
            assertEquals(1, rows);
            
            List<Generatedalwaystest> returnedRecords = mapper.selectByExample()
                    .build()
                    .execute();
            assertEquals(1, returnedRecords.size());
            
            Generatedalwaystest returnedRecord = returnedRecords.get(0);
            assertEquals(1, returnedRecord.getId().intValue());
            assertEquals(2, returnedRecord.getIdPlus1().intValue());
            assertEquals(3, returnedRecord.getIdPlus2().intValue());
            assertEquals("fred", returnedRecord.getName());
            assertTrue(TestUtilities.blobsAreEqual(gaTest.getBlob1(), returnedRecord.getBlob1()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateByPrimaryKeyWithBlobs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            GeneratedalwaystestMapper mapper = sqlSession.getMapper(GeneratedalwaystestMapper.class);
            
            Generatedalwaystest gaTest = new Generatedalwaystest();
            gaTest.setId(1);
            gaTest.setName("fred");
            gaTest.setIdPlus1(55); // should be ignored
            gaTest.setIdPlus2(66); // should be ignored
            gaTest.setBlob1(TestUtilities.generateRandomBlob());
            int rows = mapper.insert(gaTest);
            assertEquals(1, rows);
            
            gaTest.setName("barney");
            gaTest.setIdPlus1(77); // should be ignored
            gaTest.setIdPlus2(88); // should be ignored
            gaTest.setBlob1(TestUtilities.generateRandomBlob());
            rows = mapper.updateByPrimaryKey(gaTest);
            assertEquals(1, rows);
            
            List<Generatedalwaystest> returnedRecords = mapper.selectByExample()
                    .build()
                    .execute();
            assertEquals(1, returnedRecords.size());
            
            Generatedalwaystest returnedRecord = returnedRecords.get(0);
            assertEquals(1, returnedRecord.getId().intValue());
            assertEquals(2, returnedRecord.getIdPlus1().intValue());
            assertEquals(3, returnedRecord.getIdPlus2().intValue());
            assertEquals("barney", returnedRecord.getName());
            assertTrue(TestUtilities.blobsAreEqual(gaTest.getBlob1(), returnedRecord.getBlob1()));
        } finally {
            sqlSession.close();
        }
    }
}
