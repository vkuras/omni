package base;

import annotations.TestData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.dto.testdataprovider.OmniDTO;
import config.TestConfig;
import constants.TestDataTypeConstants;
import feign.FeignException;
import org.openapitools.api.OmniApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.testng.FileAssert.fail;

//Enable us to use spring application config
@ContextConfiguration(classes = TestConfig.class)
public abstract class AbstractBaseTest extends AbstractTestNGSpringContextTests {
    @Autowired
    OmniApiClient omniApiClient;
    //Must be thread save, so concurrent tests will not overwrite each others test data
    private ThreadLocal<LinkedHashMap<TestDataTypeConstants, List>> testData = ThreadLocal.withInitial(LinkedHashMap::new);
    @Autowired
    private ObjectMapper mapper;

    @BeforeMethod(alwaysRun = true)
    public void prepareTestData(Method method) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        //Get all annotations of type Test Data
        TestData[] dataAnnotations = method.getDeclaredAnnotationsByType(TestData.class);
        for (TestData dataAnnotation : dataAnnotations) {
            //Creating a list for our TestDataType if not exist. Needs to be in if so multiple @TestData annotations don't fails
            createList(dataAnnotation, dataAnnotation.testDataType().getOmniClass());
            try {
                getOmnisFromQueue(dataAnnotation);

            }
            //Happens if queue is empty or server is down. Data is then generated by scratch
            catch (FeignException e) {
                AbstractBaseGenerator baseGenerator = (AbstractBaseGenerator) dataAnnotation.testDataType()
                        .getGenerator()
                        .getDeclaredConstructor()
                        .newInstance();
                List generated = baseGenerator.generate(dataAnnotation.testDataType(), dataAnnotation.amount());

                testData.get().get(dataAnnotation.testDataType()).addAll(generated);

            }
        }

    }

    private void getOmnisFromQueue(TestData dataAnnotation) {
        Class omniClasse = dataAnnotation.testDataType().getOmniClass();
        List<OmniDTO> omniDTOS = omniApiClient.getOmni(dataAnnotation.testDataType().getTestDataType(),
                dataAnnotation.keep(),
                dataAnnotation.amount())
                .getBody();
        omniDTOS.forEach(omniDTO -> {
            try {
                testData.get().get(dataAnnotation).add(castOmni(omniDTO, omniClasse));
            } catch (JsonProcessingException e) {
                fail("Couldn't deserialize omni");
            }
        });
    }

    private <T> T castOmni(OmniDTO omniDTO, Class<T> type) throws JsonProcessingException {
        //At this moment the omni is some map or so to get it in the right value it's serialized and serialized
        String omniAsString = mapper.writeValueAsString(omniDTO.getOmni());
        return mapper.readValue(omniAsString, type);

    }

    private <T> void createList(TestData dataAnnotation, Class<T> type) {
        if (!testData.get().containsKey(dataAnnotation.testDataType())) {
            Class omniClass = dataAnnotation.testDataType().getOmniClass();
            testData.get().put(dataAnnotation.testDataType(), new ArrayList<T>(dataAnnotation.amount()));
        }

    }

    public <T> T getTestData(TestDataTypeConstants testDataTypeConstants) {
        List<T> data = testData.get().get(testDataTypeConstants);
        if (data.size() == 0) {
            fail("Either you asked for wrong test data type or not enough test data");
        }
        T requestedData = data.get(data.size() - 1);
        data.remove(data.size() - 1);
        return requestedData;
    }
}