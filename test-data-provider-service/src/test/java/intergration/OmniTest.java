package intergration;

import app.config.AppConfig;
import app.enity.Omni;
import app.repo.OmniRepo;
import app.service.OmniService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.CarDTO;
import io.restassured.http.ContentType;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.flywaydb.test.annotation.FlywayTest;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import wkda.common.dto.testdataprovider.OmniCreateDTO;
import wkda.common.dto.testdataprovider.OmniDTO;
import wkda.common.dto.testdataprovider.OmniSearchDTO;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ContextConfiguration(classes = AppConfig.class)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@FlywayTest
@AutoConfigureEmbeddedDatabase
@EnableAutoConfiguration
public class OmniTest {
    @LocalServerPort
    private Integer port;
    @Autowired
    private OmniRepo repo;

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private OmniService omniService;


    @Test
    public void createOmniTest() throws JsonProcessingException {
        String dataType="createOmniTest";
        OmniCreateDTO omniCreateDTO = createTestOmni(dataType);
        CarDTO car = (CarDTO) omniCreateDTO.getOmni();
        //@formatter:off
        given()
                .header("content-type", ContentType.JSON)
                .baseUri(String.format("http://localhost:%s", port))
                .basePath("/omni")
                .body(mapper.writeValueAsString(omniCreateDTO))

       .when()
                .post()

        .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("dataType", equalTo(dataType))
                .body("omni.vin", equalTo(car.getVin()))
                .body("omni.buildDate", equalTo(printDate(car.getBuildDate())))
                .body("omni.doors", equalTo(car.getDoors()))
                .body("omni.tüv", equalTo(printTime(car.getTüv())))
                .body("id", notNullValue())
                .body("createdOn", notNullValue());
        //@formatter:on
        List<Omni> omniDTOS = repo.findByDataTypeOrderByCreatedOnAsc(dataType, PageRequest.of(0, 500));
        assertEquals(1, omniDTOS.size());
        assertNotNull(omniDTOS.get(0).getId());
        assertNotNull(omniDTOS.get(0).getCreatedOn());
        assertEquals(dataType, omniDTOS.get(0).getDataType());
        assertNotNull(omniDTOS.get(0).getOmni());
    }



    @Test
    public void getOmniKeepTest() throws JsonProcessingException {
        String dataType="getOmniKeepTest";
        OmniCreateDTO omniCreateDTO = createTestOmni(dataType);
        CarDTO car = (CarDTO) omniCreateDTO.getOmni();
        omniService.create(omniCreateDTO);
        //@formatter:off
        given()
                .header("content-type", ContentType.JSON)
                .baseUri(String.format("http://localhost:%s", port))
                .basePath(String.format("/omni/%s",dataType))
                .queryParam("keep",true)
       .when()
                .get()
                .prettyPeek()
        .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("dataType[0]", equalTo(dataType))
                .body("omni.vin[0]", equalTo(car.getVin()))
                .body("omni.buildDate[0]", equalTo(printDate(car.getBuildDate())))
                .body("omni.doors[0]", equalTo(car.getDoors()))
                .body("omni.tüv[0]", equalTo(printTime(car.getTüv())))
                .body("id[0]", notNullValue())
                .body("createdOn[0]", notNullValue())
                .body("createdOn[1]", nullValue());
        //@formatter:on
        List<Omni> omniDTOS = repo.findByDataTypeOrderByCreatedOnAsc(dataType, PageRequest.of(0, 500));
        assertEquals(1, omniDTOS.size());
    }

    @Test
    public void getOmniNotKeepTest() throws JsonProcessingException {
        String dataType="getOmniNotKeepTest";
        OmniCreateDTO omniCreateDTO = createTestOmni(dataType);
        CarDTO car = (CarDTO) omniCreateDTO.getOmni();
        omniService.create(omniCreateDTO);
        //@formatter:off
        given()
                .header("content-type", ContentType.JSON)
                .baseUri(String.format("http://localhost:%s", port))
                .basePath(String.format("/omni/%s",dataType))

         .when()
                .get()
                .prettyPeek()
        .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("dataType[0]", equalTo(dataType))
                .body("omni.vin[0]", equalTo(car.getVin()))
                .body("omni.buildDate[0]", equalTo(printDate(car.getBuildDate())))
                .body("omni.doors[0]", equalTo(car.getDoors()))
                .body("omni.tüv[0]", equalTo(printTime(car.getTüv())))
                .body("id[0]", notNullValue())
                .body("createdOn[0]", notNullValue())
                .body("createdOn[1]", nullValue());
        //@formatter:on
        List<Omni> omniDTOS = repo.findByDataTypeOrderByCreatedOnAsc(dataType, PageRequest.of(0, 500));
        assertEquals(0, omniDTOS.size());
    }
    @Test
    public void getMultipleOmniTest() throws JsonProcessingException {
        String dataType="getOmniMultipleTest";
        OmniCreateDTO omniCreateDTO = createTestOmni(dataType);
        CarDTO car = (CarDTO) omniCreateDTO.getOmni();
        omniService.create(omniCreateDTO);
        omniService.create(omniCreateDTO);
        //@formatter:off
        given()
                .header("content-type", ContentType.JSON)
                .baseUri(String.format("http://localhost:%s", port))
                .basePath(String.format("/omni/%s",dataType))
                .queryParam("amount",2)
         .when()
                .get()
                .prettyPeek()
         .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("dataType[0]", equalTo(dataType))
                .body("omni.vin[0]", equalTo(car.getVin()))
                .body("omni.buildDate[0]", equalTo(printDate(car.getBuildDate())))
                .body("omni.doors[0]", equalTo(car.getDoors()))
                .body("omni.tüv[0]", equalTo(printTime(car.getTüv())))
                .body("id[0]", notNullValue())
                .body("createdOn[0]", notNullValue())
                .body("createdOn[1]", notNullValue());
        //@formatter:on
        List<Omni> omniDTOS = repo.findByDataTypeOrderByCreatedOnAsc(dataType, PageRequest.of(0, 500));
        assertEquals(0, omniDTOS.size());
    }

    @Test
    public void purgeQueueTest() throws JsonProcessingException {
        String dataTypeDelete="purg1";
        String dataTypeKeep="purg2";
        OmniCreateDTO omniCreateDTO1 = createTestOmni(dataTypeDelete);
        OmniCreateDTO omniCreateDTO2 = createTestOmni(dataTypeKeep);
        omniService.create(omniCreateDTO1);
        omniService.create(omniCreateDTO2);
        //@formatter:off
        given()
                .header("content-type", ContentType.JSON)
                .baseUri(String.format("http://localhost:%s", port))
                .basePath("/omni/purge/all")

                .when()
                .delete()
                .prettyPeek();
        //@formatter:on
        assertEquals(0,repo.countByDataType(dataTypeDelete).longValue());
        assertEquals(0,repo.countByDataType(dataTypeKeep).longValue());
    }
    @Test
    public void searchTest() throws JsonProcessingException {
        String dataTypeSearch="search";

        OmniCreateDTO omniCreateDTO1 = createTestOmni(dataTypeSearch);
        OmniCreateDTO omniCreateDTO2 = createTestOmni(dataTypeSearch);
        OmniDTO omniDTO=omniService.create(omniCreateDTO1);
        omniService.create(omniCreateDTO2);
        OmniSearchDTO searchDTO=new OmniSearchDTO();
        searchDTO.createdBefore(omniDTO.getCreatedOn());
        searchDTO.setDataType(dataTypeSearch);
        //@formatter:off
        given()
                .header("content-type", ContentType.JSON)
                .baseUri(String.format("http://localhost:%s", port))
                .basePath("/omni/search")
                .body(mapper.writeValueAsString(searchDTO))
        .when()
                .post()
                .prettyPeek()
        .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("id[0]",equalTo(printId(omniDTO.getId())))
                .body("id[1]",nullValue());
        //@formatter:on
        assertEquals(2,repo.countByDataType(dataTypeSearch).longValue());

    }

    @Test
    public void deletDataTypeTest() throws JsonProcessingException {
        String dataTypeDelete="purg1";
        String dataTypeKeep="purg2";
        OmniCreateDTO omniCreateDTO1 = createTestOmni(dataTypeDelete);
        OmniCreateDTO omniCreateDTO2 = createTestOmni(dataTypeKeep);
        omniService.create(omniCreateDTO1);
        omniService.create(omniCreateDTO2);
        //@formatter:off
        given()
                .header("content-type", ContentType.JSON)
                .baseUri(String.format("http://localhost:%s", port))
                .basePath(String.format("/omni/purge/%s",dataTypeDelete))

                .when()
                .delete()
                .prettyPeek();
        //@formatter:on
        assertEquals(0,repo.countByDataType(dataTypeDelete).longValue());
        assertEquals(1,repo.countByDataType(dataTypeKeep).longValue());

    }
    private String printTime(LocalDateTime time) {
        return time.toString().replace("<","").replace(">","");

    }

    private String printDate(LocalDate date) {
        return date.toString().replace("<","").replace(">","");

    }
    private String printId(UUID id) {
        return id.toString().replace("<","").replace(">","");

    }

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
}
