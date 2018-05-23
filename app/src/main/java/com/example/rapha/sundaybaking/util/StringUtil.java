package com.example.rapha.sundaybaking.util;

import com.example.rapha.sundaybaking.data.models.Ingredient;
import com.example.rapha.sundaybaking.data.models.InstructionStep;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class StringUtil {

    public static String cutStepNoFromDescription(String description) {
        return description.replaceAll("^[0-9]. ", "");
    }

    public static String prepareShortDescription(InstructionStep step) {
        if (step.getStepNo() == 0) {
            return step.getShortDescription();
        } else {
            return String.valueOf(step.getStepNo()) +
                    ". " +
                    step.getShortDescription();
        }
    }

    public static String formatIngredientQuantity(Ingredient ingredient) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
        decimalFormat.applyPattern("#.#");
        decimalFormat.setDecimalSeparatorAlwaysShown(false);
        String quantity = decimalFormat.format(ingredient.getQuantity());
        String measure = ingredient.getMeasure().toLowerCase();
        measure = measure.replace("unit", "");
        return (quantity + " " + measure).trim();
    }

    public static boolean urlContainsVideoSource(String url) {
        return url.endsWith("mp4");
    }

    public static boolean urlContainsImageSource(String url) {
        return url.endsWith("png") || url.endsWith("jpg") || url.endsWith("jpeg");
    }
}
