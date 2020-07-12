package intergration;

import app.config.AppConfig;
import app.converter.OmniConverter;
import app.enity.DataType;
import app.repo.DataTypeRepo;
import app.repo.OmniRepo;
import app.service.OmniService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.dto.testdataprovider.OmniCreateDTO;
import dto.CarDTO;
import io.restassured.http.ContentType;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.flywaydb.test.annotation.FlywayTest;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ContextConfiguration(classes = AppConfig.class)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@FlywayTest
@AutoConfigureEmbeddedDatabase
@EnableAutoConfiguration
public class DataTypeTest {
    @Value("${test-data-provider-service.others:OTHERS}")
    private String others;
    @LocalServerPort
    private Integer port;
    @Autowired
    private OmniRepo omniRepo;

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private OmniService omniService;
    @Autowired
    private DataTypeRepo dataTypeRepo;
    @Autowired
    OmniConverter omniConverter;

    @Test
    public void getCategoriesUncategorizedTest() throws JsonProcessingException {
        omniRepo.save(omniConverter.convert(createTestOmni("unCategorized")));

        //@formatter:off
        given()
                .header("content-type", ContentType.JSON)
                .baseUri(String.format("http://localhost:%s", port))
                .basePath("data-type/categories")


                .when()
                .get()

                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(".", contains(others));

        //@formatter:on

    }
    @Test
    public void getCategoriesCategorizedTest() throws JsonProcessingException {
        String dataTypeName="categorized";
        String catregory="myCategory";
        omniRepo.save(omniConverter.convert(createTestOmni(dataTypeName)));
        dataTypeRepo.save(createDataType(dataTypeName,catregory));
        //@formatter:off
        given()
                .header("content-type", ContentType.JSON)
                .baseUri(String.format("http://localhost:%s", port))
                .basePath("data-type/categories")
         .when()
                .get()
        .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(".", contains(catregory))
        .body(".", not(contains(others)));

        //@formatter:on

    }


    @Test
    public void getCategorizedDataTypesTest() throws JsonProcessingException {
        String dataTypeName1="categorized";
        String catregory1="myCategory";
        String dataTypeName2="categorized2";
        String catregory2="myCategory2";
        omniRepo.save(omniConverter.convert(createTestOmni(dataTypeName1)));
        omniRepo.save(omniConverter.convert(createTestOmni(dataTypeName2)));
        dataTypeRepo.save(createDataType(dataTypeName1,catregory1));
        dataTypeRepo.save(createDataType(dataTypeName2,catregory2));
        //@formatter:off
        given()
                .header("content-type", ContentType.JSON)
                .baseUri(String.format("http://localhost:%s", port))
                .basePath("data-type/"+catregory1)
       .when()
                .get()
       .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("name[0]", equalTo(dataTypeName1))
                .body("amountOfOmnis[0]",equalTo(1))
                .body("category[0]",equalTo(catregory1))
                .body("oldestOmni[0]",notNullValue())
                .body("name", not(contains(dataTypeName2)));
        //@formatter:on
    }

    @After
    public void emptyDb() {
        dataTypeRepo.deleteAll();
        omniRepo.deleteAll();
    }


    //TODO MOVE TO CREATOR
    private OmniCreateDTO createTestOmni(String dataType) {
        CarDTO carDTO = CarDTO.builder()
                .vin("fnsdkjn")
                .buildDate(LocalDate.now())
                .doors(5)
                .tüv(LocalDateTime.now())
                .build();
        OmniCreateDTO omni = new OmniCreateDTO();
        omni.setDataType(dataType);
        omni.setOmni(carDTO);
        return omni;
    }
    //TODO MOVE TO CREATOR
    private DataType createDataType(String dataType,String category) {
        DataType dataTypeEntity= new DataType();
        dataTypeEntity.setName(dataType);
        dataTypeEntity.setCategory(category);
        dataTypeEntity.setCreatedOn(DateTime.now());

        return dataTypeEntity;
    }
}
