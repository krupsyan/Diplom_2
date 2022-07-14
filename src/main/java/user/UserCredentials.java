package user;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentials {
    private String email;
    private String password;

    public UserCredentials(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    @Step("Take credentials for login from user")
    public static UserCredentials from(User user) {
        return new UserCredentials(user);
    }
}
