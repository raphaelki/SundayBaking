package com.example.rapha.sundaybaking.util;

import com.example.rapha.sundaybaking.data.models.Ingredient;
import com.example.rapha.sundaybaking.data.models.InstructionStep;
import com.example.rapha.sundaybaking.data.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class DataUtil {

    public static List<Recipe> createRecipes(String... names) {
        List<Recipe> recipes = new ArrayList<>();
        for (int index = 0; index < names.length; index++) {
            recipes.add(new Recipe(index, names[index], 8, ""));
        }
        return recipes;
    }

    public static List<Ingredient> createIngredients(String[] names) {
        List<Ingredient> ingredients = new ArrayList<>();
        for (int index = 0; index < names.length; index++) {
            ingredients.add(new Ingredient(index, (double) index, "g", names[index], "Pie"));
        }
        return ingredients;
    }

    public static List<InstructionStep> createDirectionSteps(String[] description) {
        List<InstructionStep> steps = new ArrayList<>();
        for (int index = 0; index < description.length; index++) {
            steps.add(new InstructionStep(index, index, description[index], description[index] + " detailed", "", "", "Pie"));
        }
        return steps;
    }

    /**
     * Create direction steps with every other holding an empty url
     *
     * @param stepCount
     * @return
     */
    public static List<InstructionStep> createDirectionSteps(int stepCount) {
        List<InstructionStep> steps = new ArrayList<>();
        for (int index = 0; index < stepCount; index++) {
            String url = "";
            if (index % 2 == 0) {
                url = "video url";
            }
            steps.add(new InstructionStep(index,
                    index,
                    "step " + index,
                    "step " + index + " detailed",
                    url,
                    "",
                    "Pie"));
        }
        return steps;
    }

    public static InstructionStep createDirectionStepWithVideoUrl(String name, String videoUrl) {
        return new InstructionStep(0, 0, "Short description",
                "Detailed description", videoUrl, "", "Pie");
    }
}
