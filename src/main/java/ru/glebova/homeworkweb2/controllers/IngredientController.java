package ru.glebova.homeworkweb2.controllers;
import org.springframework.web.bind.annotation.*;
import ru.glebova.homeworkweb2.model.Ingredient;
import ru.glebova.homeworkweb2.model.Recipe;
import ru.glebova.homeworkweb2.services.IngredientService;
import java.util.Map;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    private IngredientService ingredientService;

    public IngredientController(IngredientService recipeService) {
        this.ingredientService = recipeService;
    }

    @GetMapping("/{id}")
    public Ingredient getIngredient(@PathVariable("id") int id) {
        return ingredientService.get(id);
    }

    @GetMapping
    public Map<Integer, Ingredient> getAllIngredients() {
        return ingredientService.getAll();
    }

    @PostMapping
    public Ingredient addIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.add(ingredient);
    }

    @PutMapping("/{id}")
    public Ingredient updateIngredient(@PathVariable("id") int id, @RequestBody Ingredient ingredient) {
        return ingredientService.update(id, ingredient);
    }

    @DeleteMapping("/{id}")
    public Ingredient removeIngredient(@PathVariable("id") int id, @RequestBody Recipe ingredient) {
        return ingredientService.remove(id);
    }
}