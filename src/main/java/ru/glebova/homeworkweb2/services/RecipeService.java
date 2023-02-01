package ru.glebova.homeworkweb2.services;

import ru.glebova.homeworkweb2.model.Recipe;

public interface RecipeService {
    void add(Recipe recipe);

    Recipe get(int id);
}