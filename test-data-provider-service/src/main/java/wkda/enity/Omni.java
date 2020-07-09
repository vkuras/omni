package wkda.enity;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;
@Data
@Entity
public class Omni {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;


    private String dataType;


    private String omni;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createdOn;
}
