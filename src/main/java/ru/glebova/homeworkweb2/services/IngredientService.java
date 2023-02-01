package ru.glebova.homeworkweb2.services;

import ru.glebova.homeworkweb2.model.Ingredient;

import java.util.Map;

public interface IngredientService {
    Ingredient add(Ingredient ingredient);

    Ingredient get(int id);

    Ingredient update(int id, Ingredient ingredient);

    Ingredient remove(int id);

    Map<Integer, Ingredient> getAll();
}
