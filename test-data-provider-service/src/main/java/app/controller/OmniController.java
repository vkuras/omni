package app.controller;

import app.service.OmniService;
import com.fasterxml.jackson.core.JsonProcessingException;
import common.dto.testdataprovider.OmniCreateDTO;
import common.dto.testdataprovider.OmniDTO;
import common.dto.testdataprovider.OmniSearchDTO;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
public class OmniController {
    @Autowired
    private OmniService omniService;

    @PostMapping("/omni")
    public OmniDTO createOmni(@RequestBody OmniCreateDTO createDTO) throws JsonProcessingException {
        log.debug("Create omni was called with input {}. Start creation", createDTO);
        return omniService.create(createDTO);
    }

    @PostMapping("/omni/search")
    public List<OmniDTO> searchOmni(@RequestBody OmniSearchDTO searchDTO) throws JsonProcessingException {
        log.debug("Omni search was called with input {}. Start searching", searchDTO);
        return omniService.search(searchDTO);
    }

    @GetMapping("/omni/{dataType}")
    public List<OmniDTO> getOmni(@PathVariable @NotBlank String dataType,
                                 @RequestParam(value = "keep", defaultValue = "false", required = false) boolean keep,
                                 @RequestParam(value = "amount", defaultValue = "1", required = false) int amount) throws JsonProcessingException, NotFoundException {
        log.debug("Get omni was called. Start getting {} omni for data type {}, keep={}", amount, dataType, amount);
        return omniService.get(dataType, keep, amount);
    }

    @DeleteMapping("/omni/purge/{dataType}")
    public void purgeQueue(@PathVariable @NotBlank String dataType) {
        log.debug("Purge Queue was called. Start purging queue {}", dataType);
        omniService.purgeQueue(dataType);
    }

    @DeleteMapping("/omni/purge/all")
    public void purgeQueue() {
        log.debug("Purge all Queues was called. Start purging queue all queues");
        omniService.purgeAllQueue();
    }

    @PostMapping("/omni/delete")
    public void deleteOmnis(List<UUID> ids) {
        log.debug("Remove omnis was called. Start deleting omnis with ids {}", ids);
        omniService.deleteIds(ids);
    }


}
