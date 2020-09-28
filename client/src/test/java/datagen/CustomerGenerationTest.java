package datagen;

import app.base.AbstractBaseTest;
import app.constants.TestDataTypeConstants;
import app.generators.CustomerGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CustomerGenerationTest extends AbstractBaseTest {
    @Autowired
    CustomerGenerator customerGenerator;

    @DataProvider(name="data")
    public Object[][] getAmountOfDatat(){
        return new Object[][]{{TestDataTypeConstants.PRIVATE_COSTUMER,20},{TestDataTypeConstants.Merchant,20}};
    }
    @Test(dataProvider = "data")
    public void generateTest(TestDataTypeConstants type,int amount){
        customerGenerator.fillQueue(type,amount);

    }
}
