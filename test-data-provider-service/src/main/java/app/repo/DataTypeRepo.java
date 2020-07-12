package app.repo;

import app.enity.DataType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DataTypeRepo extends JpaRepository<DataType, UUID> {
    @Query("SELECT DISTINCT d.category FROM DataType d")
    public List<String> findAllCategoryNames();

    @Query("SELECT d.name FROM DataType d")
    public List<String> findAllDataTypesByNames();
    @Query("SELECT DISTINCT d.name FROM DataType d")
    public List<String> findAllNames();

    public List<DataType> findByNameNotIn( List<String> excludeTypes);

    public List<DataType> findByCategory(String category);
}
