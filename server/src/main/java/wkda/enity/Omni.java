package wkda.enity;

import lombok.Data;
import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;
@Data
@Entity
public class Omni {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    @NotBlank
    private String dataType;

    @NotEmpty
    private String omni;

    private DateTime created;
}
