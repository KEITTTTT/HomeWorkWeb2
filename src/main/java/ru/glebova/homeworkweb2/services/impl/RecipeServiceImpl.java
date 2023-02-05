package ru.glebova.homeworkweb2.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.glebova.homeworkweb2.exception.RecipeReadToFileException;
import ru.glebova.homeworkweb2.exception.RecipeSaveToFileException;
import ru.glebova.homeworkweb2.model.Ingredient;
import ru.glebova.homeworkweb2.model.Recipe;
import ru.glebova.homeworkweb2.services.RecipeFilesService;
import ru.glebova.homeworkweb2.services.RecipeService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {

    private static Map<Integer, Recipe> recipeMap = new HashMap<>();
    private static int count = 0;
    private final RecipeFilesService recipeFilesService;


    public RecipeServiceImpl(RecipeFilesService recipeFilesService) {
        this.recipeFilesService = recipeFilesService;
    }

    @PostConstruct
    private void init() {
        try {
            readRecipeFromFile();
        } catch (Exception e){
            e.printStackTrace();
        }
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
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            recipeFilesService.saveRecipeToFile(json);
        } catch (JsonProcessingException e) {
            throw new RecipeSaveToFileException();
        }
    }

    private void readRecipeFromFile() {
        String json = recipeFilesService.readRecipeFromFile();
        try {
            recipeMap = new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RecipeReadToFileException();
        }
    }
    @Override
    public Path getAllAsText() throws IOException {
        Path path = recipeFilesService.createTempFile("allRecipes");
        for (Recipe recipe : recipeMap.values()) {
            try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                writer.append(recipe.getName()).append("\n");
                writer.append(String.format("Время приготовления: %d мин.", recipe.getCookingTime())).append("\n");
                writer.append("Игредиенты:").append("\n");
                for (int i = 0; i < recipe.getIngredients().size(); i++) {
                    Ingredient ingredient = recipe.getIngredients().get(i);
                    writer.append(String.format("%d %s - %d %s",
                            i+1, ingredient.getName(), ingredient.getQuantity(), ingredient.getUnit()));
                    writer.append("\n");
                }
                writer.append("Инструкция приготовления:").append("\n");
                for (int i = 0; i < recipe.getSteps().size(); i++) {
                    writer.append(String.format("%d. %s", i+1, recipe.getSteps().get(i))).append("\n");
                }
            }
        }
        return path;
    }
}
