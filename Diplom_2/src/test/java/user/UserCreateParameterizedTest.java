package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class UserCreateParameterizedTest {

    private User user;
    private UserClient userClient;
    private int statusCode;
    private String massage;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    public UserCreateParameterizedTest(User user, int statusCode, String massage) {
        this.user = user;
        this.statusCode = statusCode;
        this.massage = massage;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {UserGenerator.getWithLoginOnly(), SC_FORBIDDEN, "User already exists"},
                {UserGenerator.createUserWithoutEmailAndLogin(), SC_FORBIDDEN, "User already exists"},
        };
    }

    @DisplayName("Parameterized test to not create new user")
    @Test
    public void createUserWithoutData() {
        ValidatableResponse responseCreate = userClient.createUser(user);
        String actualMessage = responseCreate.extract().path("message");
        int actualStatusCode = responseCreate.extract().statusCode();

        assertEquals("Status code is not 40*", statusCode, actualStatusCode);
        assertEquals("Error massage is incorrect", massage, actualMessage);
    }
}