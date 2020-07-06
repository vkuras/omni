package wkda.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wkda.enity.Omni;
@Repository
public interface OmniRepo extends CrudRepository<Omni, Long> {


}
