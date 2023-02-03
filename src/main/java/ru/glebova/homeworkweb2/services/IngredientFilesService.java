package ru.glebova.homeworkweb2.services;

public interface IngredientFilesService {

    boolean saveIngredientToFile(String json);

    String readIngredientFromFile();

    boolean cleanDataFile();
}
