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
    public Ingredient add(Ingredient ingredient) {
        ingredientMap.put(count++, ingredient);
        return ingredient;
    }

    @Override
    public Ingredient get(int id) {
        return ingredientMap.get(id);
    }

    @Override
    public Ingredient update(int id, Ingredient ingredient) {
        if (ingredientMap.containsKey(id)) {
            return ingredientMap.put(id, ingredient);
        }
        return null;
    }

    @Override
    public Ingredient remove(int id) {
        if (ingredientMap.containsKey(id)) {
            return ingredientMap.remove(id);
        }
        return null;
    }

    @Override
    public Map<Integer, Ingredient> getAll() {
        return ingredientMap;
    }
}


