package ru.glebova.homeworkweb2.controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.glebova.homeworkweb2.model.Recipe;
import ru.glebova.homeworkweb2.services.RecipeService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

@RestController
@RequestMapping("/recipe")
@Tag(name = "Рецепты", description = "Эндпойнты для работы с рецептами")

public class RecipeController {
    private final RecipeService recipeService;
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Поиск рецепта",
            description = "Поиск осуществляется по id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт найден",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт не найден",
                    content = {}
            )
    }
    )
    public Recipe getRecipe(@PathVariable("id") int id) {
        return recipeService.get(id);
    }

    @GetMapping
    @Operation(summary = "Получение всех рецептов")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепты найдены",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепты не найдены",
                    content = {}
            )
    }
    )
    public Map<Integer, Recipe> getAllRecipes() {
        return recipeService.getAll();
    }
    @GetMapping("/all")
    @Operation(description = "Получение всех рецептов в текстовом файле")
    public ResponseEntity<?> getAllAsText() {
        try {
            File file = recipeService.getAllAsText().toFile();
            if (!file.exists() || Files.size(file.toPath()) == 0) {
                return ResponseEntity.noContent().build();
            }
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = \"Recipes.txt\"")
                    .body(resource);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }
    }

    @PostMapping
    @Operation(summary = "Добавление нового рецепта")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт добавлен",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка",
                    content = {}
            )
    }
    )
    public ResponseEntity<?> addRecipe(@RequestBody Recipe recipe) {
        if(StringUtils.isAllBlank(recipe.getName())) {
                return ResponseEntity.badRequest().body("Название рецепта не может быть пустым");
    }
    return ResponseEntity.ok(recipeService.add(recipe));
    }

    @PutMapping ("/{id}")
    @Operation(
            summary = "Редактирование рецепта",
            description = "Редактирование осуществляется по id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт отредактирован",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка",
                    content = {}
            )
    }
    )
    public ResponseEntity<Recipe> updateRecipe(@PathVariable("id") int id, @RequestBody Recipe recipe) {
        return ResponseEntity.ok().body(recipeService.update(id, recipe));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление рецепта",
            description = "Удаление осуществляется по id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт удален"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Рецепт не найден"
            )
    }
    )
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable("id") int id) {
        return ResponseEntity.ok().body(recipeService.remove(id));
    }
}
