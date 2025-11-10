package net.engineeringdigest.JournalApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotEmpty
    private String userName;
    private String email;
    private boolean getUpdates;
    @NotEmpty
    private String password;
}
