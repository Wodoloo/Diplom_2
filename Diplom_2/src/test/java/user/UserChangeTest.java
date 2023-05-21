package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.*;

public class UserChangeTest {
    private User user;
    private UserClient userClient;
    private String token;


    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.generateDefaultUser();

    }

    @After
    public void cleanUp() {
        userClient.deleteUser(token);
    }

    @DisplayName("Change user")
    @Test
    public void changeUserTest() {
        ValidatableResponse responseCreate = userClient.createUser(user);
        token = responseCreate.extract().path("accessToken").toString().substring(7);
        ValidatableResponse responseChange = userClient.changeUserWithToken(user, token);
        boolean isUserCreated = responseChange.extract().path("success");
        int statusCode = responseChange.extract().statusCode();

        assertTrue("User changed is not ok", isUserCreated);
        assertEquals("User changed status code uncorrected", SC_OK, statusCode);
    }

    @DisplayName("Change user without Authorisation")
    @Test
    public void changeUserWithoutAuthTest() {
        ValidatableResponse responseCreate = userClient.createUser(user);
        token = responseCreate.extract().path("accessToken").toString().substring(7);
        ValidatableResponse responseChange = userClient.changeUserWithoutToken(user);
        boolean isUserChanged = responseChange.extract().path("success");
        int statusCode = responseChange.extract().statusCode();
        String actualMassage = responseChange.extract().path("message");

        assertFalse("User changed", isUserChanged);
        assertEquals("User changed status code is incorrect", SC_UNAUTHORIZED, statusCode);
        assertEquals("User is changed", "You should be authorised", actualMassage);
    }

}
