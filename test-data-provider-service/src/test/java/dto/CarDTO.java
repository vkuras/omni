package dto;

import lombok.Builder;
import lombok.Data;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;


/**
 * is used to test omnis
 */
@Builder
@Data
public class CarDTO {
    private String vin;
    private int doors;
    private LocalDate buildDate;
    private LocalDateTime tüv;

}
