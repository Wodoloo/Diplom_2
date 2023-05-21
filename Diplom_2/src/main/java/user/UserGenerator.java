package user;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import java.util.Locale;


public class UserGenerator {

    private static Faker faker = new Faker(new Locale("en"));

    @Step("Create default user")
    public static User generateDefaultUser() {
        String randomEmail = faker.name().firstName() + "@mail.ru";
        String randomPassword = faker.numerify("#####");
        String randomLogin = faker.name().firstName();
        return new User(randomLogin, randomPassword, randomEmail);
    }

    @Step("Create new user without email and login")
    public static User createUserWithoutEmailAndLogin() {
        String randomEmail = " ";
        String randomPassword = faker.numerify("###");
        String randomLogin = " ";
        return new User(randomLogin, randomPassword, randomEmail);
    }

    @Step("Create new user only login")
    public static User getWithLoginOnly() {
        String randomEmail = " ";
        String randomPassword = " ";
        String randomLogin = faker.name().firstName();
        return new User(randomLogin, randomPassword, randomEmail);
    }

    @Step("Create new user twice to login")
    public static User getDefaultCourierTwice() {
        return generateDefaultUser();
    }

    @Step("Change default user")
    public static User generateChangeDefaultUser() {
        String randomEmail = faker.name().firstName() + "@mail.ru";
        String randomPassword = faker.numerify("#####");
        String randomLogin = faker.name().firstName();
        return new User(randomLogin, randomPassword, randomEmail);
    }
}