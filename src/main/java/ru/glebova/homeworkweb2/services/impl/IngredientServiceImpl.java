package ru.glebova.homeworkweb2.services.impl;
import org.springframework.stereotype.Service;
import ru.glebova.homeworkweb2.model.Ingredient;
import ru.glebova.homeworkweb2.services.IngredientService;
import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {
    private static final Map<Integer, Ingredient> ingredientMap = new HashMap<>();
    private static int count = 0;

    @Override
    public void add(Ingredient ingredient) {
            ingredientMap.put(count++, ingredient);
        }

    @Override
    public Ingredient get(int id) {
        if (!ingredientMap.containsKey(id)) {
            throw new RuntimeException("Такой ингредиент не найден, проверьте id");
        } else {
            return ingredientMap.get(id);
        }
    }
}
