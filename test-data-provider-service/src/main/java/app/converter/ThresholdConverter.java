package app.converter;

import app.enity.DataType;
import common.dto.testdataprovider.TreshholdLevelDTO;

import java.util.ArrayList;
import java.util.List;

public class ThresholdConverter {
    public static List<TreshholdLevelDTO> convert(DataType dataTypeEntity) {
        TreshholdLevelDTO red = new TreshholdLevelDTO();
        red.setMinAmount(dataTypeEntity.getMinimumRed());
        red.setFilling(TreshholdLevelDTO.FillingEnum.RED);
        TreshholdLevelDTO green = new TreshholdLevelDTO();
        green.setMinAmount(dataTypeEntity.getMinimumGreen());
        green.setFilling(TreshholdLevelDTO.FillingEnum.GREEN);
        TreshholdLevelDTO yellow = new TreshholdLevelDTO();
        yellow.setMinAmount(dataTypeEntity.getMinimumYellow());
        yellow.setFilling(TreshholdLevelDTO.FillingEnum.YELLOW);
        List<TreshholdLevelDTO> dtos = new ArrayList<>(3);
        dtos.add(red);
        dtos.add(yellow);
        dtos.add(green);
        return dtos;
    }
}
