package ru.glebova.homeworkweb2.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor

public class Recipe {
    private String name;
    private int cookingTime;
    private List<Ingredient> ingredients;
    private ArrayList<String> steps;
}

