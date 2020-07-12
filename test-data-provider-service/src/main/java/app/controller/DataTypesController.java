package app.controller;

import app.service.DataTypeService;
import common.dto.testdataprovider.CategoryUpdateDTO;
import common.dto.testdataprovider.DataTypeDTO;
import common.dto.testdataprovider.ThresholdLevelCreateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PatchMapping("/data-type/category")
    public List<DataTypeDTO> setCategory(@RequestBody CategoryUpdateDTO categoryUpdateDTO){
        log.debug("Updating category for data type {} to {}",categoryUpdateDTO.getDataTypeId(),categoryUpdateDTO.getCategory());
        dataTypeService.setCategory(categoryUpdateDTO);
        return dataTypeService.getDataTypes(categoryUpdateDTO.getCategory());
    }
    @PatchMapping("/data-type/threshold")
    public void setThreshold(@RequestBody ThresholdLevelCreateDTO thresholdLevelCreateDTO){
        log.debug("Updating threshold {} for data type {}",thresholdLevelCreateDTO.getThresholdLevel(),
                thresholdLevelCreateDTO.getTestDataid());
        dataTypeService.setThreshold(thresholdLevelCreateDTO);

    }
}
