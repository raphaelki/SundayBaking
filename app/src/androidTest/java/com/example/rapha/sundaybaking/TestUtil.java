package com.example.rapha.sundaybaking;

import com.example.rapha.sundaybaking.data.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static List<Recipe> createRecipes() {
        Recipe recipe1 = new Recipe(1, "Kiwi cake", 3, "");
        Recipe recipe2 = new Recipe(2, "Banana cake", 5, "https://cdn.pixabay.com/photo/2018/05/03/10/10/muffins-3370959_960_720.jpg");
        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe2);
        recipes.add(recipe1);
        return recipes;
    }
}
