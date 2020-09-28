import app.annotations.TestData;
import app.base.AbstractBaseTest;
import app.testdata.CarDTO;
import app.testdata.CustomerDTO;
import org.junit.Assert;
import org.testng.annotations.Test;

import static app.constants.TestDataTypeConstants.BMW;
import static app.constants.TestDataTypeConstants.Merchant;

public class BaseTest extends AbstractBaseTest {
    @Test
    @TestData(testDataType = BMW)
    public void generationWhenEmptyQueueTest(){
        CarDTO carDTO=getTestData(BMW);
        Assert.assertEquals("BMW",carDTO.getBrand());
    }
    @Test
    @TestData(testDataType = Merchant)
    public void getFromQueueTest(){
        CustomerDTO customerDTO=getTestData(Merchant);
        Assert.assertTrue(customerDTO.isMerchant());
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
