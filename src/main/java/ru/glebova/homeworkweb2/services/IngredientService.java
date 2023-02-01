package ru.glebova.homeworkweb2.services;

import ru.glebova.homeworkweb2.model.Ingredient;

public interface IngredientService {
    void add(Ingredient ingredient);

    Ingredient get(int id);
}
