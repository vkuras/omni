package testdata;

import lombok.Builder;
import lombok.Data;
import org.joda.time.LocalDate;

import java.util.UUID;

/**
 * This is used as example test data
 */
@Builder
@Data
public class CarDTO {
    private String brand;
    private LocalDate buildOn;
    private int keys;
    private UUID id;
}
