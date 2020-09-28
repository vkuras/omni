package app.generators;

import app.base.AbstractBaseGenerator;
import app.constants.TestDataTypeConstants;
import app.testdata.CarDTO;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CarGenerator extends AbstractBaseGenerator {
    @Override
    public Object generate(TestDataTypeConstants testDataTypeConstants){
        //Here you generate the data
        switch (testDataTypeConstants){
            case VW:
                return generateCar("VW");
            case Porsche:
                return generateCar("PORSCHE");
            case BMW:
               return generateCar("BMW");
        }
        return null;
    }
    private CarDTO generateCar(String brand){
        //here would be normally your api calls
        return (CarDTO.builder().brand(brand)
                .buildOn(LocalDate.now())
                .keys(5)
                .id(UUID.randomUUID()).build());
    }
}
