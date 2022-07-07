package user;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserNoPassword {
    private String email;
    private String name;

    @Step("Generate a new user without password with all fields random")
    public static UserNoPassword getRandomNoPassword() {
        String email = RandomStringUtils.randomAlphanumeric(6) + "@" + RandomStringUtils.randomAlphanumeric(6) + ".ru";
        String name = RandomStringUtils.randomAlphanumeric(12);
        return new UserNoPassword(email, name);
    }
}
