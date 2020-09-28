package app.service;

import app.converter.DataTypeConverter;
import app.enity.DataType;
import app.repo.DataTypeRepo;
import app.repo.OmniRepo;
import com.google.common.base.Preconditions;
import common.dto.testdataprovider.CategoryUpdateDTO;
import common.dto.testdataprovider.DataTypeDTO;
import common.dto.testdataprovider.ThresholdLevelCreateDTO;
import common.dto.testdataprovider.ThresholdLevelDTO;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class DataTypeService {
    @Value("${test-data-provider-service.threshold.default.green}")
    private Integer defaultThresholdGreen;
    @Value("${test-data-provider-service.threshold.default.yellow}")
    private Integer defaultThresholdYellow;
    @Value("${test-data-provider-service.threshold.default.red}")
    private Integer getDefaultThresholdRed;
    @Value("${test-data-provider-service.others:OTHERS}")
    private String others;
    @Autowired
    private OmniRepo omniRepo;
    @Autowired
    private DataTypeRepo dataTypeRepo;

    @Transactional
    public List<String> getCategories() {
        log.info("Finding all categories");
        List<String> categoryNames = dataTypeRepo.findAllCategoryNames();
        categoryNames = (categoryNames.contains(this.others)) ? categoryNames : addOtherCatergory(categoryNames);
        log.info("Found categories {}", categoryNames);
        return categoryNames;
    }

    /**
     * ads other to categoryNames if there are any data types without category
     *
     * @param categoryNames
     * @return
     */
    private List<String> addOtherCatergory(List<String> categoryNames) {
        Preconditions.checkNotNull(categoryNames);
        log.info("Checking if I have non categorized types");
        List<String> others = null;
        if (categoryNames.isEmpty()) {
            others = omniRepo.findAllDataTypes();
        } else {
            List<String> categorizedDataTypes = dataTypeRepo.findAllNames();
            others = omniRepo.findDistinctByDataTypeNotIn(categorizedDataTypes);
        }
        if (!others.isEmpty()) {
            categoryNames.add(this.others);
        }
        return categoryNames;
    }

    @Transactional
    public List<DataTypeDTO> getDataTypes(String category) {
        log.info("Start collecting data Types with category {}", category);
        List<DataTypeDTO> dataTypeDTOS;
        if (!category.equals(others)) {
            dataTypeDTOS = collectCatgeorizedDataTypes(category);
        } else {
            dataTypeDTOS = collectUncategorizedDataTypes();
        }

        return dataTypeDTOS;
    }

    private List<DataTypeDTO> collectCatgeorizedDataTypes(String category) {
        log.debug("Getting categrory data for {}", category);
        List<DataType> dataTypes = dataTypeRepo.findByCategory(category);
        List<DataTypeDTO> dataTypeDTOS = DataTypeConverter.convert(dataTypes);
        enrichDataTypes(dataTypeDTOS);
        log.info("Found all data types with category {}", category);
        return dataTypeDTOS;
    }

    private List<DataTypeDTO> collectUncategorizedDataTypes() {
        //TODO reduce queries
        log.info("Getting all data types of category {},which have already an entry in categories", others);
        List<DataType> knownOtherDataTypes = dataTypeRepo.findByCategory(others);
        List<String> allDataTypeNamesWithCategory = dataTypeRepo.findAllDataTypesByNames();
        List<String> unknownDataTypeNames = null;
        if (!allDataTypeNamesWithCategory.isEmpty()) {
            unknownDataTypeNames = omniRepo.findDistinctByDataTypeNotIn(allDataTypeNamesWithCategory);
        } else {
            unknownDataTypeNames = omniRepo.findAllDataTypes();
        }
        List<DataType> unknownDataTypes = new ArrayList<>(unknownDataTypeNames.size());
        if (!unknownDataTypeNames.isEmpty()) {
            unknownDataTypeNames.stream().forEach(dataTypeName -> {
                unknownDataTypes.add(createDataType(dataTypeName));
            });
            dataTypeRepo.saveAll(unknownDataTypes);
            log.info("Saved data  types {}", unknownDataTypes);
        }
        knownOtherDataTypes.addAll(unknownDataTypes);
        List<DataTypeDTO> dataTypeDTOS = DataTypeConverter.convert(knownOtherDataTypes);
        enrichDataTypes(dataTypeDTOS);
        log.info("Found all data types with category {}", others);
        return dataTypeDTOS;

    }


    private void enrichDataTypes(List<DataTypeDTO> dataTypeDTOS) {
        dataTypeDTOS.forEach(dataTypeDTO -> {
            log.debug("Getting amount of omnis for {}", dataTypeDTO.getName());
            dataTypeDTO.setAmountOfOmnis(omniRepo.countByDataType(dataTypeDTO.getName()).intValue());
            log.debug("Got amount of omnis of type {} {}", dataTypeDTO.getName(), dataTypeDTO.getAmountOfOmnis());
            if (dataTypeDTO.getAmountOfOmnis() != 0) {
                log.debug("Getting oldest omni for type {}", dataTypeDTO.getName());
                dataTypeDTO.setOldestOmni(omniRepo.findByDataTypeOrderByCreatedOnAsc(dataTypeDTO.getName(), PageRequest.of(0, 1))
                        .get(0).getCreatedOn());
            }
        });
    }

    private DataType createDataType(String dataTypeName) {
        log.debug("Creating data type entity for {} ", dataTypeName);
        DataType dataType = new DataType();
        dataType.setCategory(others);
        dataType.setName(dataTypeName);
        dataType.setMinimumGreen(defaultThresholdGreen);
        dataType.setMinimumRed(getDefaultThresholdRed);
        dataType.setMinimumYellow(defaultThresholdYellow);
        dataType.setCreatedOn(DateTime.now());
        return dataType;
    }

    @Transactional
    public void setCategory(CategoryUpdateDTO categoryUpdateDTO) {
        DataType dataType = dataTypeRepo.findById(categoryUpdateDTO.getDataTypeId()).get();
        dataType.setCategory(categoryUpdateDTO.getCategory());
        dataTypeRepo.save(dataType);
        log.info("Saved data type {}", dataType);
    }

    @Transactional
    public void setThreshold(ThresholdLevelCreateDTO thresholdLevelCreateDTO) {
        DataType dataType = dataTypeRepo.findById(thresholdLevelCreateDTO.getTestDataid()).get();
        //todo
        ThresholdLevelDTO threshold = thresholdLevelCreateDTO.getThresholdLevel().stream()
                .filter(thresholdLevelDTO -> thresholdLevelDTO.getFilling() == ThresholdLevelDTO.FillingEnum.GREEN)
                .findFirst()
                .get();
        dataType.setMinimumGreen(threshold.getMinAmount());
        threshold = thresholdLevelCreateDTO.getThresholdLevel().stream()
                .filter(thresholdLevelDTO -> thresholdLevelDTO.getFilling() == ThresholdLevelDTO.FillingEnum.YELLOW)
                .findFirst()
                .get();
        dataType.setMinimumYellow(threshold.getMinAmount());
        threshold = thresholdLevelCreateDTO.getThresholdLevel().stream()
                .filter(thresholdLevelDTO -> thresholdLevelDTO.getFilling() == ThresholdLevelDTO.FillingEnum.YELLOW)
                .findFirst()
                .get();

        dataType.setMinimumYellow(threshold.getMinAmount());
        threshold = thresholdLevelCreateDTO.getThresholdLevel().stream()
                .filter(thresholdLevelDTO -> thresholdLevelDTO.getFilling() == ThresholdLevelDTO.FillingEnum.RED)
                .findFirst()
                .get();
        dataType.setMinimumRed(threshold.getMinAmount());
        dataTypeRepo.save(dataType);
        log.info("Saved data type {}", dataType);

    }

    @Transactional
    public DataTypeDTO getDataType(String dataTypeName) {
        Preconditions.checkNotNull(dataTypeName);
        DataType dataType = dataTypeRepo.findByName(dataTypeName);
        if (dataType == null) {
            dataType = new DataType();
        }
        DataTypeDTO dataTypeDTO = DataTypeConverter.convert(dataType);
        dataTypeDTO.setAmountOfOmnis(omniRepo.countByDataType(dataTypeName).intValue());
        if (dataTypeDTO.getAmountOfOmnis() != 0) {
            dataTypeDTO.setOldestOmni(omniRepo.findByDataTypeOrderByCreatedOnAsc(dataTypeName, PageRequest.of(0, 1)).get(0).getCreatedOn());
        }
        return dataTypeDTO;
    }
}
