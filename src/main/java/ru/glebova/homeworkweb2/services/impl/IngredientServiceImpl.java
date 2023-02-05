package ru.glebova.homeworkweb2.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.glebova.homeworkweb2.exception.IngredientReadToFileException;
import ru.glebova.homeworkweb2.exception.IngredientSaveToFileException;
import ru.glebova.homeworkweb2.model.Ingredient;
import ru.glebova.homeworkweb2.services.IngredientFilesService;
import ru.glebova.homeworkweb2.services.IngredientService;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {
    private static Map<Integer, Ingredient> ingredientMap = new HashMap<>();
    private static int count = 0;

    private final IngredientFilesService ingredientFilesService;

    public IngredientServiceImpl(IngredientFilesService ingredientFilesService) {
        this.ingredientFilesService = ingredientFilesService;
    }

    @PostConstruct
    private void init() {
        try {
            readIngredientFromFile();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Ingredient add(Ingredient ingredient) {
        ingredientMap.put(count++, ingredient);
        saveIngredientToFile();
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
            saveIngredientToFile();
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
            String json = new ObjectMapper().writeValueAsString(ingredientMap);
            ingredientFilesService.saveIngredientToFile(json);
        } catch (JsonProcessingException e) {
            throw new IngredientSaveToFileException();
        }
    }

    private void readIngredientFromFile(){
        String json = ingredientFilesService.readIngredientFromFile();
        try {
            ingredientMap = new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            throw new IngredientReadToFileException();
        }
    }
}



