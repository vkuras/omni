package base;

import common.dto.testdataprovider.OmniCreateDTO;
import constants.TestDataTypeConstants;
import org.openapitools.api.OmniApiClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public abstract class AbstractBaseGenerator {
    @Autowired
    private OmniApiClient omniApiClient;

    /**
     * generates test data
     *
     * @param testDataTypeConstants
     * @return any object. This will be saved as omni
     */
    public abstract Object generate(TestDataTypeConstants testDataTypeConstants);

    private void saveOmni(TestDataTypeConstants testDataTypeConstants) {
        OmniCreateDTO omniCreateDTO = new OmniCreateDTO();
        omniCreateDTO.setOmni(generate(testDataTypeConstants));

    }

    public List<Object> generate(TestDataTypeConstants testDataTypeConstants, int amount) {
        List<Object> generated = new ArrayList(amount);
        IntStream.range(0, amount).forEach(index -> {
            generated.add(generate(testDataTypeConstants));
        });
        return generated;
    }
}
