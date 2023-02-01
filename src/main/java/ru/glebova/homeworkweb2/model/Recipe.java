package ru.glebova.homeworkweb2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Recipe {
    private String name;
    private int cookingTime;
    private List<Ingredient> ingredients;

    private ArrayList<String> steps;

    public Recipe(String name, int preparingTime, List<Ingredient> ingredients, ArrayList<String> steps) {
        this.name = name;
        this.cookingTime = preparingTime;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPreparingTime() {
        return cookingTime;
    }

    public void setPreparingTime(int preparingTime) {
        this.cookingTime = preparingTime;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<String> steps) {
        this.steps = steps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return cookingTime == recipe.cookingTime && name.equals(recipe.name)
                && ingredients.equals(recipe.ingredients) && steps.equals(recipe.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cookingTime, ingredients, steps);
    }
}