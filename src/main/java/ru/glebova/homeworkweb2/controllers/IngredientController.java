package ru.glebova.homeworkweb2.controllers;

import org.springframework.web.bind.annotation.*;
import ru.glebova.homeworkweb2.model.Ingredient;
import ru.glebova.homeworkweb2.services.IngredientService;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    private IngredientService ingredientService;

    public IngredientController(IngredientService recipeService) {

        this.ingredientService = recipeService;
    }

    @GetMapping("/{id}")
    public Ingredient getIngredient(int id) {

        return ingredientService.get(id);
    }

    @PostMapping
    public void addRecipe(@RequestBody Ingredient ingredient) {

        ingredientService.add(ingredient);
    }
}