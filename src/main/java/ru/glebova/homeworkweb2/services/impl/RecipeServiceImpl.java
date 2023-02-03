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
    public Recipe add(Recipe recipe) {
        recipeMap.put(count++, recipe);
        return recipe;
    }

    @Override
    public Recipe get(int id) {
        return recipeMap.get(id);
    }

    @Override
    public Recipe update(int id, Recipe recipe) {
        if (recipeMap.containsKey(id)) {
            recipeMap.put(id, recipe);
            return recipe;
        }
        return null;
    }

    @Override
    public Recipe remove(int id) {
        return recipeMap.remove(id);
    }

    @Override
    public Map<Integer, Recipe> getAll() {
        return recipeMap;
    }
}
