package app.testdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDate;

import java.util.UUID;

/**
 * This is used as example test data
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    private String brand;
    private LocalDate buildOn;
    private int keys;
    private UUID id;
}
