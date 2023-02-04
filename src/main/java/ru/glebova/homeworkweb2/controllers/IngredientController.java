package ru.glebova.homeworkweb2.controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.glebova.homeworkweb2.model.Ingredient;
import ru.glebova.homeworkweb2.services.IngredientService;
import java.util.Map;

@RestController
@RequestMapping("/ingredient")
@Tag(name = "Ингредиенты", description = "Эндпойнты для работы с ингредиентами")

public class IngredientController {
    private final IngredientService ingredientService;
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Поиск ингредиента",
            description = "Поиск осуществляется по id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент найден",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиент не найден",
                    content = {}
            )
    }
    )
    public Ingredient getIngredient(@PathVariable int id) {
        return ingredientService.get(id);
    }

    @GetMapping
    @Operation(summary = "Получение всех ингредиентов")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиенты найдены",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
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
    public Map<Integer, Ingredient> getAllIngredients() {
        return ingredientService.getAll();
    }

    @PostMapping
    @Operation(description = "Добавление ингредиента")
    public ResponseEntity<?> addIngredient (@RequestBody Ingredient ingredient) {
        if(StringUtils.isAllBlank(ingredient.getName())) {
            return ResponseEntity.badRequest().body("Название ингредиента не может быть пустым");
        }
        return ResponseEntity.ok(ingredientService.add(ingredient));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Редактирование ингредиента",
            description = "Редактирование осуществляется по id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент отредактирован",
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
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable("id") int id, @RequestBody Ingredient ingredient) {
        return ResponseEntity.ok().body(ingredientService.update(id, ingredient));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление ингредиента",
            description = "Удаление осуществляется по id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент удален"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ингредиент не найден"
            )
    }
    )
    public ResponseEntity<Ingredient> removeIngredient(@PathVariable("id") int id) {
        return ResponseEntity.ok().body(ingredientService.remove(id));
    }
}