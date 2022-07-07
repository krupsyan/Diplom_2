package user;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialsOnlyPassword {
    private String email;
    private String password;

    public UserCredentialsOnlyPassword(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    @Step("Take credentials for login from user")
    public static UserCredentialsOnlyPassword from(User user) {
        return new UserCredentialsOnlyPassword(user);
    }
}
