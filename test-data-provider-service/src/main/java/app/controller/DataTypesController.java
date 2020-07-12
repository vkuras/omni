package app.controller;

import app.service.DataTypeService;
import common.dto.testdataprovider.DataTypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class DataTypesController {

    @Autowired
    private DataTypeService dataTypeService;

    @GetMapping("/data-type/categories")
    public List<String> getCategories(){
        log.debug("Get Categories was called");
        return dataTypeService.getCategories();

    }
    @GetMapping("/data-type/{category}")
    public List<DataTypeDTO> getCategories(@PathVariable(name="category",required = true) String category){
        log.debug("Getting all data types for category {}",category);
        return dataTypeService.getDataTypes(category);
    }
}
