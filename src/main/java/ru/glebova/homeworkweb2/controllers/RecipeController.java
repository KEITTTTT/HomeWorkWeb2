package ru.glebova.homeworkweb2.controllers;
import org.springframework.web.bind.annotation.*;
import ru.glebova.homeworkweb2.model.Recipe;
import ru.glebova.homeworkweb2.services.RecipeService;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {

        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(int id) {

        return recipeService.get(id);
    }

    @PostMapping
    public void addRecipe(@RequestBody Recipe recipe) {
        recipeService.add(recipe);
    }
}
