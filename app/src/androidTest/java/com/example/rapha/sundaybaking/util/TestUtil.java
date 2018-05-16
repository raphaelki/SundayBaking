package com.example.rapha.sundaybaking.util;

import com.example.rapha.sundaybaking.data.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static List<Recipe> createRecipes(String... names) {
        List<Recipe> recipes = new ArrayList<>();
        for (int index = 0; index < names.length; index++) {
            recipes.add(new Recipe(index, names[index], 8, ""));
        }
        return recipes;
    }
}
