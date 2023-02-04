package ru.glebova.homeworkweb2.services;

import java.io.File;

public interface RecipeFilesService {

    boolean saveRecipeToFile(String json);

    String readRecipeFromFile();

    boolean cleanDataFile();

    File getDataFile();
}
