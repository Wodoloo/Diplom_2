package user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credentials {

    private String email;
    private String password;

    public static Credentials from (User user) {
        return new Credentials(user.getEmail(), user.getPassword());
    }
}
