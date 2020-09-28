package app.base;

import app.constants.TestDataTypeConstants;
import common.dto.testdataprovider.OmniCreateDTO;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.DataTypesApi;
import org.openapitools.api.OmniApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
public abstract class AbstractBaseGenerator {
    @Autowired
    private OmniApiClient omniApiClient;
    @Autowired
    private DataTypesApi dataTypesApi;
    @Value("${max_attempts_fill_queue}")
    int maxTries;
    /**
     * generates test data
     *
     * @param testDataTypeConstants
     * @return any object. This will be saved as omni
     */
    public abstract Object generate(TestDataTypeConstants testDataTypeConstants);

    private void saveOmni(Object object, TestDataTypeConstants testDataTypeConstants) {
        OmniCreateDTO omniCreateDTO = new OmniCreateDTO();
        omniCreateDTO.setOmni(object);
        omniCreateDTO.setDataType(testDataTypeConstants.getTestDataType());
        omniApiClient.createOmni(omniCreateDTO);

    }

    public void fillQueue(TestDataTypeConstants testDataTypeConstants, int amount) {
        log.info("Getting queue Size");
        AtomicInteger exceptionCount= new AtomicInteger();
        int filling;
        while ((filling=dataTypesApi.getDataType(testDataTypeConstants.getTestDataType()).getBody().getAmountOfOmnis()) < amount) {
            IntStream.range(filling, amount).forEach(index -> {
                try {
                    Object object = generate(testDataTypeConstants);
                    saveOmni(object, testDataTypeConstants);
                }
                catch (FeignException e){
                    exceptionCount.getAndIncrement();
                    log.warn(e.toString());
                    if(exceptionCount.get()>=maxTries){
                        log.error("Reached max tries to create '%s'. Tried %d times",testDataTypeConstants.getTestDataType(),exceptionCount.get());
                        throw e;
                    }
                }
            });

        }


    }
}
