package ru.glebova.homeworkweb2.services.impl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.glebova.homeworkweb2.exception.CreateTempFileException;
import ru.glebova.homeworkweb2.model.Ingredient;
import ru.glebova.homeworkweb2.model.Recipe;
import ru.glebova.homeworkweb2.services.RecipeFilesService;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Service
public class RecipeFilesServiceImpl implements RecipeFilesService {
    @Value("${path.to.recipe.data.file}")
    private String dataFilePath;
    @Value("${name.of.recipe.data.file}")
    private String dataFileName;
    @Override
    public boolean saveRecipeToFile(String json) {
        try {
            cleanDataFile();
            Files.writeString(Path.of(dataFilePath, dataFileName), json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    @Override
    public String readRecipeFromFile() {
        try {
            return Files.readString(Path.of(dataFilePath, dataFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean cleanDataFile() {
        Path path = Path.of(dataFilePath, dataFileName);
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
    @Override
    public File getDataFile() {
        return new File(dataFilePath + "/" + dataFileName);
    }
    @Override
    public Path createTempFile(String suffix) {
        try {
            return Files.createTempFile(Path.of(dataFilePath), "tempFile", suffix);
        } catch (IOException e) {
            throw new CreateTempFileException();
        }
    }

}