package app.converter;

import app.enity.DataType;
import common.dto.testdataprovider.ThresholdLevelDTO;

import java.util.ArrayList;
import java.util.List;

public class ThresholdConverter {
    public static List<ThresholdLevelDTO> convert(DataType dataTypeEntity) {
        ThresholdLevelDTO red = new ThresholdLevelDTO();
        red.setMinAmount(dataTypeEntity.getMinimumRed());
        red.setFilling(ThresholdLevelDTO.FillingEnum.RED);
        ThresholdLevelDTO green = new ThresholdLevelDTO();
        green.setMinAmount(dataTypeEntity.getMinimumGreen());
        green.setFilling(ThresholdLevelDTO.FillingEnum.GREEN);
        ThresholdLevelDTO yellow = new ThresholdLevelDTO();
        yellow.setMinAmount(dataTypeEntity.getMinimumYellow());
        yellow.setFilling(ThresholdLevelDTO.FillingEnum.YELLOW);
        List<ThresholdLevelDTO> dtos = new ArrayList<>(3);
        dtos.add(red);
        dtos.add(yellow);
        dtos.add(green);
        return dtos;
    }
}
