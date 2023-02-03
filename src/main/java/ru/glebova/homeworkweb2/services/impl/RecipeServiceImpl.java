package ru.glebova.homeworkweb2.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.glebova.homeworkweb2.model.Recipe;
import ru.glebova.homeworkweb2.services.RecipeFilesService;
import ru.glebova.homeworkweb2.services.RecipeService;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {

    private static final Map<Integer, Recipe> recipeMap = new HashMap<>();
    private static int count = 0;
    private final RecipeFilesService recipeFilesService;
    private Map<Integer, Recipe> recipes = new HashMap<>();

    public RecipeServiceImpl(RecipeFilesService recipeFilesService) {
        this.recipeFilesService = recipeFilesService;
    }

    @PostConstruct
    private void init() {
        readRecipeFromFile();
    }

    @Override
    public Recipe add(Recipe recipe) {
        recipeMap.put(count++, recipe);
        saveRecipeToFile();
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
            saveRecipeToFile();
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

    private void saveRecipeToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipes);
            recipeFilesService.saveRecipeToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readRecipeFromFile() {
        String json = recipeFilesService.readRecipeFromFile();
        try {
            recipes = new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
