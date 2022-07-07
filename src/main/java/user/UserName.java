package user;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserName {
    private String email;

    @Step("Generate a new user name")
    public static UserName getRandomName() {
        String name = RandomStringUtils.randomAlphanumeric(12);

        return new UserName(name);
    }
}
