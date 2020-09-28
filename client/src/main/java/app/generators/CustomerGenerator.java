package app.generators;

import app.base.AbstractBaseGenerator;
import app.constants.TestDataTypeConstants;
import app.testdata.CustomerDTO;
import org.springframework.stereotype.Component;
@Component
public class CustomerGenerator extends AbstractBaseGenerator {
    @Override
    public Object generate(TestDataTypeConstants testDataTypeConstants){
        //Here you generate the data
        switch (testDataTypeConstants) {
            case PRIVATE_COSTUMER:
                return generateCustomer(false, true);
            case Merchant:
                return generateCustomer(true, false);
        }
        return null;
    }
    private CustomerDTO generateCustomer(boolean merchant, boolean privateCustomer){
        //here would be normally your api calls
        return CustomerDTO.builder()
                .email("nssfdjk@dknsdk.de")
                .firstName("sfcsd")
                .lastName("dsadd")
                .merchant(merchant)
                .privateCustomer(privateCustomer)
                .build();
    }
}
