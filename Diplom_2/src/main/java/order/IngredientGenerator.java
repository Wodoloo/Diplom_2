package order;

import io.qameta.allure.Step;

import java.util.Random;

public class IngredientGenerator {


    @Step("Create new order with bun")
    public static Order generateBun() {
        String[] ingredients = new String[]{
                "61c0c5a71d1f82001bdaaa6d",
                "61c0c5a71d1f82001bdaaa6c"};
        return new Order(ingredients[new Random().nextInt(ingredients.length)]);
    }

    @Step("Create new order with main ing")
    public static Order generateMain() {
        String[] ingredients = new String[]{
                "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa70",
                "61c0c5a71d1f82001bdaaa71", "61c0c5a71d1f82001bdaaa6e",
                "61c0c5a71d1f82001bdaaa76", "61c0c5a71d1f82001bdaaa77",
                "61c0c5a71d1f82001bdaaa78", "61c0c5a71d1f82001bdaaa79",
                "61c0c5a71d1f82001bdaaa7a"};
        return new Order(ingredients[new Random().nextInt(ingredients.length)]);
    }

    @Step("Create new order with sauce")
    public static Order generateSauce() {
        String[] ingredients = new String[]{
                "61c0c5a71d1f82001bdaaa72", "61c0c5a71d1f82001bdaaa73",
                "61c0c5a71d1f82001bdaaa74", "61c0c5a71d1f82001bdaaa75"};
        return new Order(ingredients[new Random().nextInt(ingredients.length)]);
    }

    @Step("Create new order without ingredients")
    public static Order generateNull() {
        String ingredients = "";
        return new Order(ingredients);
    }

    @Step("Create new order with false ingredient")
    public static Order generateFalseIngredient() {
        String ingredients = "888888888888888888888";
        return new Order(ingredients);
    }

}