package user;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authorization {

    private String authorization;

    @Step("Add authorization field")
    public static Authorization getAccessToken(String accessToken) {
        String authorization = accessToken;

        return new Authorization(authorization);
    }

}
