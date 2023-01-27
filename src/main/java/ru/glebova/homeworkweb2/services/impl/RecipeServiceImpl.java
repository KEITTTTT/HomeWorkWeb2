package ru.glebova.homeworkweb2.services.impl;
import org.springframework.stereotype.Service;
import ru.glebova.homeworkweb2.model.Recipe;
import ru.glebova.homeworkweb2.services.RecipeService;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {

    private static final Map<Integer, Recipe> recipeMap = new HashMap<>();
    private static int count = 0;

    @Override
    public void add(Recipe recipe) {
            recipeMap.put(count++, recipe);
        }

    @Override
    public Recipe get(int id) {
        if (!recipeMap.containsKey(id)) {
            throw new RuntimeException("Такой рецепт не найден, проверьте id");
        } else {
            return recipeMap.get(id);
        }
    }
}
