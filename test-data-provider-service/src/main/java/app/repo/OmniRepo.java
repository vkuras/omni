package app.repo;

import app.enity.Omni;
import org.joda.time.DateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OmniRepo extends JpaRepository<Omni, UUID> {

    public List<Omni> findByDataTypeOrderByCreatedOnAsc(String dataType, Pageable page);
    public void deleteByIdIn(List<UUID> ids);
    public void deleteByDataType(String dataType);
    public Long countByDataType(String dataType);
    @Query("select a from Omni a where a.dataType=:dataType and a.createdOn <= :creationDateTime")
    List<Omni> findAllWithCreationDateTimeBefore(
            @Param("creationDateTime") DateTime creationDateTime,
            @Param("dataType") String dataType);


}
