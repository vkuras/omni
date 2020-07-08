package wkda.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import wkda.common.dto.testdataprovider.OmniCreateDTO;
import wkda.common.dto.testdataprovider.OmniDTO;
import wkda.common.dto.testdataprovider.OmniSearchDTO;
import wkda.converter.OmniConverter;
import wkda.enity.Omni;
import wkda.repo.OmniRepo;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class OmniService {
    @Autowired
    private OmniRepo omniRepo;
    @Autowired
    private OmniConverter omniConvert;

    @Transactional
    public OmniDTO create(OmniCreateDTO createDTO) throws JsonProcessingException {
        Omni omniEntity = omniConvert.convert(createDTO);
        log.debug("Start");
        omniEntity = omniRepo.save(omniEntity);
        log.info("Saved Omni {}",omniEntity);
        return omniConvert.convert(omniEntity);
    }

    @Transactional
    public List<OmniDTO> search(OmniSearchDTO searchDTO) {
        return null;
    }

    @Transactional
    public List<OmniDTO> get(String dataType, boolean keep, int amount) throws JsonProcessingException, NotFoundException {
        List<OmniDTO> omniDTOS=getOmnis(dataType,keep,amount);
        checkSize(omniDTOS,amount);
        if(!keep){
            deleteOmnis(omniDTOS);
        }
       return omniDTOS;
    }

    private void checkSize(List<OmniDTO> omniDTOS,int amount) throws NotFoundException {
        log.debug("Start checking that omni where found");
        if(omniDTOS.size()<amount){
            throw new NotFoundException("Omni was not found");
        }
        log.debug("Omnis where found");
    }

    private List<OmniDTO> getOmnis(String dataType, boolean keep, int amount) throws JsonProcessingException {
        log.debug("Getting {} OmniEntites of data type {} from db",dataType,amount);
        List<Omni> omniEntities= omniRepo.findByDataTypeOrderByCreatedOnAsc(dataType, PageRequest.of(0,amount));

        log.debug("Start converting them");
        //I don't want to define own funtional interface for it. So i don't use stream
        List<OmniDTO> omniDTOS=new ArrayList<>(amount);
        for(Omni omniEntity:omniEntities){
            omniDTOS.add(omniConvert.convert(omniEntity));
        }
        log.info("Got omnis {}",omniDTOS);
       return omniDTOS;
    }

    @Transactional
    public void purgeQueue(String dataType) {

    }

    public void purgeAllQueue() {

    }

    public void deleteIds(List<UUID> ids) {
    }

    private void deleteOmnis(List<OmniDTO> omniDTOS ){
        List<UUID> ids= new ArrayList<>(omniDTOS.size());
        omniDTOS.stream().forEach(omniDTO->ids.add(omniDTO.getId()));
        log.debug("Start deleting omnis {}",omniDTOS);
        omniRepo.deleteByIdIn(ids);
        log.info("Deleted omnis {}",omniDTOS);
    }
}
