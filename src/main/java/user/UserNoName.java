package user;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserNoName {
    private String email;
    private String password;

    @Step("Generate a new user without name with all fields random")
    public static UserNoName getRandomNoName() {
        String email = RandomStringUtils.randomAlphanumeric(6) + "@" + RandomStringUtils.randomAlphanumeric(6) + ".ru";
        String password = RandomStringUtils.randomAlphanumeric(12);
        return new UserNoName(email, password);
    }
}
