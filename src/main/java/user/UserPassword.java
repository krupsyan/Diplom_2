package user;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPassword {
    private String email;

    @Step("Generate a new user password")
    public static UserPassword getRandomPassword() {
        String password = RandomStringUtils.randomAlphanumeric(12);

        return new UserPassword(password);
    }
}
