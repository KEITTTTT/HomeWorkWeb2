package ru.glebova.homeworkweb2.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.glebova.homeworkweb2.services.IngredientFilesService;
import ru.glebova.homeworkweb2.services.RecipeFilesService;

import java.io.*;
import java.nio.file.Files;

@RestController
@RequestMapping("/files")
public class FilesController {
    private final RecipeFilesService recipeFilesService;
    private final IngredientFilesService ingredientFilesService;

    public FilesController(RecipeFilesService recipeFilesService, IngredientFilesService ingredientFilesService) {
        this.recipeFilesService = recipeFilesService;
        this.ingredientFilesService = ingredientFilesService;
    }

    @GetMapping(value = "/export", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Получение файла со всеми рецептами")
    public ResponseEntity<InputStreamResource> downloadDataFile() throws FileNotFoundException {
        File file = recipeFilesService.getDataFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipesLog.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }


    @PostMapping(value = "/importRecipes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(description = "Отправка и замена файла с рецептами")
    public ResponseEntity<Void> uploadRecipesDataFile(@RequestParam MultipartFile file) {
        recipeFilesService.cleanDataFile();
        File dataFile = recipeFilesService.getDataFile();
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping(value = "/importIngredients", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(description = "Отправка и замена файла с ингредиентами")
    public ResponseEntity<Void> uploadIngredientsDataFile(@RequestParam MultipartFile file) {
        ingredientFilesService.cleanDataFile();
        File dataFile = ingredientFilesService.getDataFile();
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}