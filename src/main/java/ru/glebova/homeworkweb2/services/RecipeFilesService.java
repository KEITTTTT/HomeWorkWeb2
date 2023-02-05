package ru.glebova.homeworkweb2.services;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public interface RecipeFilesService {

    boolean saveRecipeToFile(String json);

    String readRecipeFromFile();

    boolean cleanDataFile();

    File getDataFile();
    Path createTempFile(String suffix);

}
