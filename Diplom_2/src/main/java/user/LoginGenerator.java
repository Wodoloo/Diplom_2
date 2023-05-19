package user;

import com.github.javafaker.Faker;

import java.util.Locale;

public class LoginGenerator {

    private static Faker faker = new Faker(new Locale("en"));

    public static Credentials getFalseUser(){
        String randomPassword = faker.numerify("####");
        String randomLogin = faker.name().fullName();
        return new Credentials(randomLogin, randomPassword);
    }

    public static Credentials getWithPasswordOnly() {
        String randomPassword = faker.numerify("####");
        String randomLogin = " ";
        return new Credentials(randomLogin, randomPassword);

    }

    public static Credentials getEmptyBoxUser() {
        String randomPassword = " ";
        String randomLogin = " ";
        return new Credentials(randomLogin, randomPassword);

    }

}