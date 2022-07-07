package user;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String email;
    private String password;
    private String name;

    @Step("Generate a new user with all fields random")
    public static User getRandom() {
        String email = RandomStringUtils.randomAlphanumeric(6) + "@" + RandomStringUtils.randomAlphanumeric(6) + ".ru";
        String password = RandomStringUtils.randomAlphanumeric(12);
        String name = RandomStringUtils.randomAlphanumeric(12);

        return new User(email, password, name);
    }

    @Step("Generate a new user with random password and firstName")
    public static User getRandomNamePassword(User user) {
        String email = user.getEmail();
        String password = RandomStringUtils.randomAlphanumeric(12);
        String name = RandomStringUtils.randomAlphanumeric(12);
        return new User(email, password, name);
    }

    @Step("Generate a new user without login with all fields random")
    public static User getRandomNoEmail() {
        String password = RandomStringUtils.randomAlphanumeric(12);
        String name = RandomStringUtils.randomAlphanumeric(12);
        return new User(password, name);
    }

    @Step("Generate a new user with random password and firstName")
    public static User getRandomPasswordFirstname(User user) {
        String email = user.getEmail();
        String password = RandomStringUtils.randomAlphanumeric(12);
        String name = RandomStringUtils.randomAlphanumeric(12);
        return new User(email, password, name);
    }

    public User(String password, String firstName) {
        this.name = firstName;
        this.password = password;
    }

    public User(String email) {
        this.email = email;
    }

}
