package com.example.rapha.sundaybaking;

import com.example.rapha.sundaybaking.data.models.Recipe;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtil {

    public static List<Recipe> createRecipes(String... names) {
        return Arrays.stream(names).map(name -> new Recipe(0, name, 8, ""))
                .collect(Collectors.toList());
    }
}
