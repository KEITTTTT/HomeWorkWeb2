package ru.glebova.homeworkweb2.services;

public interface RecipeFilesService {

    boolean saveRecipeToFile(String json);

    String readRecipeFromFile();

    boolean cleanDataFile();
}
