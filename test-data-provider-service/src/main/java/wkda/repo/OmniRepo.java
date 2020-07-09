package wkda.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wkda.enity.Omni;

import java.util.List;
import java.util.UUID;

@Repository
public interface OmniRepo extends CrudRepository<Omni, UUID> {

    public List<Omni> findByDataTypeOrderByCreatedOnAsc(String dataType, Pageable page);
    public void deleteByIdIn(List<UUID> ids);
    public void deleteByDataType(String dataType);

}
