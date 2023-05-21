package orders;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.IngredientGenerator;
import order.Order;
import order.OrdersClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;
import user.UserGenerator;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.assertEquals;

public class OrdersShowTest {
    private User user;
    private UserClient userClient;
    private OrdersClient ordersClient;
    private Order order;

    String token;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.generateDefaultUser();
        ordersClient = new OrdersClient();
        ValidatableResponse responseCreate = userClient.createUser(user);
        token = responseCreate.extract().path("accessToken").toString().substring(7);
        order = IngredientGenerator.generateBun();
        ordersClient.createOrderWithoutToken(order);
    }

    @After
    public void cleanUp() {
        userClient.deleteUser(token);
    }

    @DisplayName("Show orders with authorisation")
    @Test
    public void orderCanNotBeCreatedWithAuthorisation() {
        ValidatableResponse responseShowOrders = ordersClient.getOrdersUserWithToken(order, token);
        boolean isOrderCreated = responseShowOrders.extract().path("success");
        int statusCode = responseShowOrders.extract().statusCode();

        assertEquals("Order is not created", true, isOrderCreated);
        assertEquals("Order show status code uncorrected", SC_OK, statusCode);
    }

    @DisplayName("Show orders without authorisation")
    @Test
    public void orderCanNotBeCreatedWithoutAuthorisation() {
        ValidatableResponse responseShowOrders = ordersClient.getOrdersUserWithoutToken(order);
        boolean isOrderCreated = responseShowOrders.extract().path("success");
        int statusCode = responseShowOrders.extract().statusCode();
        String actualMessage = responseShowOrders.extract().path("message");

        assertEquals("Order is not created", false, isOrderCreated);
        assertEquals("Order created status code is incorrect", SC_UNAUTHORIZED, statusCode);
        assertEquals("Order show massage is incorrect", "You should be authorised", actualMessage);

    }
}
