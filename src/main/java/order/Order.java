package order;

import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private List<String> ingredients;

    @Step("Set correct ingredients")
    public static Order setCorrectIngredients() {
        List<String> ingredients = new ArrayList<>();

        ingredients.add("61c0c5a71d1f82001bdaaa6d");

        return new Order(ingredients);
    }

    @Step("Set incorrect ingredients")
    public static Order setIncorrectIngredients() {
        List<String> ingredients = new ArrayList<>();

        ingredients.add("0161c0c5a71d1f82001bdaaa6d");

        return new Order(ingredients);
    }

    @Step("Set empty ingredients")
    public static Order setEmptyIngredients() {
        List<String> ingredients = new ArrayList<>();

        return new Order(ingredients);
    }
}
