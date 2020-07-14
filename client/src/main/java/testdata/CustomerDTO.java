package testdata;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean merchant;
    private boolean privateCustomer;
}
