package ru.glebova.homeworkweb2.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.glebova.homeworkweb2.model.Ingredient;
import ru.glebova.homeworkweb2.services.IngredientFilesService;
import ru.glebova.homeworkweb2.services.IngredientService;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {
    private static final Map<Integer, Ingredient> ingredientMap = new HashMap<>();
    private static int count = 0;
    private Map<Integer, Ingredient> ingredients = new HashMap<>();
    private final IngredientFilesService ingredientFilesService;

    public IngredientServiceImpl(IngredientFilesService ingredientFilesService) {
        this.ingredientFilesService = ingredientFilesService;
    }

    @PostConstruct
    private void init() {
        readIngredientFromFile();
    }

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
            ingredientMap.put(id, ingredient);
            return ingredient;
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

    private void saveIngredientToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredients);
            ingredientFilesService.saveIngredientToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readIngredientFromFile() {
        String json = ingredientFilesService.readIngredientFromFile();
        try {
            ingredients = new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}



