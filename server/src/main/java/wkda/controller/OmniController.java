package wkda.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wkda.common.dto.testdataprovider.OmniCreateDTO;
import wkda.common.dto.testdataprovider.OmniDTO;
import wkda.common.dto.testdataprovider.OmniSearchDTO;
import wkda.service.OmniService;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
public class OmniController {
    @Autowired
    private OmniService omniService;

    @PostMapping("/omni")
    public OmniDTO createOmni(OmniCreateDTO createDTO) {
        log.info("Create omni was called with input {}. Start creation", createDTO);
        return omniService.create(createDTO);
    }

    @PostMapping("/omni/search")
    public List<OmniDTO> searchOmni(OmniSearchDTO searchDTO) {
        log.info("Omni search was called with input {}. Start searching", searchDTO);
        return omniService.search(searchDTO);
    }

    @GetMapping("/omni/{dataType}")
    public List<OmniDTO> getOmni(@PathVariable @NotBlank String dataType,
                                 @RequestParam(value = "keep", defaultValue = "false", required = false) boolean keep,
                                 @RequestParam(value = "amount", defaultValue = "1", required = false) int amount) {
        log.info("Get omni was called. Start getting {} omni for data type {}, keep={}", amount, dataType, amount);
        return omniService.get(dataType, keep, amount);
    }

    @DeleteMapping("/omni/purge/{dataType}")
    public void purgeQueue(@PathVariable @NotBlank String dataType) {
        log.info("Purge Queue was called. Start purging queue {}", dataType);
        omniService.purgeQueue(dataType);
    }

    @DeleteMapping("/omni/purge/all")
    public void purgeQueue() {
        log.info("Purge all Queues was called. Start purging queue all queues");
        omniService.purgeAllQueue();
    }

    @PostMapping("/omni/delete")
    public void deleteOmnis(List<UUID> ids) {
        log.info("Remove omnis was called. Start deleting omnis with ids {}", ids);
        omniService.delete(ids);
    }


}
