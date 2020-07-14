package constants;

import generators.CarGenerator;
import generators.CustomerGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import testdata.CarDTO;
import testdata.CustomerDTO;

/**
 * all test data types are listed here. It's used for saving and retrieving the Test data
 */
@Getter
@AllArgsConstructor
public enum TestDataTypeConstants {
    Porsche(CarDTO.class,"Porsche",CarGenerator.class),
    VW(CarDTO.class,"VW", CarGenerator.class),
    BMW(CarDTO.class,"BMW",CarGenerator.class),
    Merchant(CustomerDTO.class,"Merchant", CustomerGenerator.class),
    PRIVATE_COSTUMER(CustomerDTO.class,"Private Customer",CustomerGenerator.class);

    private Class omniClass;
    private String testDataType;
    private Class generator;


}
