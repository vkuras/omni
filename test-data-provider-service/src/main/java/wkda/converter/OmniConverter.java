package wkda.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wkda.common.dto.testdataprovider.OmniCreateDTO;
import wkda.common.dto.testdataprovider.OmniDTO;
import wkda.enity.Omni;
@Slf4j
@Component
public class OmniConverter {

    @Autowired
    private ObjectMapper mapper;

    public Omni convert(OmniCreateDTO createDTO) throws JsonProcessingException {
        log.debug("Start to convert CreateOmni {} to Omni Entity",createDTO);
        Omni omniEntity = new Omni();
        omniEntity.setCreatedOn(DateTime.now());
        omniEntity.setDataType(createDTO.getDataType());
        omniEntity.setOmni(mapper.writeValueAsString(createDTO.getOmni()));
        log.debug("OmniCreateDTO {} was converted to OmniEntiy {}",createDTO,omniEntity);
        return omniEntity;
    }
    public OmniDTO convert(Omni omniEntity) throws JsonProcessingException {
        log.debug("Start converting omni Entity {}");
        OmniDTO omniDTO = new OmniDTO();
        BeanUtils.copyProperties(omniEntity,omniDTO);
        omniDTO.setOmni(mapper.readValue(omniEntity.getOmni(),Object.class));
        return omniDTO;
    }
}
