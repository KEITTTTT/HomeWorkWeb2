package ru.glebova.homeworkweb2.services;

import java.io.File;

public interface IngredientFilesService {

    boolean saveIngredientToFile(String json);

    String readIngredientFromFile();

    boolean cleanDataFile();

    File getDataFile();
}
