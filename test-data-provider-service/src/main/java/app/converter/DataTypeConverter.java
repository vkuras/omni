package app.converter;

import app.enity.DataType;
import common.dto.testdataprovider.DataTypeDTO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;


public class DataTypeConverter {

    public static DataTypeDTO convert(DataType dataTypeEntity) {
        DataTypeDTO dataTypeDTO = new DataTypeDTO();
        BeanUtils.copyProperties(dataTypeEntity, dataTypeDTO);
        dataTypeDTO.setTreshholdLevels(ThresholdConverter.convert(dataTypeEntity));
        return dataTypeDTO;
    }

    public static List<DataTypeDTO> convert(List<DataType> dataTypes) {
        List<DataTypeDTO> dataTypeDTOS = new ArrayList<>(dataTypes.size());
        dataTypes.forEach(dataType -> dataTypeDTOS.add(DataTypeConverter.convert(dataType)));
        return dataTypeDTOS;
    }
}
