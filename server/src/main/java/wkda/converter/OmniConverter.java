package wkda.converter;

import org.springframework.beans.BeanUtils;
import wkda.common.dto.testdataprovider.OmniCreateDTO;
import wkda.common.dto.testdataprovider.OmniDTO;
import wkda.enity.Omni;

public class OmniConverter {
    public static Omni convert(OmniCreateDTO createDTO){
        Omni omni =new Omni();
        BeanUtils.copyProperties(createDTO,omni);

        return omni;
    }
    public static OmniDTO convert(Omni omni){
        OmniDTO omniDTO =new OmniDTO();
        BeanUtils.copyProperties(omni,omniDTO);
        return omniDTO;
    }
}
