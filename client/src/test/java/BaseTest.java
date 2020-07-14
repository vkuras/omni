import annotations.TestData;
import base.AbstractBaseTest;
import org.junit.Assert;
import org.testng.annotations.Test;
import testdata.CarDTO;

import static constants.TestDataTypeConstants.BMW;

public class BaseTest extends AbstractBaseTest {
    @Test
    @TestData(testDataType = BMW)
    public void generationWhenEmptyQueueTest(){
        CarDTO carDTO=getTestData(BMW);
        Assert.assertEquals("BMW",carDTO.getBrand());
    }

    @Test
    @TestData(testDataType = BMW)
    @TestData(testDataType = BMW)
    public void generationWhenEmptyQueueDoubleAnnotationTest(){
        CarDTO carDTO=getTestData(BMW);
        Assert.assertEquals("BMW",carDTO.getBrand());
        carDTO=getTestData(BMW);
        Assert.assertEquals("BMW",carDTO.getBrand());
    }

    @Test(expectedExceptions = AssertionError.class)
    @TestData(testDataType = BMW)
    public void askForTooManyObjectsTest(){
        CarDTO carDTO=getTestData(BMW);
        carDTO=getTestData(BMW);

    }

}
