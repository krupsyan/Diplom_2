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

    @Step("Generate new password and name for current user")
    public static User getRandomNamePassword(User user) {
        String email = user.getEmail();
        String password = RandomStringUtils.randomAlphanumeric(12);
        String name = RandomStringUtils.randomAlphanumeric(12);
        return new User(email, password, name);
    }

    @Step("Generate a new user without email with random password and name")
    public static User getRandomPasswordName() {
        String password = RandomStringUtils.randomAlphanumeric(12);
        String name = RandomStringUtils.randomAlphanumeric(12);
        return new User(password, name, 1);
    }

    @Step("Generate a new user without name with random email and password")
    public static User getRandomEmailPassword() {
        String email = RandomStringUtils.randomAlphanumeric(6) + "@" + RandomStringUtils.randomAlphanumeric(6) + ".ru";
        String password = RandomStringUtils.randomAlphanumeric(12);
        return new User(email, password, 3);
    }

    @Step("Generate a new user without password with random email and name")
    public static User getRandomNoPassword() {
        String email = RandomStringUtils.randomAlphanumeric(6) + "@" + RandomStringUtils.randomAlphanumeric(6) + ".ru";
        String name = RandomStringUtils.randomAlphanumeric(12);
        return new User(email, name, 2);
    }

    @Step("Generate a new user email")
    public static User getRandomEmail() {
        String email = RandomStringUtils.randomAlphanumeric(6) + "@" + RandomStringUtils.randomAlphanumeric(6) + ".ru";

        return new User(email, 1);
    }

    @Step("Generate a new user name")
    public static User getRandomName() {
        String name = RandomStringUtils.randomAlphanumeric(12);

        return new User(name, 3);
    }

    @Step("Generate a new user password")
    public static User getRandomPassword() {
        String password = RandomStringUtils.randomAlphanumeric(12);

        return new User(password, 2);
    }

    public User(String value1, String value2, int missingType) {
        if (missingType == 1) {
            this.password = value1;
            this.name = value2;
        }else if(missingType == 2){
            this.email = value1;
            this.name = value2;
        }else if(missingType == 3){
            this.email = value1;
            this.password = value2;
        }
    }

    public User(String value, int type) {

        if(type == 1){
            this.email = value;
        }else if(type == 2){
            this.password = value;
        }
        else if(type == 3){
            this.name = value;
        }
    }

}
