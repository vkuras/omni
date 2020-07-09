package wkda.service;

import org.springframework.stereotype.Component;
import wkda.common.dto.testdataprovider.OmniCreateDTO;
import wkda.common.dto.testdataprovider.OmniDTO;
import wkda.common.dto.testdataprovider.OmniSearchDTO;

import java.util.List;
import java.util.UUID;

@Component
public class OmniService {
    public OmniDTO create(OmniCreateDTO createDTO) {
        return null;
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
