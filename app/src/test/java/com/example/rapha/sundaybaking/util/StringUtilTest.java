package com.example.rapha.sundaybaking.util;

import com.example.rapha.sundaybaking.data.models.Ingredient;
import com.example.rapha.sundaybaking.data.models.InstructionStep;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StringUtilTest {

    @Test
    public void prepareShortDescription() {
        String description = "Description";
        List<InstructionStep> steps = new ArrayList<>();
        steps.add(createStep(0, description));
        steps.add(createStep(1, description));
        steps.add(createStep(2, description));
        String[] expected = {
                description,
                "1. " + description,
                "2. " + description
        };
        for (int index = 0; index < steps.size(); index++) {
            String output = StringUtil.prepareShortDescription(steps.get(index));
            assertThat(output, is(expected[index]));
        }
    }

    @Test
    public void cutStepNoFromDescription() {
        String[] descriptions = {
                "1. First step",
                "1. First step 2.",
                "1. First step 2. "};
        String[] expected = {
                "First step",
                "First step 2.",
                "First step 2. "};
        for (int index = 0; index < descriptions.length; index++) {
            String output = StringUtil.cutStepNoFromDescription(descriptions[index]);
            assertThat(output, is(expected[index]));
        }
    }

    @Test
    public void formatIngredientQuantity() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(createIngredient(3.5, "G"));
        ingredients.add(createIngredient(2.0, "UNIT"));
        ingredients.add(createIngredient(0.5, "TSP"));
        ingredients.add(createIngredient(1.0, "CUP"));
        String[] expected = {
                "3.5 g",
                "2",
                "0.5 tsp",
                "1 cup"
        };
        for (int index = 0; index < ingredients.size(); index++) {
            String output = StringUtil.formatIngredientQuantity(ingredients.get(index));
            assertThat(output, is(expected[index]));
        }
    }

    private InstructionStep createStep(int stepNo, String shortDescription) {
        return new InstructionStep(0, stepNo, shortDescription, "", "", "", "");
    }

    private Ingredient createIngredient(double quantity, String measure) {
        return new Ingredient(0, quantity, measure, "Ingredient", "");
    }

    @Test
    public void urlContainsVideoSource() {
        assertThat(StringUtil.urlContainsVideoSource("pic"), is(false));
        assertThat(StringUtil.urlContainsVideoSource("pic.mp4"), is(true));
        assertThat(StringUtil.urlContainsVideoSource("pic.jpg"), is(false));
        assertThat(StringUtil.urlContainsVideoSource(""), is(false));
    }

    @Test
    public void urlContainsImageSource() {
        assertThat(StringUtil.urlContainsImageSource("pic"), is(false));
        assertThat(StringUtil.urlContainsImageSource("pic.mp4"), is(false));
        assertThat(StringUtil.urlContainsImageSource("pic.jpg"), is(true));
        assertThat(StringUtil.urlContainsImageSource("pic.jpeg"), is(true));
        assertThat(StringUtil.urlContainsImageSource("pic.png"), is(true));
        assertThat(StringUtil.urlContainsImageSource(""), is(false));
    }
}