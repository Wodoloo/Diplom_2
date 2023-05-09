package orders;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import lombok.SneakyThrows;
import order.IngredientGenerator;
import order.Order;
import order.OrdersClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import user.*;

import java.util.concurrent.TimeUnit;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OrdersCreateParameterizedTest {

    private User user;
    private UserClient userClient;
    private OrdersClient ordersClient;
    private Order order;

    boolean isOrderCreated;
    int statusCode;
    String token;

    @SneakyThrows
    @Before
    public void setUp() {
        TimeUnit.SECONDS.sleep(3); // to avoid too many requests
        userClient = new UserClient();
        user = UserGenerator.generateDefaultUser();
        ordersClient = new OrdersClient();

    }

    @After
    public void cleanUp() {
        userClient.deleteUser(token);
    }

    public OrdersCreateParameterizedTest(Order order, int statusCode, boolean isOrderCreated, User user) {
        this.order = order;
        this.statusCode = statusCode;
        this.isOrderCreated = isOrderCreated;
        this.user = user;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {IngredientGenerator.generateBun(), SC_OK, true, UserGenerator.generateDefaultUser()},
                {IngredientGenerator.generateMain(), SC_OK, true, UserGenerator.generateDefaultUser()},
                {IngredientGenerator.generateSauce(), SC_OK, true, UserGenerator.generateDefaultUser()},
        };
    }

    @DisplayName("Created new order")
    @Test
    public void orderBunCanBeCreated() {
        ValidatableResponse responseCreate = userClient.createUser(user);
        token = responseCreate.extract().path("accessToken").toString().substring(7);
        ValidatableResponse responseCreateOrder = ordersClient.createOrderWithToken(order, token);
        boolean isOrderCreated = responseCreateOrder.extract().path("success");
        int statusCode = responseCreateOrder.extract().statusCode();

        assertEquals("Order is not created", true, isOrderCreated);
        assertEquals("Order created status code is incorrect", SC_OK, statusCode);
    }

}
