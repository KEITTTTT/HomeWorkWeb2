package ru.glebova.homeworkweb2.services;

import ru.glebova.homeworkweb2.model.Recipe;

import java.util.Map;

public interface RecipeService {
    Recipe add(Recipe recipe);

    Recipe get(int id);

    Recipe update(int id, Recipe recipe);

    Recipe remove(int id);

    Map<Integer, Recipe> getAll();
}