package wkda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wkda.common.dto.testdataprovider.OmniCreateDTO;
import wkda.common.dto.testdataprovider.OmniDTO;
import wkda.common.dto.testdataprovider.OmniSearchDTO;
import wkda.enity.Omni;
import wkda.repo.OmniRepo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static wkda.converter.OmniConverter.convert;

@Component
public class OmniService {
    @Autowired
    private OmniRepo omniRepo;
    @Transactional
    public OmniDTO create(OmniCreateDTO createDTO) {
        Omni omni= convert(createDTO);
        omni=omniRepo.save(omni);
        return convert(omni);
    }

    public List<OmniDTO> search(OmniSearchDTO searchDTO) {
        return null;
    }

    public List<OmniDTO> get(String dataType, boolean keep, int amount) {
        return null;
    }

    public void purgeQueue(String dataType) {

    }

    public void purgeAllQueue() {

    }

    public void delete(List<UUID> ids) {
    }
}
